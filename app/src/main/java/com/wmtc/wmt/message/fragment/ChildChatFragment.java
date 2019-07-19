package com.wmtc.wmt.message.fragment;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.wmtc.wmt.message.adapter.ChildChatAdapter;
import com.wmtc.wmt.message.bean.MessageBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChildChatFragment extends SuperBaseFragment {
    private ChildChatAdapter mAdapter;
    public String type;

    @Override
    public int initLayout() {
        return R.layout.fragment_child_message;
    }

    @Override
    protected void initData(View rootView) {
        JMessageClient.registerEventReceiver(this);
        initRefreshStatusView(rootView);
        mAdapter = new ChildChatAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position1) -> {
            MessageBean.DataBean bean = mAdapter.getData().get(position1);
            Bundle bundle = new Bundle();
            bundle.putString("name", bean.chatId);
            ActivityUtils.init().start(mActivity, CustomChatActivity.class, bean.messageTitle, bundle);

        });
    }

    public void onEventMainThread(MessageEvent event) {
        getChatList();
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        getChatList();
    }

    public void requestList() {
        showLoading();
        getChatList();
    }

    private void getChatList() {
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (conversationList != null) {
            ArrayList<MessageBean.DataBean> data = new ArrayList<>();
            for (Conversation conversation : conversationList) {
                MessageBean.DataBean dataBean = new MessageBean.DataBean();
                Message latestMessage = conversation.getLatestMessage();
                /* if ("admin".equals(conversation.getTargetId())) {
                    continue;
                }*/
                if (latestMessage != null) {
                    Date date = new Date();
                    date.setTime(latestMessage.getCreateTime());
                    dataBean.createTime = DateUtils.getSpecifyDate(date, DateUtils.FORMAT_YYYY_MM_DD_HH_MI_SS);
                    dataBean.messageTitle = conversation.getTitle();
                    dataBean.chatId = conversation.getTargetId();
                    if (latestMessage.getContentType().equals(ContentType.text)) {
                        TextContent textContent = (TextContent) latestMessage.getContent();
                        dataBean.messageContent = textContent.getText();
                    } else if (latestMessage.getContentType().equals(ContentType.custom)) {
                        dataBean.messageContent = "[订单消息]";
                    } else if (latestMessage.getContentType().equals(ContentType.image)) {
                        dataBean.messageContent = "[图片消息]";
                    }else if (latestMessage.getContentType().equals(ContentType.voice)) {
                        dataBean.messageContent = "[语音消息]";
                    }
                }
                data.add(dataBean);
            }
            mAdapter.setNewData(data);
            LogUtil.e("-------------");
        } else {
            showEmpty();
        }
        LogUtil.e(conversationList);
        responseSuccess();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
