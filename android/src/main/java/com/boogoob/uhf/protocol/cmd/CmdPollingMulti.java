package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;

public class CmdPollingMulti extends CmdFrame
{
    private int count;

    public CmdPollingMulti(int count)
    {
        super();
        setCount(count);
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.POLLING_MULTI;
    }

    @Override
    public int getLength()
    {
        return 3;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { 0x22, count / 0x100, count % 0x100 };
    }

}
