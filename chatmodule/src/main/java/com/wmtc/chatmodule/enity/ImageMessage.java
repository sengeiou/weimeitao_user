package com.wmtc.chatmodule.enity;

import com.wmtc.chatmodule.base.MessageBaseInfo;
import com.wmtc.chatmodule.util.MessageConst;

/**
 * Created by Obl on 2019/7/9.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ImageMessage extends MessageBaseInfo {
    public String imageUrl;

    public ImageMessage(String imageUrl, @MessageConst.Type String lOrR) {
        this.imageUrl = imageUrl;
        this.leftOrRight = lOrR;
    }

    public ImageMessage() {
    }
}
