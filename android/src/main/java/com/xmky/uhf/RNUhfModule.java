
package com.xmky.uhf;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.boogoob.uhf.protocol.cmd.CmdDeviceInfo;
import com.boogoob.uhf.protocol.cmd.CmdFrame;
import com.boogoob.uhf.protocol.type.DeviceInfoType;
import com.boogoob.uhf.protocol.utils.ConvertUtils;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.example.serialportuhf.aidl.IUhfListener;
import com.example.serialportuhf.aidl.IUhfService;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RNUhfModule extends ReactContextBaseJavaModule {
  protected static final Logger logger = LoggerFactory
          .getLogger(RNUhfModule.class.getSimpleName());

  private final ReactApplicationContext reactContext;

  private IUhfService uhfService; // 注意可能为null

  public RNUhfModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNUhf";
  }

  private ServiceConnection sc = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      uhfService = IUhfService.Stub.asInterface(service);
      try {
        uhfService.setListener(listener);
        if (uhfService.isPowerOn()) {

        } else {

        }
      } catch (RemoteException e) {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
      uhfService = null;
    }

  };

  private IUhfListener listener = new IUhfListener.Stub() {

    @Override
    public void onPowerOn() throws RemoteException
    {
      logger.info("on power on!");
    }

    @Override
    public void onPowerOff() throws RemoteException
    {
      logger.info("on power off");
    }

    @Override
    public void onResponse(byte[] resp) throws RemoteException
    {
      logger.info(ConvertUtils.bytesToString(resp));
//      List<RespOrNotifyFrame> msgList = factory.receive(resp);
//      for (RespOrNotifyFrame frame : msgList)
//      {
//        showResult(frame.toBytes());
//        logger.info(frame.getClass().getSimpleName());
//        frame.handleBy(protocolHandler);
//      }
    }
  };

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

  @ReactMethod
  public void getFirmware() {
    write(new CmdDeviceInfo(DeviceInfoType.HARDWARE));
  }

  @ReactMethod
  public void getVersionInfo() {
    write(new CmdDeviceInfo(DeviceInfoType.SOFTWARE));
  }

  @ReactMethod
  public void getMakerInfo() {
    write(new CmdDeviceInfo(DeviceInfoType.MANUFACTURER));
  }



}