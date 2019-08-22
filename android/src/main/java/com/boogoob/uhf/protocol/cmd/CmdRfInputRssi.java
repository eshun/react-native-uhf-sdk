package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;

public class CmdRfInputRssi extends CmdFrame
{
    @Override
    public CommandType getCommandType()
    {
        return CommandType.RF_INPUT_RSSI;
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
