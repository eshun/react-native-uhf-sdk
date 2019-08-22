package com.boogoob.uhf.protocol.resp;

import com.boogoob.uhf.protocol.RespAndNotifyHandler;
import com.boogoob.uhf.protocol.type.CommandType;

public class RespTagSelect extends RespFrame
{
    private boolean isSuccess;

    public boolean isSuccess()
    {
        return isSuccess;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_SELECT;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { isSuccess ? 1 : 0 };
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
