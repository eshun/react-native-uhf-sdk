package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;

public class CmdModemSet extends CmdFrame
{
    private int mixerG;
    private int ifG;
    private int threshold;

    public CmdModemSet(int mixerG, int ifG, int threshold)
    {
        super();
        this.mixerG = mixerG;
        this.ifG = ifG;
        this.threshold = threshold;
    }

    public int getMixerG()
    {
        return mixerG;
    }

    public void setMixerG(int mixerG)
    {
        this.mixerG = mixerG;
    }

    public int getIfG()
    {
        return ifG;
    }

    public void setIfG(int ifG)
    {
        this.ifG = ifG;
    }

    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.MODEM_SET;
    }

    @Override
    public int getLength()
    {
        return 4;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { mixerG, ifG, threshold / 0x100, threshold % 0x100 };
    }

    public static void main(String[] args)
    {}

}
