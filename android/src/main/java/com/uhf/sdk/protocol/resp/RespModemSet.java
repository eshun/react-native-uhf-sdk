package com.uhf.protocol.sdk.resp;

import com.uhf.protocol.sdk.RespAndNotifyHandler;
import com.uhf.protocol.sdk.type.CommandType;

public class RespModemSet extends RespFrame
{
    private boolean isSuccess = false;

    public boolean isSuccess()
    {
        return isSuccess;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.MODEM_SET;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { isSuccess ? 0 : 1 };
    }

    @Override
    public void setContent(int[] content)
    {
        isSuccess = (content[0] == 0);
    }

    @Override
    public void handleBy(RespAndNotifyHandler handler)
    {
        if (handler != null)
        {
            handler.handle(this);
        }
    }
}
