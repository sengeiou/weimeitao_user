package com.wmtc.chatmodule.adapter.holder;

import android.view.View;

import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.MessageAdapter;
import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.base.OblMessageManager;
import com.wmtc.chatmodule.enity.TextMessage;
import com.wmtc.chatmodule.widget.GifTextView;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageTextHolder extends MessageBaseHolder<TextMessage> {


    public MessageTextHolder(View view, MessageAdapter adapter) {
        super(view, adapter);
    }

    @Override
    public void bindData(TextMessage msg, int position) {
        super.bindData(msg, position);
        GifTextView tvContentLeft = itemView.findViewById(R.id.tvTextLeft);
        GifTextView tvContentRight = itemView.findViewById(R.id.tvTextRight);
        tvContentLeft.setSpanText(OblMessageManager.mHandler, msg.content, true);
        tvContentRight.setSpanText(OblMessageManager.mHandler, msg.content, true);

        addOnClickListener(R.id.tvTextLeft)
                .addOnClickListener(R.id.tvTextRight);

    }
}
