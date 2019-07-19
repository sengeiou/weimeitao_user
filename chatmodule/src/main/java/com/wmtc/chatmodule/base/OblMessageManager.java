package com.wmtc.chatmodule.base;

import android.app.Application;
import android.os.Handler;

import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.holder.MessageImageHolder;
import com.wmtc.chatmodule.adapter.holder.MessageTextHolder;
import com.wmtc.chatmodule.adapter.holder.MessageVoiceHolder;
import com.wmtc.chatmodule.enity.CustomHolder;
import com.wmtc.chatmodule.enity.ImageMessage;
import com.wmtc.chatmodule.enity.TextMessage;
import com.wmtc.chatmodule.enity.VoiceMessage;

import java.util.LinkedHashMap;
import java.util.Map;

import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OblMessageManager {
    private static OblMessageManager mChatInit;
    public static Map<Integer, CustomHolder> mHolderMap;
    public static Handler mHandler;

    private OblMessageManager() {
        mHolderMap = new LinkedHashMap<>();
        addCustomMsg(new CustomHolder<>(MessageTextHolder.class, R.layout.obl_message_text, TextMessage.class))
                .addCustomMsg(new CustomHolder<>(MessageImageHolder.class, R.layout.obl_message_image, ImageMessage.class))
                .addCustomMsg(new CustomHolder<>(MessageVoiceHolder.class, R.layout.obl_message_voice, VoiceMessage.class));
    }

    public static OblMessageManager init(Application application) {
        if (mChatInit == null) {
            mChatInit = new OblMessageManager();
            mHandler = new Handler(application.getMainLooper());
        }
        return mChatInit;
    }

    public OblMessageManager addCustomMsg(CustomHolder holderBean) {
        mHolderMap.put(mHolderMap.size(), holderBean);
        return this;
    }

    public void build() {
        LogUtil.e("==========================初始化消息==========================");
        for (Map.Entry<Integer, CustomHolder> entry : mHolderMap.entrySet()) {
            CustomHolder value = entry.getValue();
            LogUtil.e(entry.getKey(), value.holderClass.getName(), value.msg.getName());
        }
        LogUtil.e("===========================初始化消息=========================");
    }
}
