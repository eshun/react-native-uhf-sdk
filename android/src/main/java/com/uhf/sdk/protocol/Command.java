package com.uhf.sdk.protocol;

import com.uhf.sdk.protocol.type.CommandType;
import com.uhf.sdk.protocol.type.FrameType;

public interface Command
{
    public static final int[] CARD = new int[] { 0x00, 0x00, 0x98, 0xFC, 0xA1,
            0x20, 0x15, 0x09, 0x25, 0x00, 0xE7, 0x55 };

    public static final int FIX_LENGTH = 7;// Header(1)+Type(1)+Command(1)+Length(2)+Checksum(1)+End(1)
    public static final int START = 0xFE;

    public static final int[] STEP1 = { 0xDB, 0xDE, 0xDF };
    public static final int[] STEP1_ACK = { 0xBF, 0xEF, 0xEF };
    public static final int[] STEP2 = { 0xFD, 0xFD, 0xFD };
    public static final int[] STEP3 = { 0xD3, 0xD3, 0xD3, 0xD3, 0xD3, 0xD3 };

    public static final int HEADER = 0xBB;
    public static final int END = 0x7E;

    public FrameType getFrameType();

    public CommandType getCommandType();

    public int getLength();

    public int[] getParameter();

    public byte[] toBytes();

    public void setContent(int[] content);

}
