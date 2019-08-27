package com.uhf.protocol.sdk.resp;

import com.uhf.protocol.sdk.RespAndNotifyHandler;
import com.uhf.protocol.sdk.type.CommandType;

public class RespModemGet extends RespFrame
{
    private int mixerG;
    private int ifG;
    private int threshold;

    public int getMixerG()
    {
        return mixerG;
    }

    public int getIfG()
    {
        return ifG;
    }

    public int getThreshold()
    {
        return threshold;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.MODEM_GET;
    }

    @Override
    public int getLength()
    {
        return 4;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { ifG, mixerG, threshold / 0x100, threshold % 0x100 };
    }

    public static void main(String[] args)
    {
        RespModemGet respGetModem = new RespModemGet();
        respGetModem.mixerG = 3;
        respGetModem.ifG = 6;
        respGetModem.threshold = 0x1B0;
    }

    @Override
    public void setContent(int[] content)
    {
        mixerG = content[0];
        ifG = content[1];
        threshold = content[2] * 0x100 + content[3];
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
