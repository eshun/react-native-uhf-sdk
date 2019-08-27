package com.uhf.protocol.sdk.type;

import java.util.HashMap;
import java.util.Map;

public enum WorkRegionType
{
    CN_900(0x01, 920125, 1, 250, 20), US(0x02, 902250, 1, 500, 52), EU(0x03,
            865100, 1, 200, 15), CN_800(0x04, 840125, 1, 250, 20), KR(0x06,
            917100, 1, 200, 32);

    private int freqMin;
    private int freqMax;
    private int step;
    private int count;

    private static final Map<Integer, WorkRegionType> IntegerToEnum = new HashMap<>();

    public boolean isPossibleFreq(int freq)
    {
        return freq >= freqMin && ((freq - freqMin) / step < count)
                && ((freq - freqMin) % step == 0);
    }

    public int getChannel(int freq)
    {
        return (freq - freqMin) / step;
    }

    public int getCount()
    {
        return count;
    }

    static
    {
        for (WorkRegionType dt : values())
        {
            IntegerToEnum.put(dt.intWorkRegionType, dt);
        }
    }

    private Integer intWorkRegionType;

    WorkRegionType(Integer value, int freqMin, int freqMax, int step, int count)
    {
        this.intWorkRegionType = value;
        this.freqMin = freqMin;
        this.freqMax = freqMax;
        this.step = step;
        this.count = count;
    }

    public Integer toTransitiveInteger()
    {
        return intWorkRegionType;
    }

    public static WorkRegionType fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }

    public static WorkRegionType getRegion(int freq)
    {
        WorkRegionType region = null;
        for (WorkRegionType wrt : values())
        {
            if (wrt.isPossibleFreq(freq))
            {
                region = wrt;
                break;
            }
        }
        return region;
    }

}
