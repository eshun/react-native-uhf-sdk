package com.uhf.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.type.CommandType;
import com.uhf.protocol.utils.ConvertUtils;

public class RespTagDataRead extends RespFrame
{
    private int[] pc;
    private int[] epc;
    private int[] dt;

    public void setPc(String pc)
    {
        this.pc = ConvertUtils.stringToInteger(pc);
    }

    public void setEpc(String epc)
    {
        this.epc = ConvertUtils.stringToInteger(epc);
    }

    public int[] getPc()
    {
        return pc;
    }

    public void setPc(int[] pc)
    {
        this.pc = pc;
    }

    public int[] getEpc()
    {
        return epc;
    }

    public void setEpc(int[] epc)
    {
        this.epc = epc;
    }

    public int[] getDt()
    {
        return dt;
    }

    public void setDt(int[] dt)
    {
        if (dt != null)
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
        return CommandType.TAG_DATA_READ;
    }

    @Override
    public int getLength()
    {
        return 1 + pc.length + epc.length + dt.length;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = pc.length + epc.length;
        for (int value : pc)
        {
            parameter[index++] = value;
        }
        for (int value : epc)
        {
            parameter[index++] = value;
        }
        for (int value : dt)
        {
            parameter[index++] = value;
        }
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        int lenOfPcAndEpc = content[0];
        pc = ArrayUtils.subarray(content, 1, 3);
        epc = ArrayUtils.subarray(content, 3, lenOfPcAndEpc + 1);
        dt = ArrayUtils
                .subarray(content, lenOfPcAndEpc + 1, content.length - 1);
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
