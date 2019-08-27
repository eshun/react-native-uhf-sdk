package com.uhf.protocol.sdk.cmd;

import com.uhf.protocol.sdk.type.CommandType;
import com.uhf.protocol.sdk.type.MemBank;
import com.uhf.protocol.sdk.utils.ConvertUtils;

public class CmdTagDataWrite extends CmdFrame
{
    private int[] accessPassword = new int[]{0,0,0,0};
    private MemBank memBank = MemBank.USER;
    private int sa;
    private int dl;
    private int[] dt;

    public CmdTagDataWrite(int[] accessPassword, MemBank memBank, int sa, int dl,
            int[] dt)
    {
        super();
        this.accessPassword = accessPassword;
        this.memBank = memBank;
        this.sa = sa;
        this.dl = dl;
        this.dt = dt;
    }

    public CmdTagDataWrite(MemBank memBank, String dt)
    {
        setMemBank(memBank, dt);
        setDt(dt);
    }

    public void setDt(String dt)
    {
        this.dt = ConvertUtils.stringToInteger(dt);
    }

    public MemBank getMemBank()
    {
        return memBank;
    }

    public void setMemBank(MemBank memBank, String dt)
    {
        this.memBank = memBank;
        this.sa = memBank.getSA();
        this.dl = memBank.getDL(dt);
    }

    public int[] getAccessPassword()
    {
        return accessPassword;
    }

    public void setAccessPassword(int[] accessPassword)
    {
        this.accessPassword = accessPassword;
    }

    public int[] getDt()
    {
        return dt;
    }

    public void setDt(int[] dt)
    {
        if (dt != null && dt.length == dl * 2)
        {
            this.dt = dt;
        } else
        {
            throw new IllegalArgumentException(
                    "dt is null or its length doesn't match with dl");
        }
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_DATA_WRITE;
    }

    @Override
    public int getLength()
    {
        return 0x09 + dl * 2; // dl表示多少word(两字节)的内容
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        for (int i : accessPassword)
        {
            parameter[index++] = i;
        }
        parameter[index++] = memBank.toTransitiveInteger();
        parameter[index++] = sa / 0x100;
        parameter[index++] = sa % 0x100;
        parameter[index++] = dl / 0x100;
        parameter[index++] = dl % 0x100;
        for (int value : dt)
        {
            parameter[index++] = value;
        }
        return parameter;
    }

    public static void main(String[] args)
    {}
}
