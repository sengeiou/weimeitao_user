package com.wmtc.chatmodule.enity;

import com.wmtc.chatmodule.base.MessageBaseInfo;
import com.wmtc.chatmodule.util.MessageConst;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VoiceMessage extends MessageBaseInfo {
    public String voicePath;
    public long voiceSecond;

    public VoiceMessage(String voicePath, long voiceSecond, @MessageConst.Type String lOrR) {
        this.voicePath = voicePath;
        this.voiceSecond = voiceSecond;
        this.leftOrRight = lOrR;
    }

    public VoiceMessage() {
    }
}
