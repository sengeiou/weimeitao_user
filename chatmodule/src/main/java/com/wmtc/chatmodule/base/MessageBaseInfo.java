package com.wmtc.chatmodule.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.chatmodule.enity.CustomHolder;
import com.wmtc.chatmodule.util.MessageConst;

import java.util.Map;

/**
 * Created by Obl on 2019/7/4.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class MessageBaseInfo implements MultiItemEntity {


    public String extra;

    public String createTime;
    public int msgType;
    public String msgId;
    public int msgState;
    public String msgKey;

    public String fromName;
    public String fromAvatar;
    public String fromId;

    public String targetName;
    public String targetAvatar;
    public String targetId;

    @MessageConst.Type
    public String leftOrRight = MessageConst.TYPE_LEFT;

    public boolean isLeft() {
        return MessageConst.TYPE_LEFT.equals(leftOrRight);
    }

    @Override
    public final int getItemType() {
        for (Map.Entry<Integer, CustomHolder> entry : OblMessageManager.mHolderMap.entrySet()) {
            String name = entry.getValue().msg.getName();
            String thisName = getClass().getName();
            if (thisName.equals(name)) {
                return entry.getKey();
            }
        }
        return msgType;
    }

}
