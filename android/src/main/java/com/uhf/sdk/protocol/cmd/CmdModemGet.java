package com.uhf.protocol.sdk.cmd;

import com.uhf.protocol.sdk.type.CommandType;

public class CmdModemGet extends CmdFrame
{
    @Override
    public CommandType getCommandType()
    {
        return CommandType.MODEM_GET;
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
