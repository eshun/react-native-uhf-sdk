package com.uhf.protocol.sdk.cmd;

import com.uhf.protocol.sdk.type.CommandType;

public class CmdRfInputBlock extends CmdFrame
{
    @Override
    public CommandType getCommandType()
    {
        return CommandType.RF_INPUT_BLOCK;
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
