package com.boogoob.uhf.protocol;

import com.boogoob.uhf.protocol.utils.ConvertUtils;

public abstract class AbstractCommand implements Command
{
    @Override
    public byte[] toBytes()
    {
        int[] content = new int[FIX_LENGTH + getLength()];
        int checksum = 0;
        int index = 0;
        content[index++] = HEADER;
        content[index++] = getFrameType().toTransitiveInteger();
        content[index++] = getCommandType().toTransitiveInteger();
        content[index++] = getLength() / 0x100;
        content[index++] = getLength() % 0x100;
        for (int param : getParameter())
        {
            content[index++] = param;
        }
        for (int i = 1; i < index; i++)
        {
            checksum += content[i];
        }
        content[index++] = checksum % 0x100;
        content[index++] = END;
        return ConvertUtils.integersToBytes(content);
    }

    @Override
    public String toString()
    {
        return ConvertUtils.bytesToString(toBytes());
    }

}
