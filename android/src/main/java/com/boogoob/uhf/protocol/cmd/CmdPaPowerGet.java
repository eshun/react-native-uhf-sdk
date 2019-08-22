package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;

public class CmdPaPowerGet extends CmdFrame
{

    @Override
    public CommandType getCommandType()
    {
        return CommandType.PA_POWER_GET;
    }

    @Override
    public int getLength()
    {
        return 0;
    }

    @Override
    public int[] getParameter()
    {
        return new int[]{};
    }

}
