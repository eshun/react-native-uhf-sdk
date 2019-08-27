package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;

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
