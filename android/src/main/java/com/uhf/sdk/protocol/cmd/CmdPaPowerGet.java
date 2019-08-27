package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;

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
