package com.uhf.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.type.CommandType;
import com.uhf.protocol.type.FrameType;
import com.uhf.protocol.utils.ConvertUtils;

public class RespPollingSingle extends RespFrame
{
    private int rssi;
    private int[] pc;
    private int[] epc;
    private int[] crc;

    public int getRssi()
    {
        return rssi;
    }

    public int[] getPc()
    {
        return pc;
    }

    public int[] getEpc()
    {
        return epc;
    }

    public int[] getCrc()
    {
        return crc;
    }

    @Override
    public FrameType getFrameType()
    {
        return FrameType.NOTIFY;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.POLLING_SINGLE;
    }

    @Override
    public int getLength()
    {
        return 1 + pc.length + epc.length + crc.length;// 1是rssi长度
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = rssi;
        for (int value : pc)
        {
            parameter[index++] = value;
        }
        for (int value : epc)
        {
            parameter[index++] = value;
        }
        for (int value : crc)
        {
            parameter[index++] = value;
        }
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        rssi = content[0];
        pc = ArrayUtils.subarray(content, 1, 3);
        epc = ArrayUtils.subarray(content, 3, content.length - 3);
        crc = ArrayUtils.subarray(content, content.length - 3,
                content.length - 1);
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
