package com.wmtc.wmt.appoint.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.PicShowActivity;
import com.wmtc.wmt.appoint.adapter.CheckAdapter;
import com.wmtc.wmt.appoint.adapter.CheckProBkAdapter;
import com.wmtc.wmt.appoint.adapter.ProBkAdapter;
import com.wmtc.wmt.appoint.adapter.ProBkPicAdapter;
import com.wmtc.wmt.appoint.adapter.ProBkViewPagerAdapter;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;
import com.wmtc.wmt.appoint.presenter.ProBkPresenter;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.activity.VideoPlayActivity;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/28.
 * com.wmtc.wmt.appoint.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkFragment extends SuperBaseFragment {

    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayoutChild)
    SlidingTabLayout mTabLayoutChild;
    Unbinder unbinder;
    @BindView(R.id.tvProInfo)
    TextView mTvProInfo;
    @BindView(R.id.llBanner)
    LinearLayout llBanner;
    @BindView(R.id.tvXMGX)
    TextView mTvXMGX;
    @BindView(R.id.tvBYRQ)
    TextView mTvBYRQ;
    @BindView(R.id.tvCZBW)
    TextView mTvCZBW;
    @BindView(R.id.tvZLFS)
    TextView mTvZLFS;
    @BindView(R.id.tvJGQJ)
    TextView mTvJGQJ;
    @BindView(R.id.tvZLSC)
    TextView mTvZLSC;
    @BindView(R.id.tvProGood)
    TextView mTvProGood;
    @BindView(R.id.tvProBad)
    TextView mTvProBad;
    @BindView(R.id.tvZLFF)
    TextView mTvZLFF;
    @BindView(R.id.tvPHYQ)
    TextView mTvPHYQ;
    @BindView(R.id.tvMorePic)
    TextView mTvMorePic;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.llPics)
    LinearLayout mLlPics;
    @BindView(R.id.recyclerViewCheck)
    RecyclerView recyclerViewCheck;
    private ProBkPresenter mPresenter;
    private ProBkPicAdapter mAdapter;
    private CheckProBkAdapter mCheckRecyclerAdapter;
    private Bundle mArguments;
    private String mProId;
    private ProBkInfoBean.DataBean mData;

    @Override
    public int initLayout() {
        return R.layout.fragment_pro_bk;
    }

    @Override
    protected void initData(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = new ProBkPresenter(this);
        mArguments = getArguments();
        if (mArguments != null) {
            mProId = mArguments.getString("proId");
            if (StringUtils.isNotBlank(mProId)) {
                mViewPager.setVisibility(View.GONE);
                mTabLayoutChild.setVisibility(View.GONE);
                recyclerViewCheck.setVisibility(View.GONE);
                llBanner.setVisibility(View.GONE);
                mPresenter.getProductBaike(mProId);
            } else {
                mPresenter.getFunctionList();
                mPresenter.getBanner();

            }
        }
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

        recyclerViewCheck.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mCheckRecyclerAdapter = new CheckProBkAdapter(null);
        recyclerViewCheck.setAdapter(mCheckRecyclerAdapter);
        mCheckRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mCheckRecyclerAdapter.getData().size(); i++) {
                mCheckRecyclerAdapter.getData().get(i).isSel = i == position;
            }
            ProBkBean.DataBean.ListBean listBean = mCheckRecyclerAdapter.getData().get(position);
            mPresenter.getProductBaike(listBean.id + "");
            mCheckRecyclerAdapter.notifyDataSetChanged();
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mAdapter = new ProBkPicAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProBkInfoBean.DataBean.PicsBean picsBean = mAdapter.getData().get(position);
            if ("pic".equals(picsBean.type)) {
                if (mData != null) {
                    List<ProBkInfoBean.DataBean.PicsBean> pics = mData.pics;
                    int fixPos = position;
                    if (mData.videos != null) {
                        fixPos = position - mData.videos.size();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("url", (ArrayList<? extends Parcelable>) pics);
                    bundle.putInt("page", fixPos);
                    ActivityUtils.init().start(mActivity, PicShowActivity.class, "", bundle);
                }
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("url", picsBean.url);
                ActivityUtils.init().start(mActivity, VideoPlayActivity.class, "", bundle);
            }
        });
        mTvMorePic.setOnClickListener(v -> {
            if (mData != null) {
                List<ProBkInfoBean.DataBean.PicsBean> pics = mData.pics;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("url", (ArrayList<? extends Parcelable>) pics);
                bundle.putInt("page", 0);
                ActivityUtils.init().start(mActivity, PicShowActivity.class, "", bundle);
            }
        });
    }

    @SuppressLint("CheckResult")
    public void productBk(ProBkInfoBean bean) {
        mData = bean.data;
        if (mData != null) {
            mTvProInfo.setText(StringUtils.init().fixNullStr(mData.content));
            mTvXMGX.setText(StringUtils.init().fixNullStr(mData.functionName));
            mTvBYRQ.setText(StringUtils.init().fixNullStr(mData.suitCrowds));
            mTvCZBW.setText(StringUtils.init().fixNullStr(mData.useLocation));
            mTvZLFS.setText(StringUtils.init().fixNullStr(mData.fangShi));
            mTvJGQJ.setText(StringUtils.init().fixNullStr(mData.price));
            mTvZLSC.setText(StringUtils.init().fixNullStr(mData.needTime));
            mTvProGood.setText(StringUtils.init().fixNullStr(mData.advantage));
            mTvProBad.setText(StringUtils.init().fixNullStr(mData.attention));
            mTvPHYQ.setText(StringUtils.init().fixNullStr(mData.useTool));
            mTvPHYQ.setText(StringUtils.init().fixNullStr(mData.methods));
            ArrayList<ProBkInfoBean.DataBean.PicsBean> picsBeans = new ArrayList<>();
            if (mData.videos != null && mData.videos.size() > 0) {
                Observable.fromIterable(mData.videos)
                        .subscribe(videoBean -> {
                            videoBean.type = "video";
                        });
                picsBeans.addAll(mData.videos);
            }
            if (mData.pics != null && mData.pics.size() > 0) {
                Observable.fromIterable(mData.pics)
                        .subscribe(picsBean -> {
                            picsBean.type = "pic";
                        });
                picsBeans.addAll(mData.pics);
            }
            if (picsBeans.size() > 0) {
                if (picsBeans.size() > 3) {
                    mAdapter.setNewData(picsBeans.subList(0, 3));
                } else {
                    mAdapter.setNewData(picsBeans);
                }
                mLlPics.setVisibility(View.VISIBLE);
            } else {
                mLlPics.setVisibility(View.GONE);
            }
        }
    }

    public void getBanner(CommentBannerBean bean) {
        mBanner.setData(bean.data, null);
    }

    @SuppressLint("CheckResult")
    public void getFunctionList(ProBkBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            mViewPager.setAdapter(new ProBkViewPagerAdapter(bean.data));
            mTabLayoutChild.setViewPager(mViewPager);
            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    ProBkBean.DataBean dataBean = bean.data.get(position);
                    List<ProBkBean.DataBean.ListBean> list = dataBean.list;
                    if (list != null && list.size() > 0) {
                        Observable.fromIterable(list).subscribe(listBean -> {
                            listBean.isSel = false;
                        }, throwable -> {
                        });
                        list.get(0).isSel = true;
                        mCheckRecyclerAdapter.setNewData(list);
                        mPresenter.getProductBaike("" + list.get(0).id);
                    }
                }
            });

            ProBkBean.DataBean dataBean = bean.data.get(0);
            List<ProBkBean.DataBean.ListBean> list = dataBean.list;
            if (list != null && list.size() > 0) {
                list.get(0).isSel = true;
                mCheckRecyclerAdapter.setNewData(list);
                mPresenter.getProductBaike(list.get(0).id + "");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.detachView();
    }
}
