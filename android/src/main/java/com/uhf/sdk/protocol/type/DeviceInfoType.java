package com.uhf.protocol.sdk.type;

import java.util.HashMap;
import java.util.Map;

public enum DeviceInfoType
{
    HARDWARE(0x00), SOFTWARE(0x01), MANUFACTURER(0x02);
    private static final Map<Integer, DeviceInfoType> IntegerToEnum = new HashMap<>();

    static
    {
        for (DeviceInfoType dt : values())
        {
            IntegerToEnum.put(dt.intDeviceInfo, dt);
        }
    }

    private Integer intDeviceInfo;

    DeviceInfoType(Integer value)
    {
        this.intDeviceInfo = value;
    }

    public Integer toTransitiveInteger()
    {
        return intDeviceInfo;
    }

    public static DeviceInfoType fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }
    
}
