package com.uhf.sdk.protocol.cmd;

import com.uhf.sdk.protocol.type.CommandType;
import com.uhf.sdk.protocol.type.WorkRegionType;

public class CmdWorkRegionSet extends CmdFrame
{
    private WorkRegionType region;

    public CmdWorkRegionSet(WorkRegionType region)
    {
        super();
        if (region == null)
        {
            throw new IllegalArgumentException("work region can't be set to null!");
        }
        this.region = region;
    }

    @Override
    public CommandType getCommandType()
    {
        return CommandType.WORK_REGION;
    }

    @Override
    public int getLength()
    {
        return 1;
    }

    @Override
    public int[] getParameter()
    {
        return new int[] { region.toTransitiveInteger() };
    }

}
