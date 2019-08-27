package com.uhf.protocol.type;

public enum PreloadStep
{
    STEP0, STEP1, STEP2, STEP3;

    // public static final int START = 0xFE;
    // public static final int[] STEP1 = { 0xDB, 0xDE, 0xDF };
    // public static final int[] STEP2 = { 0xFD, 0xFD, 0xFD };
    // public static final int[] STEP3 = { 0xD3, 0xD3, 0xD3, 0xD3, 0xD3, 0xD3 };
    public int[] getArray()
    {
        switch (this)
        {
            case STEP0:
                return new int[] { 0xFE };
            case STEP1:
                return new int[] { 0xDB };
            case STEP2:
                return new int[] { 0xFD };
            case STEP3:
                return new int[] { 0xD3, 0xD3, 0xD3, 0xD3, 0xD3, 0xD3 };
        }
        throw new IllegalArgumentException("should not happen");
    }
}
