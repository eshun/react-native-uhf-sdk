package com.uhf.protocol.resp;

import com.uhf.protocol.AbstractCommand;
import com.uhf.protocol.RespOrNotifyFrame;
import com.uhf.protocol.type.FrameType;

public abstract class RespFrame extends RespOrNotifyFrame
{
    @Override
    public FrameType getFrameType()
    {
        return FrameType.RESP;
    }

}
