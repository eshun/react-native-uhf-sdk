package com.uhf.protocol.sdk.resp;

import com.uhf.protocol.sdk.AbstractCommand;
import com.uhf.protocol.sdk.RespOrNotifyFrame;
import com.uhf.protocol.sdk.type.FrameType;

public abstract class RespFrame extends RespOrNotifyFrame
{
    @Override
    public FrameType getFrameType()
    {
        return FrameType.RESP;
    }

}
