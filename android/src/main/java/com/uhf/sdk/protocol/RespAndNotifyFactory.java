package com.uhf.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import com.uhf.protocol.sdk.type.CommandType;
import com.uhf.protocol.sdk.type.FrameType;
import com.uhf.protocol.sdk.utils.ConvertUtils;

public class RespAndNotifyFactory
{
    List<RespOrNotifyFrame> respOrNotify = new ArrayList<>();

    private int[] remain = new int[] {};

    public List<RespOrNotifyFrame> receive(byte[] bytes)
    {
        respOrNotify.clear();
        int[] array = ConvertUtils.bytesToInteger(bytes);
        int[] all = ArrayUtils.addAll(remain, array);
        int headIndex = 0;
        int index = 0;
        if (index + 5 < all.length)
        {
            while (index + 5 < all.length)
            {
                if (all[index++] == Command.HEADER)
                {
                    headIndex = index - 1;
                    FrameType frameType = FrameType.fromInteger(all[index++]);
                    if (frameType == FrameType.RESP
                            || frameType == FrameType.NOTIFY)
                    {
                        CommandType commandType = CommandType
                                .fromInteger(all[index++]);
                        if (commandType != null)
                        {
                            RespOrNotifyFrame frame = commandType.toResp();
                            int length = all[index++] * 0x100;
                            length += all[index++];
                            if (length < 50)
                            {
                                if (all.length >= index + length + 2) // 根据长度判断是否已接收到结束标志
                                {
                                    if (all[index + length + 1] == Command.END) // 结束标志正确
                                    {
                                        frame.setContent(ArrayUtils.subarray(
                                                all, index, index + length + 1));
                                        respOrNotify.add(frame);
                                        index = index + length + 1 + 1;
                                        remain = new int[] {};
                                        continue;
                                    } else
                                    // 结束标志不正确
                                    {
                                        index = headIndex + 1;
                                        continue;
                                    }
                                } else
                                {
                                    remain = ArrayUtils.subarray(all,
                                            headIndex, all.length);
                                    return respOrNotify;
                                }
                            } else
                            {
                                index = headIndex + 1;
                            }
                        }
                    }
                }
            }
        }
        remain = ArrayUtils.subarray(all, index, all.length);
        return respOrNotify;
    }
}
