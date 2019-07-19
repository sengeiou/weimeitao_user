package com.wmtc.chatmodule.enity;

import com.wmtc.chatmodule.base.MessageBaseInfo;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TextMessage extends MessageBaseInfo {
    public String content;

    public TextMessage(String content, String lOrR) {
        this.content = content;
        this.leftOrRight = lOrR;
    }

    public TextMessage() {
    }
}
