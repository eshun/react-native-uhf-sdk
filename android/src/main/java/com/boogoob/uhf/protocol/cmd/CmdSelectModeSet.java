package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;

public class CmdSelectModeSet extends CmdFrame
{
    private int mode;

    public CmdSelectModeSet(int mode)
    {
        this.mode = mode;
    }
    
    @Override
    public CommandType getCommandType()
    {
        return CommandType.SELECT_MODE;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { mode };
    }
}
