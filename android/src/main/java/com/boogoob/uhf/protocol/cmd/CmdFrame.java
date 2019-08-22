package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.AbstractCommand;
import com.boogoob.uhf.protocol.type.FrameType;

public abstract class CmdFrame extends AbstractCommand
{
    @Override
    public FrameType getFrameType()
    {
        return FrameType.CMD;
    }
    
    @Override
    public final void setContent(int[] content)
    {
        //TODO:not in prior
    }
}
