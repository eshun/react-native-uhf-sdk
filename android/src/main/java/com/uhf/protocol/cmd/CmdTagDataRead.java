package com.uhf.protocol.cmd;

import com.uhf.protocol.type.CommandType;
import com.uhf.protocol.type.MemBank;

public class CmdTagDataRead extends CmdFrame
{
    private int[] accessPassword;
    private MemBank memBank = MemBank.USER;
    private int sa;
    private int dl;

    public CmdTagDataRead(int[] accessPassword, MemBank memBank, int sa, int dl)
    {
        this.accessPassword = accessPassword;
        this.memBank = memBank;
        this.sa = sa;
        this.dl = dl;
    }

    public int[] getAccessPassword()
    {
        return accessPassword;
    }

    public void setAccessPassword(int[] accessPassword)
    {
        this.accessPassword = accessPassword;
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

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_DATA_READ;
    }

    @Override
    public int getLength()
    {
        return 0x09;
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
        return parameter;
    }

    public static void main(String[] args)
    {}
}
