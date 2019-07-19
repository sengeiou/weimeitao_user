package com.wmtc.wmt.appoint.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.SkinBkInfoActivity;
import com.wmtc.wmt.appoint.adapter.CheckAdapter;
import com.wmtc.wmt.appoint.adapter.ServerAdapter;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.presenter.SkinBkPresenter;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinBkFragment extends SuperBaseFragment {

    @BindView(R.id.banner)
    BGABanner mBanner;
    Unbinder unbinder;
    private SkinBkPresenter mPresenter;
    private RecyclerView mRecyclerViewCheck;
    private CheckAdapter mAdapter;

    @Override
    public int initLayout() {
        return R.layout.fragment_skinbk;
    }

    @Override
    protected void initData(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = new SkinBkPresenter(this);
        mPresenter.getBanner();
        mPresenter.getSkin();
        mBanner.setAdapter((BGABanner.Adapter<ImageView, CommentBannerBean.DataBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model.path)
                        .apply(GlideUtils.init().options(R.mipmap.wmt_default))
                        .into(itemView);
                itemView.setOnClickListener(v -> {
                    LogUtil.e(model.linkType);
                    WmtUtil.init().bannerType(mActivity, model.linkType, model.linkUrl);
                });
            }
        });


        mRecyclerViewCheck = rootView.findViewById(R.id.recyclerViewCheck);
        mRecyclerViewCheck.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mAdapter = new CheckAdapter(null);
        mRecyclerViewCheck.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DictListBean.DataBean item = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("fid", item.id);
            ActivityUtils.init().start(mActivity, SkinBkInfoActivity.class, item.name, bundle);
        });

    }

    public void getBanner(CommentBannerBean bean) {
        mBanner.setData(bean.data, null);
    }

    public void getSkin(DictListBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            mAdapter.setNewData(bean.data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }
}
