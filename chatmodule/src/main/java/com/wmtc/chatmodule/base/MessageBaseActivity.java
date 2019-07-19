package com.wmtc.chatmodule.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.CommonFragmentPagerAdapter;
import com.wmtc.chatmodule.adapter.MessageAdapter;
import com.wmtc.chatmodule.enity.ImageMessage;
import com.wmtc.chatmodule.enity.TextMessage;
import com.wmtc.chatmodule.enity.VoiceMessage;
import com.wmtc.chatmodule.ui.fragment.MessageEmotionFragment;
import com.wmtc.chatmodule.ui.fragment.MessageFunctionFragment;
import com.wmtc.chatmodule.util.GlobalOnItemClickManagerUtils;
import com.wmtc.chatmodule.util.MessageConst;
import com.wmtc.chatmodule.widget.EmotionInputDetector;
import com.wmtc.chatmodule.widget.NoScrollViewPager;
import com.wmtc.chatmodule.widget.StateButton;

import java.util.ArrayList;

/**
 * Created by Obl on 2019/7/9.
 * com.wmtc.chatmodule.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class MessageBaseActivity extends AppCompatActivity {
    public View superRootView;
    public Activity mActivity;
    /*----------------------start----------------*/
    public MessageAdapter mMessageAdapter;
    public ImageView emotionVoice;
    public EditText editText;
    public TextView voiceText;
    public ImageView emotionButton;
    public ImageView emotionAdd;
    public StateButton emotionSend;
    public NoScrollViewPager viewpager;
    public RelativeLayout emotionLayout;
    public ArrayList<Fragment> fragments;
    public MessageEmotionFragment chatEmotionFragment;
    public MessageFunctionFragment chatFunctionFragment;
    public EmotionInputDetector mDetector;
    public CommonFragmentPagerAdapter mFragmentPagerAdapter;
    public RecyclerView mRecyclerView;

    /*----------------------end----------------*/


    public void initPreCreate() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initPreCreate();
        super.onCreate(savedInstanceState);
        mActivity = this;
        superRootView = initRootView();
        setContentView(superRootView);
        initRootData(superRootView);
    }

    protected View initRootView() {
        int layout = initRootLayout();
        if (layout == 0) {
            layout = top.jplayer.baseprolibrary.R.layout.layout_test;
        }
        return View.inflate(this, layout, null);
    }

    public void initBottomWidget() {
        emotionVoice = findViewById(R.id.emotion_voice);
        editText = findViewById(R.id.edit_text);
        voiceText = findViewById(R.id.voice_text);
        emotionButton = findViewById(R.id.emotion_button);
        emotionAdd = findViewById(R.id.emotion_add);
        emotionSend = findViewById(R.id.emotion_send);
        viewpager = findViewById(R.id.viewpager);
        emotionLayout = findViewById(R.id.emotion_layout);
        fragments = new ArrayList<>();
        chatEmotionFragment = new MessageEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new MessageFunctionFragment();
        fragments.add(chatFunctionFragment);
        mFragmentPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(mFragmentPagerAdapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(initContentView())
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();
        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);
    }

    protected View initContentView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
            mRecyclerView.setHasFixedSize(true);
        } else {
            throw new RuntimeException("ContentView 需重写，或 设置RecyclerView ID 为 recyclerView");
        }
        return mRecyclerView;
    }

    /**
     * 已添加默认布局， 重写方法可自定义布局
     *
     * @return
     */
    @LayoutRes
    protected int initRootLayout() {
        return R.layout.activity_demo;
    }

    /**
     * 默认原始根布局下的FrameLayout,基于相同ToolBar 的视图
     *
     * @param view 根布局
     */
    public void initRootData(View view) {
        initBottomWidget();
        initMessageAdapter(mRecyclerView);
    }

    /**
     * 初始化 Adapter ，方法中已默认添加adapter.
     *
     * @param recyclerView
     */
    public void initMessageAdapter(RecyclerView recyclerView) {
        mMessageAdapter = new MessageAdapter(initMessageData());
        recyclerView.setAdapter(mMessageAdapter);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeKeyboard(mActivity);
    }

    /**
     * 构建自己的数据，方法中已构建了
     *
     * @return
     */
    public abstract ArrayList<MessageBaseInfo> initMessageData();

}
