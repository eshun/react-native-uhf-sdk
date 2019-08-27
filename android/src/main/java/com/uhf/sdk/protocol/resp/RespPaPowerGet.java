package com.uhf.sdk.protocol.resp;

import com.uhf.sdk.protocol.RespAndNotifyHandler;
import com.uhf.sdk.protocol.type.CommandType;

public class RespPaPowerGet extends RespFrame
{

    private int paPower;// dBm值，转成参数时要*100

    public int getPaPower()
    {
        return paPower;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.PA_POWER_GET;
    }

    @Override
    public int getLength()
    {
        return 2;
    }

    @Override
    public int[] getParameter()
    {
        int value = paPower * 100;
        return new int[] { value / 0x100, value % 0x100 };
    }

    @Override
    public void setContent(int[] content)
    {
        paPower = content[0] * 0x100 + content[1];
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
