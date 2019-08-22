package com.boogoob.uhf.protocol;

public abstract class RespOrNotifyFrame extends AbstractCommand
{
    public abstract void handleBy(RespAndNotifyHandler handler);
}
