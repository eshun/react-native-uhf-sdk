package com.uhf.protocol.cmd;

import com.uhf.protocol.type.CommandType;
import com.uhf.protocol.type.PreloadStep;
import com.uhf.protocol.utils.ConvertUtils;

public class CmdPreload extends CmdFrame
{
    private PreloadStep step;

    public CmdPreload(PreloadStep step)
    {
        super();
        this.step = step;
    }

    @Override
    public CommandType getCommandType()
    {
        throw new IllegalStateException("should not call this");
    }

    @Override
    public int getLength()
    {
        throw new IllegalStateException("should not call this");
    }

    @Override
    public int[] getParameter()
    {
        throw new IllegalStateException("should not call this");
    }

    @Override
    public byte[] toBytes()
    {
        return ConvertUtils.integersToBytes(step.getArray());
    }

}
