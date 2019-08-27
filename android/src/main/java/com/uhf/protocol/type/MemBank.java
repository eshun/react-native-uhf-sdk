package com.uhf.protocol.type;

import java.util.HashMap;
import java.util.Map;

public enum MemBank
{
    RFU(0x00), EPC(0x01), TID(0x02), USER(0x03);
    private static final Map<Integer, MemBank> IntegerToEnum = new HashMap<Integer, MemBank>();

    static
    {
        for (MemBank dt : values())
        {
            IntegerToEnum.put(dt.intMemBank, dt);
        }
    }

    private Integer intMemBank;

    MemBank(Integer value)
    {
        this.intMemBank = value;
    }

    public Integer toTransitiveInteger()
    {
        return intMemBank;
    }

    public static MemBank fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }

    public int getSA()
    {
        switch (this)
        {
            case EPC:
                return 2;
            case USER:
                return 0;
            case RFU: // TODO:目前不知道偏移
            case TID:
            default:
                throw new IllegalArgumentException("should not be called");
        }
    }

    public int getDL(String epc)
    {
        switch (this)
        {
            case EPC:
                return epc.length() / 4;
            case USER:
                return 2;
            case RFU: // TODO:目前不知道偏移
            case TID:
            default:
                throw new IllegalArgumentException("should not be called");
        }
    }

}
