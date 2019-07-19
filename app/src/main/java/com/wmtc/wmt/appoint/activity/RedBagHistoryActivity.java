package com.wmtc.wmt.appoint.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.jplayer.baseprolibrary.utils.klog.KLog;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.fragment.PastdueFragment;
import com.wmtc.wmt.appoint.fragment.UsedFragment;
import com.wmtc.wmt.base.CommonWmtActivity;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.widgets.NoScrollViewPager;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

public class RedBagHistoryActivity extends CommonWmtActivity {

    private TextView tv_left;
    private TextView tv_right;
    private NoScrollViewPager view_pager;
    private int currentIndex = 0;
    private UsedFragment usedFragment;
    private PastdueFragment pastdueFragment;
    public String type;
    private LinkedHashMap<String, Fragment> mLinkedHashMap;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        initView();
        setFragment();
    }

    @Override
    public int initAddLayout() {
        return R.layout.activity_red_bag_history;
    }

    private void initView() {
        type = mBundle.getString("title");
        tv_left = findViewById(R.id.tv_left);
        tv_left.setOnClickListener(v -> {
            view_pager.setCurrentItem(0);
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_left.setTextColor(Color.parseColor("#222222"));
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_right.setTextColor(Color.parseColor("#999999"));
        });
        tv_right = findViewById(R.id.tv_right);
        tv_right.setOnClickListener(v -> {
            view_pager.setCurrentItem(1);
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_left.setTextColor(Color.parseColor("#999999"));
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_right.setTextColor(Color.parseColor("#222222"));
        });
        view_pager = findViewById(R.id.view_pager);
    }

    private void setFragment() {
        usedFragment = new UsedFragment();
        pastdueFragment = new PastdueFragment();
        view_pager.setOffscreenPageLimit(2);
        mLinkedHashMap = new LinkedHashMap<>();
        mLinkedHashMap.put("已使用", usedFragment);
        mLinkedHashMap.put("未使用", pastdueFragment);
        view_pager.setAdapter(new BaseViewPagerFragmentAdapter<>(getSupportFragmentManager(), mLinkedHashMap));

        //设置监听
        view_pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeFragment(position);
            }
        });
    }

    public void changeFragment(int index) {
        if (index == currentIndex) {
            return;
        }
        switch (currentIndex) {
            case 0:
                tv_left.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 1:
                tv_right.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
        }
        switch (index) {
            case 0:
                tv_left.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            case 1:
                tv_right.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
        }
        currentIndex = index;
        //切换Fragment
        view_pager.setCurrentItem(currentIndex);
    }
}
