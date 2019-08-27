package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;
import com.uhf.sdk.protocol.type.DeviceInfoType;

public class CmdDeviceInfo extends CmdFrame
{
    private DeviceInfoType info = DeviceInfoType.HARDWARE;

    public DeviceInfoType getInfo()
    {
        return info;
    }

    public void setInfo(DeviceInfoType info)
    {
        if (info != null)
        {
            this.info = info;
        }
    }

    public CmdDeviceInfo(DeviceInfoType info)
    {
        super();
        setInfo(info);
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.DEVICE_INFO;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { info.toTransitiveInteger() };
    }

}
