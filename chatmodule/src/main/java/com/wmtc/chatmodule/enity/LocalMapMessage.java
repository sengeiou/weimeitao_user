package com.wmtc.chatmodule.enity;

import com.wmtc.chatmodule.base.MessageBaseInfo;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalMapMessage extends MessageBaseInfo {
    public String longlat;
    public String address;

    public LocalMapMessage(String longlat, String address) {
        this.longlat = longlat;
        this.address = address;
    }

    public LocalMapMessage() {
    }
}
