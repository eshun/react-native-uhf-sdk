package com.uhf.protocol.resp;

import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.type.CommandType;

public class RespFreqHopping extends RespFrame
{

    private boolean isSuccess = false;

    public boolean isSuccess()
    {
        return isSuccess;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.FREQ_HOPPING;
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