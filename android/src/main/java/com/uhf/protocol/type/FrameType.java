package com.uhf.protocol.type;

import java.util.HashMap;
import java.util.Map;

public enum FrameType
{
    CMD(0x00), RESP(0x01), NOTIFY(0x02);
    private static final Map<Integer, FrameType> IntegerToEnum = new HashMap<Integer, FrameType>();

    static
    {
        for (FrameType dt : values())
        {
            IntegerToEnum.put(dt.intFrameType, dt);
        }
    }

    private Integer intFrameType;

    FrameType(Integer value)
    {
        this.intFrameType = value;
    }

    public Integer toTransitiveInteger()
    {
        return intFrameType;
    }

    public static FrameType fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }

}
