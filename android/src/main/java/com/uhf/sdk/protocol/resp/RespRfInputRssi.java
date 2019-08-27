package com.uhf.sdk.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.uhf.sdk.protocol.RespAndNotifyHandler;
import com.uhf.sdk.protocol.type.CommandType;
import com.uhf.sdk.protocol.utils.ConvertUtils;

public class RespRfInputRssi extends RespFrame
{
    private int channelLow;
    private int channelHigh;
    private String rssi;

    @Override
    public CommandType getCommandType()
    {
        return CommandType.RF_INPUT_RSSI;
    }

    @Override
    public int getLength()
    {
        return 2 + rssi.length()/2;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = channelLow;
        parameter[index++] = channelHigh;
        for (int value : ConvertUtils.stringToInteger(rssi))
        {
            parameter[index++] = value;
        }
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        channelLow = content[0];
        channelHigh = content[1];
        rssi = new String(ConvertUtils.integerToString(ArrayUtils.subarray(
                content, 2, content.length - 1)));
    }

    @Override
    public void handleBy(RespAndNotifyHandler handler)
    {
        if (handler != null)
        {
            handler.handle(this);
        }
    }

    public int getChannelLow()
    {
        return channelLow;
    }

    public int getChannelHigh()
    {
        return channelHigh;
    }

    public String getRssi()
    {
        return rssi;
    }

}
