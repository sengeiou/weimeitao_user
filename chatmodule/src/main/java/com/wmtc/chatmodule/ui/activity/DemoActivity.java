package com.wmtc.chatmodule.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.base.MessageBaseActivity;
import com.wmtc.chatmodule.base.MessageBaseInfo;
import com.wmtc.chatmodule.enity.ImageMessage;
import com.wmtc.chatmodule.enity.TextMessage;
import com.wmtc.chatmodule.enity.VoiceMessage;
import com.wmtc.chatmodule.util.MessageConst;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DemoActivity extends MessageBaseActivity {


    @Override
    protected int initRootLayout() {
        return R.layout.activity_demo;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        view.findViewById(R.id.tvBack).setOnClickListener(v -> finish());
    }

    @Override
    public void initMessageAdapter(RecyclerView recyclerView) {
        super.initMessageAdapter(recyclerView);
        mMessageAdapter.setOnItemChildClickListener((adapter, itemView, position) -> {
            if (R.id.tvTextLeft == itemView.getId() || R.id.tvTextRight == itemView.getId()) {
                ToastUtils.init().showInfoToast(mActivity, "你点我干啥");
            }
            return false;
        });
    }

    @Subscribe
    public void onEvent(VoiceMessage message) {
        LogUtil.e(message);
        addMessage(message);
    }


    @Subscribe
    public void onEvent(TextMessage message) {
        LogUtil.e(message);
        addMessage(message);
    }

    @Subscribe
    public void onEvent(ImageMessage message) {
        LogUtil.e(message);
        addMessage(message);
    }


    @Override
    public ArrayList<MessageBaseInfo> initMessageData() {
        ArrayList<MessageBaseInfo> messages = new ArrayList<>();
        messages.add(new TextMessage("[微笑][色][色][色]", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息9", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息10", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息10", MessageConst.TYPE_LEFT));
        TextMessage message = new TextMessage("asdwescxdazsxda", MessageConst.TYPE_RIGHT);
        message.createTime = "10:10:10";
        messages.add(message);
        messages.add(new VoiceMessage("http://qiniu.jplayer.top/voice.wav", 5, MessageConst.TYPE_RIGHT));
        messages.add(new VoiceMessage("http://qiniu.jplayer.top/voice.wav", 55, MessageConst.TYPE_RIGHT));
        messages.add(new TextMessage("我是一条文本消息2", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息3", MessageConst.TYPE_LEFT));
        messages.add(new ImageMessage("http://qiniu.jplayer.top/tuchongeter/19543071.jpg", MessageConst.TYPE_RIGHT));
        messages.add(new ImageMessage());
        messages.add(new TextMessage("我是一条文本消息10", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息4", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息6", MessageConst.TYPE_LEFT));
        messages.add(new VoiceMessage("http://qiniu.jplayer.top/voice.wav", 3, MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息1", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息7", MessageConst.TYPE_LEFT));
        messages.add(new TextMessage("我是一条文本消息8", MessageConst.TYPE_LEFT));
        messages.add(new VoiceMessage("http://qiniu.jplayer.top/voice.wav", 13, MessageConst.TYPE_LEFT));
        return messages;
    }

    private void addMessage(MessageBaseInfo message) {
        mMessageAdapter.addData(message);
        mRecyclerView.smoothScrollToPosition(mMessageAdapter.getData().size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
