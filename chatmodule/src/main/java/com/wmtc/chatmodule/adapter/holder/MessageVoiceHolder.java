package com.wmtc.chatmodule.adapter.holder;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.MessageAdapter;
import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.base.OblMessageManager;
import com.wmtc.chatmodule.enity.VoiceMessage;
import com.wmtc.chatmodule.util.MediaManager;
import com.wmtc.chatmodule.widget.GifTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageVoiceHolder extends MessageBaseHolder<VoiceMessage> {
    //录音相关
    private int animationRes = 0;
    private int res = 0;
    private AnimationDrawable animationDrawable = null;
    private ImageView animView;

    public MessageVoiceHolder(View view, MessageAdapter adapter) {
        super(view, adapter);
    }

    @Override
    public void bindData(VoiceMessage msg, int position) {
        super.bindData(msg, position);
        GifTextView tvTextLeft = itemView.findViewById(R.id.tvTextLeft);
        GifTextView tvTextRight = itemView.findViewById(R.id.tvTextRight);

        StringBuilder builder = new StringBuilder();
        for (long i = 0; i < msg.voiceSecond; i++) {
            if (i > 20) {
                break;
            }
            builder.append(" ");
        }
        tvTextLeft.setSpanText(OblMessageManager.mHandler,
                StringUtils.init().fixNullStr(String.valueOf(msg.voiceSecond) + "'" + builder.toString()), true);
        tvTextRight.setSpanText(OblMessageManager.mHandler,
                StringUtils.init().fixNullStr(builder.toString() + String.valueOf(msg.voiceSecond) + "'"), true);


        itemView.findViewById(R.id.llLeft).setOnClickListener(v -> {
            voiceMsgClick(msg);
        });
        itemView.findViewById(R.id.llRight).setOnClickListener(v -> {
            voiceMsgClick(msg);
        });
    }

    private void voiceMsgClick(VoiceMessage msg) {
        Map<ImageView, Integer> voiceMessageMap = mAdapter.mVoiceMessageMap;

        if (msg.isLeft()) {
            animView = itemView.findViewById(R.id.ivVoicePicLeft);
            animationRes = R.drawable.voice_left;
            res = R.drawable.icon_voice_left3;
        } else {
            animView = itemView.findViewById(R.id.ivVoicePicRight);
            animationRes = R.drawable.voice_right;
            res = R.drawable.icon_voice_right3;
        }
        for (Map.Entry<ImageView, Integer> integerEntry : voiceMessageMap.entrySet()) {
            integerEntry.getKey().setImageResource(integerEntry.getValue());
        }
        voiceMessageMap.put(animView, res);
        animView.setImageResource(animationRes);
        animationDrawable = (AnimationDrawable) animView.getDrawable();
        animationDrawable.start();
        MediaManager.playSound(msg.voicePath, mp -> {
            animView.setImageResource(res);
        });
    }
}
