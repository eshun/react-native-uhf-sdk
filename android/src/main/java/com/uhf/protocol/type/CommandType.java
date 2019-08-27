package com.uhf.protocol.type;

import java.util.HashMap;
import java.util.Map;
import com.uhf.protocol.RespOrNotifyFrame;
import com.uhf.protocol.resp.RespDeviceInfo;
import com.uhf.protocol.resp.RespFreqHopping;
import com.uhf.protocol.resp.RespModemGet;
import com.uhf.protocol.resp.RespModemSet;
import com.uhf.protocol.resp.RespPaPowerGet;
import com.uhf.protocol.resp.RespPaPowerSet;
import com.uhf.protocol.resp.RespPollingSingle;
import com.uhf.protocol.resp.RespPollingStop;
import com.uhf.protocol.resp.RespRfInputBlock;
import com.uhf.protocol.resp.RespRfInputRssi;
import com.uhf.protocol.resp.RespSelectModeSet;
import com.uhf.protocol.resp.RespTagDataError;
import com.uhf.protocol.resp.RespTagDataRead;
import com.uhf.protocol.resp.RespTagDataWrite;
import com.uhf.protocol.resp.RespTagSelect;
import com.uhf.protocol.resp.RespWorkChannelGet;
import com.uhf.protocol.resp.RespWorkChannelSet;
import com.uhf.protocol.resp.RespWorkRegion;

public enum CommandType
{
    DEVICE_INFO(0x03), POLLING_SINGLE(0x22), POLLING_MULTI(0x27), POLLING_STOP(
            0x28), TAG_SELECT(0x0C), SELECT_MODE(0x12), TAG_DATA_READ(0x39), TAG_DATA_WRITE(
            0x49), WORK_REGION(0x07), WORK_CHANNEL_SET(0xAB), WORK_CHANNEL_GET(0xAA), FREQ_HOPPING(0xAD), PA_POWER_GET(0xB7), PA_POWER_SET(0xB6), MODEM_GET(0xF1), MODEM_SET(
            0xF0), RF_INPUT_BLOCK(0xF2), RF_INPUT_RSSI(0xF3), TAG_DATA_ERROR(0xFF);
    private static final Map<Integer, CommandType> IntegerToEnum = new HashMap<>();

    static
    {
        for (CommandType dt : values())
        {
            IntegerToEnum.put(dt.intCommandType, dt);
        }
    }

    private Integer intCommandType;

    CommandType(Integer value)
    {
        this.intCommandType = value;
    }

    public Integer toTransitiveInteger()
    {
        return intCommandType;
    }

    public static CommandType fromInteger(Integer value)
    {
        return IntegerToEnum.get(value);
    }

    public RespOrNotifyFrame toResp()
    {
        switch (this)
        {
            case MODEM_GET:
                return new RespModemGet();
            case MODEM_SET:
                return new RespModemSet();
            case PA_POWER_GET:
                return new RespPaPowerGet();
            case PA_POWER_SET:
                return new RespPaPowerSet();
            case TAG_SELECT:
                return new RespTagSelect();
            case TAG_DATA_ERROR:
                return new RespTagDataError();
            case TAG_DATA_READ:
                return new RespTagDataRead();
            case TAG_DATA_WRITE:
                return new RespTagDataWrite();
            case DEVICE_INFO:
                return new RespDeviceInfo();
            case POLLING_SINGLE:
                return new RespPollingSingle();
            case POLLING_STOP:
                return new RespPollingStop();
            case RF_INPUT_BLOCK:
                return new RespRfInputBlock();
            case RF_INPUT_RSSI:
                return new RespRfInputRssi();
            case WORK_CHANNEL_GET:
                return new RespWorkChannelGet();
            case WORK_CHANNEL_SET:
                return new RespWorkChannelSet();
            case WORK_REGION:
                return new RespWorkRegion();
            case SELECT_MODE:
                return new RespSelectModeSet();
            case FREQ_HOPPING:
                return new RespFreqHopping();

        }
        throw new IllegalArgumentException("not proper command type:" + this);
    }

}
