package com.wmtc.wmt.appoint.event;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PayOffLineEvent {
    public int aprice;
    public String type;

    public PayOffLineEvent(int anInt, String type) {
        this.aprice = anInt;
        this.type = type;

    }
}
