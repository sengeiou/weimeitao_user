package com.wmtc.chatmodule.adapter.holder;

import android.view.View;

import com.wmtc.chatmodule.adapter.MessageAdapter;
import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.enity.LocalMapMessage;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageMapHolder extends MessageBaseHolder<LocalMapMessage> {


    public MessageMapHolder(View view, MessageAdapter adapter) {
        super(view, adapter);
    }

    @Override
    public void bindData(LocalMapMessage msg, int position) {
        super.bindData(msg, position);
    }
}
