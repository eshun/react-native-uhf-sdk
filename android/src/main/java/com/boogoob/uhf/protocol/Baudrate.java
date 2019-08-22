package com.boogoob.uhf.protocol;

import java.util.HashMap;
import java.util.Map;

public enum Baudrate
{
    B9600(0xb0), B19200(0xb1), B28800(0xb2), B38400(0xb3), B57600(0xb4), B115200(
            0xb5);
    private static final Map<Integer, Baudrate> IntegerToEnum = new HashMap<Integer, Baudrate>();

    static
    {
        for (Baudrate dt : values())
        {
            IntegerToEnum.put(dt.intBaudrate, dt);
        }
    }

    private Integer intBaudrate;

    Baudrate(Integer value)
    {
        this.intBaudrate = value;
    }

    public Integer toTransitiveInteger()
    {
        return intBaudrate;
    }

    public static Baudrate fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }

}
