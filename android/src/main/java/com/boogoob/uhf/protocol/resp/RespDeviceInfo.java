package com.boogoob.uhf.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.boogoob.uhf.protocol.RespAndNotifyHandler;
import com.boogoob.uhf.protocol.type.CommandType;
import com.boogoob.uhf.protocol.type.DeviceInfoType;
import com.boogoob.uhf.protocol.utils.ConvertUtils;

public class RespDeviceInfo extends RespFrame
{
    private DeviceInfoType infoType;
    private String info;

    public DeviceInfoType getInfoType()
    {
        return infoType;
    }

    public String getInfo()
    {
        return info;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.DEVICE_INFO;
    }

    @Override
    public int getLength()
    {
        return 1 + info.getBytes().length;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = infoType.toTransitiveInteger();
        for (int value : ConvertUtils.bytesToInteger(info.getBytes()))
        {
            parameter[index++] = value;
        }
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        infoType = DeviceInfoType.fromInteger(content[0]);
        info = new String(ConvertUtils.integersToBytes(ArrayUtils.subarray(
                content, 1, content.length - 1)));
    }

    @Override
    public void handleBy(RespAndNotifyHandler handler)
    {
        if (handler != null)
        {
            handler.handle(this);
        }
    }

}
