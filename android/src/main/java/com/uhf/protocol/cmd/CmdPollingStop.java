package com.uhf.protocol.cmd;

import com.uhf.protocol.type.CommandType;

public class CmdPollingStop extends CmdFrame
{

    @Override
    public CommandType getCommandType()
    {
        return CommandType.POLLING_STOP;
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
