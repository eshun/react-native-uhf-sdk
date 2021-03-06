package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;

public class CmdPollingSingle extends CmdFrame
{
    @Override
    public CommandType getCommandType()
    {
        return CommandType.POLLING_SINGLE;
    }

    @Override
    public int getLength()
    {
        return 0;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] {};
    }
}
