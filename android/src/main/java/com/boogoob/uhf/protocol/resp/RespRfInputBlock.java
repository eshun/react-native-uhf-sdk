package com.boogoob.uhf.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.boogoob.uhf.protocol.RespAndNotifyHandler;
import com.boogoob.uhf.protocol.type.CommandType;
import com.boogoob.uhf.protocol.utils.ConvertUtils;

public class RespRfInputBlock extends RespFrame
{
    private int channelLow;
    private int channelHigh;
    private String jmr;

    @Override
    public CommandType getCommandType()
    {
        return CommandType.RF_INPUT_BLOCK;
    }

    @Override
    public int getLength()
    {
        return 2 + jmr.length() / 2;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = channelLow;
        parameter[index++] = channelHigh;
        for (int value : ConvertUtils.stringToInteger(jmr))
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
        jmr = new String(ConvertUtils.integerToString(ArrayUtils.subarray(
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

    public String getJmr()
    {
        return jmr;
    }

}
