package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.fragment.ProBkFragment;
import com.wmtc.wmt.appoint.fragment.SkinBkFragment;
import com.wmtc.wmt.forum.bean.TabLayoutBean;

import java.util.ArrayList;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/28.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProSkinBKActivity extends SuperBaseActivity {

    private CommonTabLayout mTabLayout;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_pro_skin;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true)
                .titleBar(R.id.toolBar)
                .init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        view.findViewById(R.id.tvBack).setOnClickListener(v -> finish());
        mTabLayout = view.findViewById(R.id.tabLayout);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        int type = mBundle.getInt("type");
        String proId = mBundle.getString("proId");
        ArrayList<CustomTabEntity> tabEntitys = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ProBkFragment proBkFragment = new ProBkFragment();
        proBkFragment.setArguments(mBundle);
        fragments.add(proBkFragment);
        if (StringUtils.isNotBlank(proId)) {
            tabEntitys.add(new TabLayoutBean(mBundle.getString("proName")));
        } else {
            tabEntitys.add(new TabLayoutBean("项目百科"));
            tabEntitys.add(new TabLayoutBean("护肤百科"));
            fragments.add(new SkinBkFragment());
        }

        mTabLayout.setTabData(tabEntitys, this, R.id.flFragment, fragments);
        mTabLayout.setCurrentTab(type);
    }
}

