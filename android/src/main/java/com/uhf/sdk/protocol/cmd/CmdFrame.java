package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.AbstractCommand;
import com.uhf.sdk.protocol.type.FrameType;

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
