package com.uhf.sdk.protocol.resp;

import org.apache.commons.lang3.ArrayUtils;
import com.uhf.sdk.protocol.RespAndNotifyHandler;
import com.uhf.sdk.protocol.type.CommandType;
import com.uhf.sdk.protocol.utils.ConvertUtils;

public class RespTagDataWrite extends RespFrame
{
    private int[] pc;
    private int[] epc;
    private boolean isSuccess;

    public void setPc(String pc)
    {
        this.pc = ConvertUtils.stringToInteger(pc);
    }

    public void setEpc(String epc)
    {
        this.epc = ConvertUtils.stringToInteger(epc);
    }

    public boolean isSuccess()
    {
        return isSuccess;
    }

    public int[] getPc()
    {
        return pc;
    }

    public int[] getEpc()
    {
        return epc;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_DATA_READ;
    }

    @Override
    public int getLength()
    {
        return 2 + pc.length + epc.length; // dl表示多少word(两字节)的内容
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
        parameter[index++] = isSuccess ? 0x00 : 0x01;
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        this.pc = ArrayUtils.subarray(content, 1, 3);
        this.epc = ArrayUtils.subarray(content, 3, content.length - 2);
        this.isSuccess = (content[content.length - 2] == 0);
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
