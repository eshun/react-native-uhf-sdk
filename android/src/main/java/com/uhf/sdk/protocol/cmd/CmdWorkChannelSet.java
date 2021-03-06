package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;

public class CmdWorkChannelSet extends CmdFrame
{
    private int channel;

    public CmdWorkChannelSet(int channel)
    {
        super();
        this.channel = channel;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.WORK_CHANNEL_SET;
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

}
