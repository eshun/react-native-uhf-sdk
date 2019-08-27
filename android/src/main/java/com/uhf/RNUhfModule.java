
package com.uhf;

import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.uhf.protocol.RespAndNotifyFactory;
import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.RespOrNotifyFrame;
import com.uhf.protocol.cmd.CmdDeviceInfo;
import com.uhf.protocol.cmd.CmdFrame;
import com.uhf.protocol.cmd.CmdPollingMulti;
import com.uhf.protocol.cmd.CmdPollingSingle;
import com.uhf.protocol.cmd.CmdPollingStop;
import com.uhf.protocol.cmd.CmdSelectModeSet;
import com.uhf.protocol.cmd.CmdTagDataRead;
import com.uhf.protocol.cmd.CmdTagDataWrite;
import com.uhf.protocol.cmd.CmdTagSelect;
import com.uhf.protocol.type.DeviceInfoType;
import com.uhf.protocol.utils.ConvertUtils;
import com.uhf.protocol.resp.RespDeviceInfo;
import com.uhf.protocol.resp.RespPollingSingle;
import com.uhf.protocol.resp.RespPollingStop;
import com.uhf.protocol.resp.RespSelectModeSet;
import com.uhf.protocol.resp.RespTagDataError;
import com.uhf.protocol.resp.RespTagDataRead;
import com.uhf.protocol.resp.RespTagDataWrite;
import com.uhf.protocol.resp.RespTagSelect;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import com.uhf.aidl.IUhfListener;
import com.uhf.aidl.IUhfService;

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

  private void unbindService(ServiceConnection serviceConnection) {
    reactContext.unbindService(serviceConnection);
  }

  private boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
    return reactContext.bindService(intent, serviceConnection, i);
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

  private ServiceConnection sc = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      try {
        logger.info("on Service Connected!");
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
    public void onServiceDisconnected(ComponentName name)
    {
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

      @Override
      public void handle(RespPollingSingle resp)
      {
        String epc = ConvertUtils.integerToString(resp.getEpc());
        logger.info(epc);
//                    write(new CmdPollingStop());

      }

      @Override
      public void handle(RespPollingStop resp)
      {
        logger.info("polling stop result!"+System.currentTimeMillis());
      }

      public void handle(RespTagSelect resp) //first
      {
        logger.error("first");
        logger.info(resp.isSuccess()+" tag select result"+System.currentTimeMillis());
        if (resp.isSuccess())
        {
          write(new CmdSelectModeSet(2));
        }
      }

      public void handle(RespSelectModeSet resp) //second
      {
        logger.error("second");
        logger.info("read data select:"+ System.currentTimeMillis());
        if (resp.isSuccess())
        {

        } else
        {

        }
      }

      @Override
      public void handle(RespTagDataRead resp) //third
      {
        logger.error("third");
        logger.info("read data result:"+ System.currentTimeMillis());

//        handler.post(new Runnable() {
//          public void run()
//          {
//            if(epcTemp.length()>0)
//            {
//              isPollingNoResp = false;
//              isScanning = false;
//              handler.removeCallbacks(current);
//              tvReceived.setText("EPC: "+epcTemp +"//"+"TID: " + ConvertUtils.integerToString(resp.getDt()));
//              btnStartScan.setEnabled(true);
//
//              //handler.removeCallbacks(current);
//              isScanning = true;
//              isPollingNoResp = true;
//              btnStartScan.setEnabled(false);
//              handler.postDelayed(current, 300L);
//            }
//          }
//        });
      }

      @Override
      public void handle(RespTagDataWrite resp)
      {
//        handler.post(new Runnable() {
//          public void run()
//          {
//            onToast("Write success锛�" + "\n");
//          }
//        });
      }

      @Override
      public void handle(final RespTagDataError resp)
      {
        logger.info("tag data error:"+ System.currentTimeMillis());
//        isPollingNoResp = false;
//        //btnStartScan.setEnabled(true);
//        handler.post(new Runnable() {
//          public void run()
//          {
//            if(bTid)
//            {
//              isScanning = true;
//              tvReceived.setText("0");
//              getepc();
//            }
//            else
//            {
//              getepc();
//            }
//          }
//        });
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

  @ReactMethod
  public void init() {
    if (uhfService == null) {
      String packageName = reactContext.getPackageName();
      logger.info("init："+packageName);

      Intent intent = new Intent(reactContext, IUhfService.class);

      bindService(intent, sc, Context.BIND_AUTO_CREATE);
    }
  }

  public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
    // Retrieve all services that can match the given intent
    PackageManager pm = context.getPackageManager();
    List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

    // Make sure only one match was found
    if (resolveInfo == null || resolveInfo.size() != 1) {
      return null;
    }

    // Get component info and create ComponentName
    ResolveInfo serviceInfo = resolveInfo.get(0);
    String packageName = serviceInfo.serviceInfo.packageName;
    String className = serviceInfo.serviceInfo.name;
    ComponentName component = new ComponentName(packageName, className);

    // Create a new intent. Use the old one for extras and such reuse
    Intent explicitIntent = new Intent(implicitIntent);

    // Set the component to be explicit
    explicitIntent.setComponent(component);

    return explicitIntent;
  }

  @ReactMethod
  public void pause(){
    try {
      if (uhfService != null)
      {
        uhfService.setListener(null);
      }
      unbindService(sc);
    } catch (RemoteException e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
    }
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