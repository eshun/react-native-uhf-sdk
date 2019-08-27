package com.uhf.protocol.sdk.cmd;

import com.uhf.protocol.sdk.type.CommandType;

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
