package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;

public class CmdFreqHopping extends CmdFrame
{
    private boolean isAutoFreqHopping;

    @Override
    public CommandType getCommandType()
    {
        return CommandType.FREQ_HOPPING;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { isAutoFreqHopping ? 0xff : 0x00 };
    }

}
