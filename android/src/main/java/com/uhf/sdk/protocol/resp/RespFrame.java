package com.uhf.sdk.protocol.resp;

import com.uhf.sdk.protocol.AbstractCommand;
import com.uhf.sdk.protocol.RespOrNotifyFrame;
import com.uhf.sdk.protocol.type.FrameType;

public abstract class RespFrame extends RespOrNotifyFrame
{
    @Override
    public FrameType getFrameType()
    {
        return FrameType.RESP;
    }

}
