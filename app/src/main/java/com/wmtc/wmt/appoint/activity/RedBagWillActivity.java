package com.wmtc.wmt.appoint.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.fragment.DiscountsFragment;
import com.wmtc.wmt.appoint.fragment.RedBagFragment;
import com.wmtc.wmt.base.CommonWmtActivity;

import java.util.LinkedHashMap;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.widgets.NoScrollViewPager;

public class RedBagWillActivity extends CommonWmtActivity {

    private TextView tv_left;
    private DiscountsFragment discountsFragment;
    private TextView tv_right;
    private RedBagFragment redBagFragment;

    private NoScrollViewPager mViewPager;
    private int currentIndex = 0;
    private BaseViewPagerFragmentAdapter<String, Fragment> mAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_red_main;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        initView();
        setFragment();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    private void initView() {
        tv_left = findViewById(R.id.tv_left);
        tv_left.setOnClickListener(v -> {
            mViewPager.setCurrentItem(0);
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_left.setTextColor(Color.parseColor("#222222"));
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_right.setTextColor(Color.parseColor("#999999"));
        });
        tv_right = findViewById(R.id.tv_right);
        tv_right.setOnClickListener(v -> {
            mViewPager.setCurrentItem(1);
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_left.setTextColor(Color.parseColor("#999999"));
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv_right.setTextColor(Color.parseColor("#222222"));
        });
        mViewPager = findViewById(R.id.view_pager);
    }

    private void setFragment() {
        discountsFragment = new DiscountsFragment();
        redBagFragment = new RedBagFragment();
        mViewPager.setOffscreenPageLimit(2);
        LinkedHashMap<String, Fragment> arrayMap = new LinkedHashMap<>();
        arrayMap.put("优惠券", discountsFragment);
        arrayMap.put("红包", redBagFragment);
        mAdapter = new BaseViewPagerFragmentAdapter<>(getSupportFragmentManager(), arrayMap);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
        mViewPager.setCurrentItem(currentIndex);

    }
}
