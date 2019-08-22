package com.boogoob.uhf.protocol.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public final class ProtocolUtils
{
    public static final int[] MIXER_TABLE = new int[] { 0, 3, 6, 9, 12, 15, 16 };
    public static final int[] IF_TABLE = new int[] { 12, 18, 21, 24, 27, 30,
            36, 40 }; // 索引为设置值，内容为增益值(dB)

    private static final String UNIT = "dBm";

    public static String paPowerToDbm(int paPower)
    {
        return Integer.toString(paPower / 100) + "dBm";
    }

    public static int dbmToPaPower(String dbm)
    {
        String[] array = dbm.split(UNIT);
        return NumberUtils.toInt(array[0]) * 100;
    }

    public static boolean isValidDbm(String dbm)
    {
        String[] array = dbm.split(UNIT);
        return NumberUtils.isNumber(array[0]);
    }

    public static boolean isValidHex(String str, int len)
    {
        return str.length() == len
                && StringUtils.containsOnly(str, "0123456789abcdefABCDEF");
    }
}
