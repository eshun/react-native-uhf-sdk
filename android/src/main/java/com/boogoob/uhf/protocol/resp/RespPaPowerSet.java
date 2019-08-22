package com.boogoob.uhf.protocol.resp;

import com.boogoob.uhf.protocol.RespAndNotifyHandler;
import com.boogoob.uhf.protocol.type.CommandType;

public class RespPaPowerSet extends RespFrame
{

    private boolean isSuccess = false;

    public boolean isSuccess()
    {
        return isSuccess;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.PA_POWER_SET;
    }

    @Override
    public int getLength()
    {
        return 2;
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
