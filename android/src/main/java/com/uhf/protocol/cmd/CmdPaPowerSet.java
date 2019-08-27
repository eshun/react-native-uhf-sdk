package com.uhf.protocol.cmd;

import com.uhf.protocol.type.CommandType;

public class CmdPaPowerSet extends CmdFrame
{
    private int paPower;    //除以100后才是真实dbm

    public CmdPaPowerSet(int paPower)   //需要使用乘以100后的值
    {
        super();
        setPaPower(paPower);
    }

    public int getPaPower()
    {
        return paPower;
    }

    public void setPaPower(int paPower)
    {
        this.paPower = paPower;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.PA_POWER_SET;
    }

    @Override
    public int getLength()
    {
        return 2;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { paPower / 0x100, paPower % 0x100 };
    }

}
