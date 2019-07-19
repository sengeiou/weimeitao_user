package com.wmtc.wmt.message.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.wmtc.chatmodule.adapter.ChatAdapter;
import com.wmtc.chatmodule.adapter.CommonFragmentPagerAdapter;
import com.wmtc.chatmodule.enity.ImageInfo;
import com.wmtc.chatmodule.enity.MessageInfo;
import com.wmtc.chatmodule.ui.activity.ScanImageActivity;
import com.wmtc.chatmodule.ui.fragment.MessageEmotionFragment;
import com.wmtc.chatmodule.ui.fragment.MessageFunctionFragment;
import com.wmtc.chatmodule.util.Constants;
import com.wmtc.chatmodule.util.GlobalOnItemClickManagerUtils;
import com.wmtc.chatmodule.util.MediaManager;
import com.wmtc.chatmodule.widget.EmotionInputDetector;
import com.wmtc.chatmodule.widget.NoScrollViewPager;
import com.wmtc.chatmodule.widget.StateButton;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.activity.ForumMeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.myapplication
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CustomChatActivity extends CommonWmtActivity {
    EasyRecyclerView mEasyRecyclerView;
    ImageView emotionVoice;
    EditText editText;
    TextView voiceText;
    ImageView emotionButton;
    ImageView emotionAdd;
    StateButton emotionSend;
    NoScrollViewPager viewpager;
    RelativeLayout emotionLayout;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private MessageEmotionFragment chatEmotionFragment;
    private MessageFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;
    private String mIdName;
    private HashMap<String, String> mGoodsCustom;

    @Override
    public int initAddLayout() {
        return R.layout.activity_jpush_chat;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        JMessageClient.registerEventReceiver(this);
        EventBus.getDefault().register(this);
        mIdName = mBundle.getString("name");
        LogUtil.e(mIdName + "==============================");
        mEasyRecyclerView = findViewById(R.id.chat_list);
        emotionVoice = findViewById(R.id.emotion_voice);
        editText = findViewById(R.id.edit_text);
        voiceText = findViewById(R.id.voice_text);
        emotionButton = findViewById(R.id.emotion_button);
        emotionAdd = findViewById(R.id.emotion_add);
        emotionSend = findViewById(R.id.emotion_send);
        viewpager = findViewById(R.id.viewpager);
        emotionLayout = findViewById(R.id.emotion_layout);
        initWidget();
        mTvToolRight.setVisibility(View.INVISIBLE);

        mGoodsCustom = (HashMap<String, String>) mBundle.getSerializable("goodsCustom");
        if (mGoodsCustom != null) {
            loadCustomSendData(mGoodsCustom, Constants.CHAT_ITEM_TYPE_SEND_GOODS);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    private void initWidget() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new MessageEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new MessageFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(mEasyRecyclerView)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEasyRecyclerView.setLayoutManager(layoutManager);
        mEasyRecyclerView.setAdapter(chatAdapter);
        mEasyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        Conversation conversation = JMessageClient.getSingleConversation(mIdName, mIdName.contains("B_") ?
                WmtApplication.B_JMKEY :
                WmtApplication.C_JMKEY);
        messageInfos = new ArrayList<>();
        if (conversation != null) {
            List<Message> allMessage = conversation.getAllMessage();
            if (allMessage != null) {
                loadData(allMessage);
            } else {
                createTextMsg();
            }
        } else {
            createTextMsg();
        }
    }

    private void loadData(List<Message> allMessage) {

        for (Message message : allMessage) {
            MessageInfo messageInfo = new MessageInfo();
            MessageContent content = message.getContent();
            if (content.getContentType().equals(ContentType.text)) {
                if (MessageDirect.receive.equals(message.getDirect())) {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                    messageInfo.setHeader(message.getFromUser().getAvatar());
                } else {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                    messageInfo.setHeader(WmtApplication.avatar);
                }
                TextContent textContent = (TextContent) content;
                messageInfo.setContent(textContent.getText());
                messageInfos.add(messageInfo);
            } else if (content.getContentType().equals(ContentType.custom)) {
                CustomContent customContent = (CustomContent) message.getContent();
                Map stringValues = customContent.getAllStringValues();
                loadCustomData(stringValues);
            } else if (content.getContentType().equals(ContentType.voice)) {
                if (MessageDirect.receive.equals(message.getDirect())) {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                    messageInfo.setHeader(message.getFromUser().getAvatar());
                } else {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                    messageInfo.setHeader(WmtApplication.avatar);
                }
                VoiceContent voiceContent = (VoiceContent) message.getContent();
                messageInfo.setFilepath(voiceContent.getLocalPath());
                messageInfo.setVoiceTime(voiceContent.getDuration());
                messageInfos.add(messageInfo);
                LogUtil.e(messageInfo);
            } else if (content.getContentType().equals(ContentType.image)) {
                if (MessageDirect.receive.equals(message.getDirect())) {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                    messageInfo.setHeader(message.getFromUser().getAvatar());
                } else {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                    messageInfo.setHeader(WmtApplication.avatar);
                }
                ImageContent voiceContent = (ImageContent) message.getContent();
                messageInfo.setImageUrl(voiceContent.getLocalThumbnailPath());
                messageInfos.add(messageInfo);
            }
        }
        LogUtil.e(allMessage);
        chatAdapter.addAll(messageInfos);
    }

    public void createTextMsg() {
        if ("admin".equals(mBundle.getString("name"))) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setContent("您好，有什么需要帮您");
            messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
            messageInfo.setHeader("");
            chatAdapter.add(messageInfo);
        }
    }


    private void loadCustomData(Map map) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent((String) map.get("title"));
        messageInfo.setHeader((String) map.get("price"));
        messageInfo.setMsgId((String) map.get("orderId"));
        messageInfo.setImageUrl(WmtApplication.url_host + map.get("url"));
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_GOODS);
        messageInfos.add(messageInfo);
    }

    private void loadCustomSendData(Map map, int type) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent((String) map.get("title"));
        messageInfo.setHeader((String) map.get("price"));
        messageInfo.setMsgId((String) map.get("orderId"));
        messageInfo.setImageUrl(WmtApplication.url_host + map.get("url"));
        messageInfo.setType(type);
        chatAdapter.add(messageInfo);
        chatAdapter.notifyDataSetChanged();
    }

    /**
     * 构造聊天数据
     */
    private void LoadData() {
        messageInfos = new ArrayList<>();

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent("你好，欢迎使用Rance的聊天界面框架");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpgg");
        messageInfos.add(messageInfo);

        MessageInfo messageInfo1 = new MessageInfo();
        messageInfo1.setFilepath("http://www.trueme.net/bb_midi/welcome.wav");
        messageInfo1.setVoiceTime(3000);
        messageInfo1.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo1.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        messageInfo1.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo1);

        MessageInfo messageInfo2 = new MessageInfo();
        messageInfo2.setImageUrl("http://img4.imgtn.bdimg.com/it/u=1800788429,176707229&fm=21&gp=0.jpg");
        messageInfo2.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo2.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo2);

        MessageInfo messageInfo3 = new MessageInfo();
        messageInfo3.setContent("[微笑][色][色][色]");
        messageInfo3.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo3.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        messageInfo3.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo3);

        chatAdapter.addAll(messageInfos);
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
            Bundle bundle = new Bundle();
            LogUtil.e(position, mIdName);
            if (Constants.CHAT_ITEM_TYPE_LEFT == chatAdapter.getViewType(position)) {
                if (mIdName != null && mIdName.length() > 3 && mIdName.contains("C_")) {
                    bundle.putString("id", mIdName.substring(2));
                }
            } else if (Constants.CHAT_ITEM_TYPE_RIGHT == chatAdapter.getViewType(position)) {
                bundle.putString("id", WmtApplication.id);
            }
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, bundle);
        }

        @Override
        public void onImageClick(View view, int position) {
            MessageInfo messageInfo = chatAdapter.getAllData().get(position);
            if (messageInfo.getType() == Constants.CHAT_ITEM_TYPE_GOODS) {
                Bundle bundle = new Bundle();
                bundle.putString("id", messageInfo.getMsgId());
                ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
            } else if (messageInfo.getType() == Constants.CHAT_ITEM_TYPE_SEND_GOODS) {
                String key = mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY;
                Message customMessage = JMessageClient.createSingleCustomMessage(mIdName, key, mGoodsCustom);
                LogUtil.e(mGoodsCustom);
                LogUtil.e(customMessage);
                JMessageClient.sendMessage(customMessage);
                Conversation conversation = JMessageClient.getSingleConversation(mIdName, mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY);
                messageInfos = new ArrayList<>();
                if (conversation != null) {
                    List<Message> allMessage = conversation.getAllMessage();
                    if (allMessage != null) {
                        chatAdapter.clear();
                        loadData(allMessage);
                        chatAdapter.notifyDataSetChanged();
                        mEasyRecyclerView.scrollToPosition(chatAdapter.getCount() - 1);
                    }
                }
            } else {
                int location[] = new int[2];
                view.getLocationOnScreen(location);
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setLocationX(location[0]);
                imageInfo.setLocationY(location[1]);
                imageInfo.setWidth(view.getWidth());
                imageInfo.setHeight(view.getHeight());
                imageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
                Intent intent = new Intent(mActivity, ScanImageActivity.class);
                intent.putExtra("imageInfo",imageInfo);
                startActivity(intent);
            }
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.drawable.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.drawable.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), mp -> animView.setImageResource(res));
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        LogUtil.e(messageInfo);
        LogUtil.e("-------------------");
        if (StringUtils.isNotBlank(messageInfo.getFilepath())) {
            LogUtil.e("音频");
            try {
                Message voiceMessage = JMessageClient.createSingleVoiceMessage(mIdName,
                        mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY,
                        new File(messageInfo.getFilepath()), (int) messageInfo.getVoiceTime());
                JMessageClient.sendMessage(voiceMessage);
                voiceMessage.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        LogUtil.e(i, s);
                    }
                });
                messageInfo.setHeader(WmtApplication.avatar);
                messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                messageInfos.add(messageInfo);
                chatAdapter.add(messageInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotBlank(messageInfo.getImageUrl())) {
            LogUtil.e("图片");
            try {
                Message imgMessage = JMessageClient.createSingleImageMessage(mIdName,
                        mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY,
                        new File(messageInfo.getImageUrl()));
                JMessageClient.sendMessage(imgMessage);
                imgMessage.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        LogUtil.e(i, s);
                    }
                });
                messageInfo.setHeader(WmtApplication.avatar);
                messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                messageInfos.add(messageInfo);
                chatAdapter.add(messageInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotBlank(messageInfo.getContent())) {
            LogUtil.e("文本");
            Message message = JMessageClient.createSingleTextMessage(mIdName,
                    mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY,
                    messageInfo.getContent());
            JMessageClient.sendMessage(message);
            messageInfo.setHeader(WmtApplication.avatar);
            messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
            messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
            messageInfos.add(messageInfo);
            chatAdapter.add(messageInfo);
        }
        mEasyRecyclerView.scrollToPosition(chatAdapter.getCount() - 1);
    }

    public void onEventMainThread(MessageEvent event) {
        LogUtil.e("-----");
        Conversation conversation = JMessageClient.getSingleConversation(mIdName, mIdName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY);
        messageInfos = new ArrayList<>();
        if (conversation != null) {
            List<Message> allMessage = conversation.getAllMessage();
            if (allMessage != null) {
                chatAdapter.clear();
                loadData(allMessage);
                chatAdapter.notifyDataSetChanged();
                mEasyRecyclerView.scrollToPosition(chatAdapter.getCount() - 1);
            } else {
                createTextMsg();
            }
        } else {
            createTextMsg();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BGAKeyboardUtil.closeKeyboard(mActivity);
        KeyboardUtils.init().hideSoftInput(this);
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
        JMessageClient.unRegisterEventReceiver(this);
    }

}
