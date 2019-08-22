package com.boogoob.uhf.protocol.cmd;

import com.boogoob.uhf.protocol.type.CommandType;
import com.boogoob.uhf.protocol.type.MemBank;
import com.boogoob.uhf.protocol.utils.ConvertUtils;

public class CmdTagSelect extends CmdFrame
{

    private int selParamTarget = 0;
    private int selParamAction = 0;
    private MemBank memBank = MemBank.EPC;

    private int ptr = 0x20;
    private int maskLen = 0x60;
    private boolean isTruncate = false;
    private int[] mask = new int[] { 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01, 0x01 };

    public CmdTagSelect(int target, int action, MemBank memBank, String mask)
    {
        this.selParamTarget = target;
        this.selParamAction = action;
        this.memBank = memBank;
        setMask(ConvertUtils.stringToInteger(mask));
    }
    
    public CmdTagSelect(String epc)
    {
        super();
        setMask(ConvertUtils.stringToInteger(epc));
    }

    public void setMask(int[] mask)
    {
        this.mask = mask;
        this.maskLen = mask.length * 8;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.TAG_SELECT;
    }

    @Override
    public int getLength()
    {
        return 7 + mask.length;
    }

    @Override
    public int[] getParameter()
    {
        int[] parameter = new int[7 + mask.length];
        int index = 0;
        parameter[index++] = (selParamTarget << 5) + (selParamAction << 2)
                + memBank.toTransitiveInteger(); // SelParam
        parameter[index++] = ptr / 0x1000000;
        parameter[index++] = ptr % 0x1000000 / 0x10000;
        parameter[index++] = ptr % 0x10000 / 0x100;
        parameter[index++] = ptr % 0x100;
        parameter[index++] = maskLen;
        parameter[index++] = isTruncate ? 0x80 : 0x00;
        for (int i = 0; i < mask.length; i++)
        {
            parameter[index++] = mask[i];
        }
        return parameter;
    }
}
