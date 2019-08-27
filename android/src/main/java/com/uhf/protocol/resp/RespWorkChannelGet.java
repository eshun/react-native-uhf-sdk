package com.uhf.protocol.resp;

import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.type.CommandType;

public class RespWorkChannelGet extends RespFrame
{
    private int channel;

    @Override
    public CommandType getCommandType()
    {
        return CommandType.WORK_CHANNEL_GET;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { channel };
    }

    @Override
    public void setContent(int[] content)
    {
        channel = content[0];
    }

    @Override
    public void handleBy(RespAndNotifyHandler handler)
    {
        if (handler != null)
        {
            handler.handle(this);
        }
    }

    public int getChannel()
    {
        return channel;
    }

}
