package com.boogoob.uhf.protocol.resp;

import com.boogoob.uhf.protocol.AbstractCommand;
import com.boogoob.uhf.protocol.RespOrNotifyFrame;
import com.boogoob.uhf.protocol.type.FrameType;

public abstract class RespFrame extends RespOrNotifyFrame
{
    @Override
    public FrameType getFrameType()
    {
        return FrameType.RESP;
    }

}
