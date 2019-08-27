package com.uhf.protocol.resp;

import com.uhf.protocol.RespAndNotifyHandler;
import com.uhf.protocol.type.CommandType;
import com.uhf.protocol.utils.ConvertUtils;

public class RespTagDataError extends RespFrame
{
    private int[] pc;
    private int[] epc; // 找不到标签或epc不对时将仅返回errorCode，不返回此项
    private int errorCode; // 本条本身为失败响应，必有errorCode

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

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

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_DATA_ERROR;
    }

    @Override
    public int getLength()
    {
        int len = 1; // errorCode位

        if (epc != null && epc.length > 0 && pc != null && pc.length > 0)
        {
            len += 1 + pc.length + epc.length; // 1是长度位
        }
        return len;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[getLength()];
        int index = 0;
        parameter[index++] = errorCode;
        if (pc != null && pc.length != 0 && epc != null && epc.length != 0)
        {
            parameter[index++] = pc.length + epc.length;
            for (int value : pc)
            {
                parameter[index++] = value;
            }
            for (int value : epc)
            {
                parameter[index++] = value;
            }
        }
        return parameter;
    }

    @Override
    public void setContent(int[] content)
    {
        errorCode = content[0];
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
