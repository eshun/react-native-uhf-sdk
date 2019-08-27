
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
      showResult("on Service Connected!");
      try {
        uhfService = IUhfService.Stub.asInterface(service);
        uhfService.setListener(listener);
        if (uhfService.isPowerOn()) {
          showResult("power on!");
        } else {
          showResult("power off!");
        }
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      showResult("on Service DisConnected!");
      uhfService = null;
    }

  };

  private IUhfListener listener = new IUhfListener.Stub() {

    @Override
    public void onPowerOn() throws RemoteException {
      showResult("on power on!");
    }

    @Override
    public void onPowerOff() throws RemoteException {
      showResult("on power off!");
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

        showResult(data);
      }

      @Override
      public void handle(RespPollingSingle resp) {

      }

      @Override
      public void handle(RespPollingStop resp)
      {
        logger.info("polling stop result!"+System.currentTimeMillis());
      }

      public void handle(RespTagSelect resp) //first
      {
        logger.info(resp.isSuccess()+" tag select result"+System.currentTimeMillis());
        if (resp.isSuccess())
        {
          write(new CmdSelectModeSet(2));
        } else
        {
          //onFail();
        }
      }

      public void handle(RespSelectModeSet resp) //second
      {
        logger.info("read data select:"+ System.currentTimeMillis());
        if (resp.isSuccess())
        {
//          if (cmdAfterSelect != null)
//          {
//            write(cmdAfterSelect);
//            isPollingNoResp = true;
//            handler.removeCallbacks(current);
//            handler.postDelayed(current, 100L);
//            cmdAfterSelect = null;
//          }
        } else
        {
          //onFail();
        }
      }

      @Override
      public void handle(final RespTagDataRead resp) //third
      {
        logger.info("read data result:"+ System.currentTimeMillis());
      }

      @Override
      public void handle(final RespTagDataWrite resp)
      {
        logger.info("write data success:"+ System.currentTimeMillis());
      }

      @Override
      public void handle(final RespTagDataError resp)
      {
        logger.info("tag data error:"+ System.currentTimeMillis());
      }
    };
  }

  private boolean write(CmdFrame cmd) {
    if (uhfService != null && cmd != null) {
      try {
        logger.info("write: "
                + ConvertUtils.bytesToString(cmd.toBytes()));
        uhfService.write(cmd.toBytes());
        return true;
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }
    return false;
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
    return isBind;
    //}
  }

  @ReactMethod
  public boolean reset(){
    return init();
  }

  @ReactMethod
  public void pause() {
    unbindService();
  }

  @ReactMethod
  public boolean getFirmware() {
    return write(new CmdDeviceInfo(DeviceInfoType.HARDWARE));
  }

  @ReactMethod
  public boolean getVersion() {
    return write(new CmdDeviceInfo(DeviceInfoType.SOFTWARE));
  }

  @ReactMethod
  public boolean getMakerInfo() {
    return write(new CmdDeviceInfo(DeviceInfoType.MANUFACTURER));
  }

  @ReactMethod
  public boolean getEpc() {
    byte[] cmd = {(byte) 0xBB, 0x00, 0x22, 0x00, 0x00, 0x22, 0x7E};
    return write(cmd);
  }

  private void showResult(String data) {
    logger.info(data);
    WritableMap params = Arguments.createMap();
    params.putString("data", data);
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("showResult", params);
  }


}