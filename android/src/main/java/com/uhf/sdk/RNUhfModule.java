
package com.uhf.sdk;

import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageInfo;
import android.content.ServiceConnection;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.KeyEvent;

import com.uhf.sdk.protocol.RespAndNotifyFactory;
import com.uhf.sdk.protocol.RespAndNotifyHandler;
import com.uhf.sdk.protocol.RespOrNotifyFrame;
import com.uhf.sdk.protocol.cmd.CmdDeviceInfo;
import com.uhf.sdk.protocol.cmd.CmdFrame;
import com.uhf.sdk.protocol.cmd.CmdPollingMulti;
import com.uhf.sdk.protocol.cmd.CmdPollingSingle;
import com.uhf.sdk.protocol.cmd.CmdPollingStop;
import com.uhf.sdk.protocol.cmd.CmdSelectModeSet;
import com.uhf.sdk.protocol.cmd.CmdTagDataRead;
import com.uhf.sdk.protocol.cmd.CmdTagDataWrite;
import com.uhf.sdk.protocol.cmd.CmdTagSelect;
import com.uhf.sdk.protocol.cmd.CmdPaPowerGet;
import com.uhf.sdk.protocol.cmd.CmdPaPowerSet;
import com.uhf.sdk.protocol.type.DeviceInfoType;
import com.uhf.sdk.protocol.utils.ConvertUtils;
import com.uhf.sdk.protocol.resp.RespDeviceInfo;
import com.uhf.sdk.protocol.resp.RespPollingSingle;
import com.uhf.sdk.protocol.resp.RespPollingStop;
import com.uhf.sdk.protocol.resp.RespSelectModeSet;
import com.uhf.sdk.protocol.resp.RespTagDataError;
import com.uhf.sdk.protocol.resp.RespTagDataRead;
import com.uhf.sdk.protocol.resp.RespTagDataWrite;
import com.uhf.sdk.protocol.resp.RespTagSelect;
import com.uhf.sdk.protocol.resp.RespPaPowerGet;
import com.uhf.sdk.protocol.resp.RespPaPowerSet;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import com.example.serialportuhf.aidl.IUhfListener;
import com.example.serialportuhf.aidl.IUhfService;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RNUhfModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  protected static final Logger logger = LoggerFactory
          .getLogger(RNUhfModule.class.getSimpleName());

  private final ReactApplicationContext reactContext;

  private RespAndNotifyFactory factory = new RespAndNotifyFactory();
  private RespAndNotifyHandler protocolHandler = getProtocolHandler();
  private IUhfService uhfService; // 注意可能为null
  private boolean isScanning = false;
  private boolean isMultiScan = false;
  private Thread thread;
  private static RNUhfModule instance = null;

  public static RNUhfModule getInstance() {
    return instance;
  }

  public static RNUhfModule setInstance(ReactApplicationContext reactContext) {
    if (instance == null) {
      synchronized (RNUhfModule.class) {
        if (instance == null) {
          instance = new RNUhfModule(reactContext);
        }
      }
    }
    return instance;
  }

  public RNUhfModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
    this.reactContext = reactContext;
  }

  @Override
  public void onHostResume() {
    // Activity `onResume`
    init();
  }

  @Override
  public void onHostPause() {
    // Activity `onPause`
    pause();
  }

  @Override
  public void onHostDestroy() {
    // do not set state to destroyed, do not send an event. By the current implementation, the
    // catalyst instance is going to be immediately dropped, and all JS calls with it.
  }

  private void unbindService() {
    try {
      if (uhfService != null) {
        uhfService.setListener(null);
      }
    } catch (RemoteException e) {
      logger.error(ExceptionUtils.getStackTrace(e));
    }
    reactContext.getApplicationContext().unbindService(serviceConnection);
  }

  private boolean bindService(Intent intent) {
    return reactContext.getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  public String getName() {
    return "RNUhf";
  }

  @Override
  public void initialize() {
    super.initialize();

    //init();
  }

  private ServiceConnection serviceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      showInfo("on Service Connected!");
      try {
        uhfService = IUhfService.Stub.asInterface(service);
        uhfService.setListener(listener);
        if (uhfService.isPowerOn()) {
          showInfo("power on!");
        } else {
          showInfo("power off!");
        }
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      showInfo("on Service DisConnected!");
      uhfService = null;
    }

  };

  private IUhfListener listener = new IUhfListener.Stub() {

    @Override
    public void onPowerOn() throws RemoteException {
      showInfo("on power on!");
    }

    @Override
    public void onPowerOff() throws RemoteException {
      showInfo("on power off!");
    }

    @Override
    public void onResponse(byte[] resp) throws RemoteException {
      String s = ConvertUtils.bytesToString(resp);
      logger.info("onResponse:" + s);
      List<RespOrNotifyFrame> msgList = factory.receive(resp);
      for (RespOrNotifyFrame frame : msgList) {
        logger.info(frame.getClass().getSimpleName());
        frame.handleBy(protocolHandler);
      }
    }
  };

  private RespAndNotifyHandler getProtocolHandler() {
    return new RespAndNotifyHandler() {
      @Override
      public void handle(RespDeviceInfo resp) {
        String data = resp.getInfoType() + " " + resp.getInfo();

        showInfo(data);
      }

      @Override
      public void handle(RespPollingSingle resp) {
        String epc = ConvertUtils.integerToString(resp.getEpc());
        if (isScanning) {
          if (!isMultiScan) {
            isScanning = false;
          }
          showScan(epc);
        }
      }

      @Override
      public void handle(RespPollingStop resp) {
        logger.info("polling stop result!" + System.currentTimeMillis());
        isScanning = false;
        isMultiScan = false;
      }

      @Override
      public void handle(RespPaPowerGet resp) {
        String power = resp.getPaPower() / 100 + " dBm";
        showInfo(power);
      }

      @Override
      public void handle(RespPaPowerSet resp) {
        if (resp.isSuccess()) {
          showInfo("set power success!");

        } else {
          showFail("set power fail!");
        }
      }

      @Override
      public void handle(RespTagDataError resp) {
        showFail("data error code:" + resp.getErrorCode());
        if(isScanning&&!isMultiScan) {
          isScanning = false;
          getEpc();
        }
      }
    };
  }

  private void write(final CmdFrame cmd) {
    if (cmd != null) {
      logger.info("write: "
              + ConvertUtils.bytesToString(cmd.toBytes()));

      try {
        thread = new Thread(new Runnable() {
          @Override
          public void run() {
            boolean toStop = false;
            while (!isScanning && !toStop) {
              toStop = true;
              write(cmd.toBytes());
              try {
                thread.join();
              } catch (Exception e) {

              }
            }
          }
        });
        thread.start();
      } catch (Exception e) {

      }
    }
  }

  private boolean write(byte[] array) {
    if (uhfService != null) {
      try {
        logger.info("write: " + ConvertUtils.bytesToString(array));
        uhfService.write(array);
        return true;
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }
    return false;
  }

  public boolean init() {
    //if (uhfService == null) {
    String packageName = "com.example.serialportuhf";
    String className = IUhfService.class.getName();
    logger.info("init：" + packageName + "-" + className);

    Intent intent = new Intent();
    intent.setAction(className);
    intent.setPackage(packageName);

    boolean isBind = bindService(intent);
    logger.info(String.valueOf(isBind));
    if(!isBind){
      showFail("on Service Fail");
    }
    return isBind;
    //}
  }

  @ReactMethod
  public boolean reset(){
    if (isScanning) {
      stop();
    }
    return init();
  }

  @ReactMethod
  public void pause() {
    if (isScanning) {
      stop();
    }
    unbindService();
  }

  @ReactMethod
  public void getFirmware() {
    if (isScanning) {
      stop();
    }
    write(new CmdDeviceInfo(DeviceInfoType.HARDWARE));
  }

  @ReactMethod
  public void getVersion() {
    if (isScanning) {
      stop();
    }
    write(new CmdDeviceInfo(DeviceInfoType.SOFTWARE));
  }

  @ReactMethod
  public void getMakerInfo() {
    if (isScanning) {
      stop();
    }
    write(new CmdDeviceInfo(DeviceInfoType.MANUFACTURER));
  }

  @ReactMethod
  public boolean getEpc() {
    byte[] cmd = {(byte) 0xBB, 0x00, 0x22, 0x00, 0x00, 0x22, 0x7E};
    //new CmdPollingSingle()
    if (!isScanning) {
      isScanning = true;
      isMultiScan = false;
      return write(cmd);
    }
    return false;
  }

  @ReactMethod
  public boolean scan() {
    if (!isScanning && !isMultiScan) {
      isScanning = true;
      isMultiScan = true;
      byte[] bytes=new CmdPollingMulti(10000).toBytes();
      return write(bytes);
    }
    return false;
  }

  @ReactMethod
  public boolean stop() {
    isScanning = false;
    isMultiScan = false;
    byte[] bytes = new CmdPollingStop().toBytes();
    return write(bytes);
  }

  @ReactMethod
  public void getPower() {
    if (isScanning) {
      stop();
    }
    write(new CmdPaPowerGet());
  }

  @ReactMethod
  public void setPower(int power) {
    if (isScanning) {
      stop();
    }
    write(new CmdPaPowerSet(power * 100));
  }

  @ReactMethod
  public void play() {
    try {
      Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      Ringtone r = RingtoneManager.getRingtone(reactContext, notification);
      r.play();
    } catch (Exception e) {

    }
  }

  public void onKeyDownEvent(int keyCode, KeyEvent keyEvent) {
    logger.info("onKeyDownEvent:"+keyCode);

    WritableMap params = Arguments.createMap();
    params.putString("on", "onKeyDown");
    params.putInt("keyCode", keyCode);

    WritableMap keyEventMap = Arguments.createMap();
    int action = keyEvent.getAction();
    char pressedKey = (char) keyEvent.getUnicodeChar();
    keyEventMap.putInt("action", action);
    keyEventMap.putString("pressedKey", String.valueOf(pressedKey));
    if (keyEvent.getAction() == KeyEvent.ACTION_MULTIPLE && keyCode == KeyEvent.KEYCODE_UNKNOWN) {
      String chars = keyEvent.getCharacters();
      if (chars != null) {
        keyEventMap.putString("characters", chars);
      }
    }

    params.putMap("keyEvent",keyEventMap);
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("uhfEvent", params);
  }

  public void onKeyUpEvent(int keyCode, KeyEvent keyEvent) {
    logger.info("onKeyUpEvent:"+keyCode);

    WritableMap params = Arguments.createMap();
    params.putString("on", "onKeyUp");
    params.putInt("keyCode", keyCode);

    WritableMap keyEventMap = Arguments.createMap();
    int action = keyEvent.getAction();
    char pressedKey = (char) keyEvent.getUnicodeChar();
    keyEventMap.putInt("action", action);
    keyEventMap.putString("pressedKey", String.valueOf(pressedKey));
    if (keyEvent.getAction() == KeyEvent.ACTION_MULTIPLE && keyCode == KeyEvent.KEYCODE_UNKNOWN) {
      String chars = keyEvent.getCharacters();
      if (chars != null) {
        keyEventMap.putString("characters", chars);
      }
    }

    params.putMap("keyEvent",keyEventMap);
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("uhfEvent", params);
  }

  private void showInfo(String data) {
    logger.info(data);
    sendEvent("info",data);
  }
  private void showScan(String data) {
    logger.info(data);
    sendEvent("scan",data);
  }
  private void showFail(String data) {
    logger.warn​(data);
    sendEvent("err",data);
  }
  private void sendEvent(String key,String data){
    WritableMap params = Arguments.createMap();
    params.putString(key, data);
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("uhfEvent", params);
  }

}