
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

import com.uhf.protocol.sdk.RespAndNotifyFactory;
import com.uhf.protocol.sdk.RespAndNotifyHandler;
import com.uhf.protocol.sdk.RespOrNotifyFrame;
import com.uhf.protocol.sdk.cmd.CmdDeviceInfo;
import com.uhf.protocol.sdk.cmd.CmdFrame;
import com.uhf.protocol.sdk.cmd.CmdPollingMulti;
import com.uhf.protocol.sdk.cmd.CmdPollingSingle;
import com.uhf.protocol.sdk.cmd.CmdPollingStop;
import com.uhf.protocol.sdk.cmd.CmdSelectModeSet;
import com.uhf.protocol.sdk.cmd.CmdTagDataRead;
import com.uhf.protocol.sdk.cmd.CmdTagDataWrite;
import com.uhf.protocol.sdk.cmd.CmdTagSelect;
import com.uhf.protocol.sdk.type.DeviceInfoType;
import com.uhf.protocol.sdk.utils.ConvertUtils;
import com.uhf.protocol.sdk.resp.RespDeviceInfo;
import com.uhf.protocol.sdk.resp.RespPollingSingle;
import com.uhf.protocol.sdk.resp.RespPollingStop;
import com.uhf.protocol.sdk.resp.RespSelectModeSet;
import com.uhf.protocol.sdk.resp.RespTagDataError;
import com.uhf.protocol.sdk.resp.RespTagDataRead;
import com.uhf.protocol.sdk.resp.RespTagDataWrite;
import com.uhf.protocol.sdk.resp.RespTagSelect;

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
      if (uhfService != null)
      {
        uhfService.setListener(null);
      }
    } catch (RemoteException e)
    {
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
      logger.info("on Service Connected!");
      try {
        uhfService = IUhfService.Stub.asInterface(service);
        uhfService.setListener(listener);
        if (uhfService.isPowerOn()) {

        } else {

        }
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      logger.info("on Service DisConnected!");
      uhfService = null;
    }

  };

  private IUhfListener listener = new IUhfListener.Stub() {

    @Override
    public void onPowerOn() throws RemoteException
    {
      logger.info("power on");
    }

    @Override
    public void onPowerOff() throws RemoteException
    {
      logger.info("power off");
    }

    @Override
    public void onResponse(byte[] resp) throws RemoteException
    {
      String s=ConvertUtils.bytesToString(resp);
      logger.error("onResponse:"+s);
      List<RespOrNotifyFrame> msgList = factory.receive(resp);
      for (RespOrNotifyFrame frame : msgList)
      {
        logger.info(frame.getClass().getSimpleName());
        //showResult(reactContext, frame.toBytes());
        frame.handleBy(protocolHandler);
      }
    }
  };

  private RespAndNotifyHandler getProtocolHandler()
  {
    return new RespAndNotifyHandler() {
      @Override
      public void handle(RespDeviceInfo resp)
      {
        logger.error("getInfo:"+resp.getInfo());
        logger.info(resp.getInfoType() + " " + resp.getInfo());
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

  public void init() {
    if (uhfService == null) {
      String packageName = "com.example.serialportuhf";//reactContext.getPackageName()  IUhfService.class.getPackage().getName()
      String className = "com.example.serialportuhf.aidl.IUhfService";//IUhfService.class.getName();
      logger.info("init："+packageName+"-"+className);

      Intent intent = new Intent();
      //intent.setComponent(new ComponentName(packageName, className));
      intent.setAction(className);
      intent.setPackage(packageName);

      boolean isBind=bindService(intent);
      logger.info(String.valueOf(isBind));
    }
  }

  @ReactMethod
  public void pause(){
    unbindService();
  }

  @ReactMethod
  public boolean getFirmware() {
    return write(new CmdDeviceInfo(DeviceInfoType.HARDWARE));
  }

  @ReactMethod
  public boolean getVersionInfo() {
    return write(new CmdDeviceInfo(DeviceInfoType.SOFTWARE));
  }

  @ReactMethod
  public boolean getMakerInfo() {
    return write(new CmdDeviceInfo(DeviceInfoType.MANUFACTURER));
  }

  @ReactMethod
  public boolean getEpc(){
    byte[] cmd = {(byte) 0xBB,0x00,0x22,0x00,0x00,0x22,0x7E};
    return write(cmd);
  }

  private void showResult(ReactApplicationContext reactContext,byte[] bytes) {
    String s = ConvertUtils.bytesToString(bytes);
    logger.error("showResult:" + s);
    WritableMap params = Arguments.createMap();
    params.putString("data", s);
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("showResult", params);
  }


}