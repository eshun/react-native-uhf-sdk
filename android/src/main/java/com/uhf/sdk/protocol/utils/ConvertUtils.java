package com.uhf.protocol.sdk.utils;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class ConvertUtils
{

    public static String bytesToString(byte[] bytes)
    {
        return bytesToString(bytes, bytes.length);
    }

    public static String bytesToString(byte[] bytes, int bytesLength)
    {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < bytesLength; ++i)
        {
            if ((bytes[i] < 16) && (bytes[i] >= 0))
                ss.append("0");
            ss.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return ss.toString().toUpperCase();
    }

    public static final String integerToString(int[] array)
    {
        return integerToString(array, array.length);
    }

    public static final String integerToString(int[] array, int arrayLength)
    {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < arrayLength; ++i)
        {
            if (array[i] < 0 || array[i] > 0xFF)
            {
                throw new IllegalArgumentException(
                        "not proper value to convert to byte string");
            }
            if (array[i] < 16)
            {
                ss.append("0");
            }
            ss.append(Integer.toHexString(array[i]));
        }
        return ss.toString().toUpperCase();
    }

    public static String bytesToString2(byte[] bytes, int bytesLength)
    {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < bytesLength; ++i)
        {
            int q = 0xff & bytes[i];
            if (q < 10)
            {
                ss.append("0");
            }
            ss.append(Integer.toHexString(q));
        }
        return ss.toString().toUpperCase();
    }

    public static final int[] bytesToInteger(byte[] bytes)
    {
        return bytesToInteger(bytes, bytes.length);
    }

    public static final int[] bytesToInteger(byte[] bytes, int bytesLength)
    {
        int[] result = new int[bytesLength];
        for (int i = 0; i < bytesLength; i++)
        {
            result[i] = 0xff & bytes[i];
        }
        return result;
    }

    public static final byte[] integersToBytes(int[] intArray)
    {
        return integersToBytes(intArray, intArray.length);
    }

    public static final byte[] integersToBytes(int[] intArray, int length)
    {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++)
        {
            result[i] = (byte) intArray[i];
        }
        return result;
    }

    public static final int[] stringToInteger(String s)
    {
        // String[] ss = s.split("(?<=\\G.{2})");
        if (s.length() % 2 == 0)
        {
            int[] array = new int[s.length() / 2];
            for (int i = 0; i < array.length; i++)
            {
                array[i] = Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
            }
            return array;
        } else
        {
            return new int[] {};
        }
    }
}
