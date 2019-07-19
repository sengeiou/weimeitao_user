package com.wmtc.wmt.message.fragment;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.wmtc.chatmodule.ui.activity.DemoActivity;
import com.wmtc.wmt.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Administrator on 2019/2/20.
 */

public class MessageFragment extends SuperBaseFragment {


    private ViewPager mViewPager;
    private LinkedHashMap<String, SuperBaseFragment> mFragmentMap;
    private BaseViewPagerFragmentAdapter<String, SuperBaseFragment> mAdapter;
    private SlidingTabLayout mTabLayout;
    private ChildMessageFragment mServiceFragment;
    private ChildMessageFragment mNotice;
    private ChildChatFragment mChatFragment;

    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.messageBar).init();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        mViewPager = rootView.findViewById(R.id.viewPager);
        mTabLayout = rootView.findViewById(R.id.tabLayout);
        initFragment();
        mAdapter = new BaseViewPagerFragmentAdapter<>(getChildFragmentManager(), mFragmentMap);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    mChatFragment.requestList();
                } else if (position == 1) {
                    mNotice.requestList("notice");
                } else if (position == 2) {
                    mServiceFragment.requestList("service");
                }
            }
        });
        Observable.timer(100, TimeUnit.MILLISECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
            mChatFragment.requestList();
        });

    }

    private void initFragment() {
        mFragmentMap = new LinkedHashMap<>();

        mChatFragment = new ChildChatFragment();
        mFragmentMap.put("私信", mChatFragment);

        mNotice = new ChildMessageFragment();
        mFragmentMap.put("互动消息", mNotice);

        mServiceFragment = new ChildMessageFragment();
        mFragmentMap.put("服务提醒", mServiceFragment);
    }

    @Override
    protected void onShowFragment() {
        super.onShowFragment();
//        ActivityUtils.init().start(mActivity, DemoActivity.class);
    }
}
