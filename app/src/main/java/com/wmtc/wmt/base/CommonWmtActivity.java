package com.wmtc.wmt.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmtc.wmt.R;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class CommonWmtActivity extends SuperBaseActivity {
    FrameLayout mRootView;
    public TextView mTvToolRight;
    public TextView mIvToolLeft;
    public ImageView mIvShare;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_wmt_common;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mRootView = view.findViewById(R.id.rootView);
        mIvToolLeft = view.findViewById(R.id.tvBack);
        mTvToolRight = view.findViewById(R.id.tvSave);
        mIvShare = view.findViewById(R.id.ivShare);
        mRootView.removeAllViews();
        mRootView.addView(View.inflate(this, initAddLayout(), null));
        initAddView(mRootView);
        initListener();
    }

    private void initListener() {
        mIvToolLeft.setOnClickListener(this::toolLeftBtn);
        mTvToolRight.setOnClickListener(this::toolRightBtn);
    }


    public void toolRightBtn(View v) {
    }


    public void toolLeftBtn(View v) {
        finish();
    }

    @LayoutRes
    public abstract int initAddLayout();

    public void initAddView(FrameLayout rootView) {
        initRefreshStatusView(rootView);
        mIvToolLeft.setText(StringUtils.init().fixNullStr(getIntent().getStringExtra("title")));
    }
}
