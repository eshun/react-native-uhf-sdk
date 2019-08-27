package com.uhf.sdk.protocol;

public abstract class RespOrNotifyFrame extends AbstractCommand
{
    public abstract void handleBy(RespAndNotifyHandler handler);
}
