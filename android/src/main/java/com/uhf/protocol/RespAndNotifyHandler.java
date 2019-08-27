package com.uhf.protocol;

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

public class RespAndNotifyHandler
{
    public void handle(RespModemGet respModemGet)
    {}

    public void handle(RespTagDataWrite respTagDataWrite)
    {}

    public void handle(RespPaPowerSet respPaPowerSet)
    {}

    public void handle(RespPaPowerGet respPaPowerGet)
    {}

    public void handle(RespTagSelect respSetSelectParam)
    {}

    public void handle(RespTagDataError respTagDataError)
    {}

    public void handle(RespTagDataRead respTagDataRead)
    {}

    public void handle(RespDeviceInfo respDeviceInfo)
    {}

    public void handle(RespPollingSingle respPollingSingle)
    {}

    public void handle(RespModemSet respModemSet)
    {}

    public void handle(RespPollingStop respPollingStop)
    {}

    public void handle(RespWorkChannelGet respWorkChannelGet)
    {}

    public void handle(RespWorkChannelSet respWorkChannelSet)
    {}

    public void handle(RespWorkRegion respWorkRegion)
    {}

    public void handle(RespRfInputBlock respRfInputBlock)
    {}

    public void handle(RespRfInputRssi respRfInputRssi)
    {}

    public void handle(RespSelectModeSet respSelectModeSet)
    {}

    public void handle(RespFreqHopping respFreqHopping)
    {}
}
