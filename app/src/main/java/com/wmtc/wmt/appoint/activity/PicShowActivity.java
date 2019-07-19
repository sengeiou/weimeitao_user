package com.wmtc.wmt.appoint.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerViewAdapter;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PicShowActivity extends SuperBaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvSize)
    TextView mTvSize;
    private Unbinder mBind;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_pic_show;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this);
        mIvBack.setOnClickListener(v -> finish());
        ArrayList<ProBkInfoBean.DataBean.PicsBean> url = mBundle.getParcelableArrayList("url");
        int page = mBundle.getInt("page");
        mTvSize.setText(String.format(Locale.CHINA, "%d/%s", page + 1, url.size()));
        mViewPager.setAdapter(new BaseViewPagerViewAdapter<ProBkInfoBean.DataBean.PicsBean>(url, R.layout.adapter_pic_show) {
            @Override
            public void bindView(View view, ProBkInfoBean.DataBean.PicsBean picsBean, int position) {
                ImageView ivPic = view.findViewById(R.id.ivPic);
                Glide.with(mActivity).load(picsBean.url).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(ivPic);
            }
        });
        mViewPager.setCurrentItem(page);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTvSize.setText(String.format(Locale.CHINA, "%d/%s", position + 1, url.size()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
