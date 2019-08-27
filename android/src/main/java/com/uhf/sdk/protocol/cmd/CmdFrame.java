package com.uhf.protocol.sdk.cmd;

import com.uhf.protocol.sdk.AbstractCommand;
import com.uhf.protocol.sdk.type.FrameType;

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
