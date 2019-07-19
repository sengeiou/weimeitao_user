package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.florent37.viewanimator.ViewAnimator;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.CheckAdapter;
import com.wmtc.wmt.appoint.adapter.MatchShopAdapter;
import com.wmtc.wmt.appoint.adapter.ServerAdapter;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.MatchShopBean;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.appoint.presenter.MatchAnimPresenter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.FeedBackUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.widgets.CardTransformer;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchAnimActivity extends SuperBaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.alphaBg)
    View mAlphaBg;
    @BindView(R.id.ivUp)
    ImageView mIvUp;
    @BindView(R.id.ivDown)
    ImageView mIvDown;
    @BindView(R.id.ivMatchAnim)
    ImageView mIvMatchAnim;
    @BindView(R.id.llReset)
    LinearLayout mLlReset;
    @BindView(R.id.btnAppoint)
    TextView mBtnAppoint;
    @BindView(R.id.flBtn)
    FrameLayout mFlBtn;
    @BindView(R.id.flMatchAnim)
    ConstraintLayout mFlMatchAnim;
    @BindView(R.id.recyclerViewPrice)
    RecyclerView recyclerViewPrice;
    @BindView(R.id.recyclerViewSkin)
    RecyclerView recyclerViewSkin;
    private Unbinder mBind;
    private int animType = 0;
    private MatchAnimPresenter mPresenter;
    private MatchPojo mMatchPojo;
    private MatchShopAdapter mAdapter;
    private int page = 1;
    private GifDrawable mGifDrawable;
    private CheckAdapter mPriceRecyclerAdapter;
    private CheckAdapter mSkinRecyclerAdapter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_match_anim;
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
        String proId = mBundle.getString("proId");
        mMatchPojo = mBundle.getParcelable("pojo");
        mPresenter = new MatchAnimPresenter(this);
        mPresenter.getNeed();
        mPresenter.getPrice();


        recyclerViewPrice.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mPriceRecyclerAdapter = new CheckAdapter(null);
        recyclerViewPrice.setAdapter(mPriceRecyclerAdapter);


        recyclerViewSkin.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mSkinRecyclerAdapter = new CheckAdapter(null);
        recyclerViewSkin.setAdapter(mSkinRecyclerAdapter);


        initClick();
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new CardTransformer(30));
        mMatchPojo.page = 1 + "";
        mPresenter.matchShopByES(mMatchPojo);
        animLoading();
    }

    @SuppressLint("CheckResult")
    private void animLoading() {

        mFlMatchAnim.setVisibility(View.VISIBLE);
        SimpleTarget<Drawable> target = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {
                    mGifDrawable = (GifDrawable) drawable;
                    mIvMatchAnim.setImageDrawable(drawable);
                    mGifDrawable.startFromFirstFrame();

                }

            }
        };
        Glide.with(mActivity).load(R.drawable.match).into(target);
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> FeedBackUtil.init(this).playBeep(R.raw.shuidi), throwable -> {
                });

    }


    private void initClick() {
        mAlphaBg.setOnClickListener(v -> {
            dowmAnim();
        });
        superRootView.findViewById(R.id.tvBack).setOnClickListener(v -> finish());
        mIvDown.setOnClickListener(v -> {
            dowmAnim();

        });
        mIvUp.setOnClickListener(v -> {
            if (animType != 1) {
                if (animType == 0) {
                    ViewAnimator.animate(mLlReset)
                            .duration(200)
                            .translationY(0, -mLlReset.getHeight())
                            .onStart(() -> {
                                animType = 1;
                                mAlphaBg.setVisibility(View.VISIBLE);
                            })
                            .onStop(() -> {
                                animType = 2;

                            })
                            .start();
                }
            }

        });

        mPriceRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mPriceRecyclerAdapter.getData().size(); i++) {
                if (i == position) {
                    mPriceRecyclerAdapter.getData().get(i).isSel = true;
                } else {
                    mPriceRecyclerAdapter.getData().get(i).isSel = false;
                }
            }
            DictListBean.DataBean dataBean = mPriceRecyclerAdapter.getData().get(position);
            mMatchPojo.prices = dataBean.name;
            mPriceRecyclerAdapter.notifyDataSetChanged();
        });

        mSkinRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mSkinRecyclerAdapter.getData().size(); i++) {
                if (i == position) {
                    mSkinRecyclerAdapter.getData().get(i).isSel = true;
                } else {
                    mSkinRecyclerAdapter.getData().get(i).isSel = false;
                }
            }
            DictListBean.DataBean dataBean = mSkinRecyclerAdapter.getData().get(position);
            mMatchPojo.hufuxuKeys = dataBean.name;
            mSkinRecyclerAdapter.notifyDataSetChanged();
        });


        mBtnAppoint.setOnClickListener(v -> {
            LogUtil.e(mMatchPojo);
            ++page;
            if (totalPage < page) {
                page = 1;
            }
            mMatchPojo.page = page + "";
            animLoading();
            mPresenter.matchShopByES(mMatchPojo);
            dowmAnim();
        });
    }

    private void dowmAnim() {
        if (animType != 1) {
            if (animType == 2) {
                ViewAnimator.animate(mLlReset)
                        .duration(200)
                        .translationY(-mLlReset.getHeight(), 0)
                        .onStart(() -> {
                            animType = 1;
                            mAlphaBg.setVisibility(View.GONE);
                        })
                        .onStop(() -> {
                            animType = 0;
                        })
                        .start();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    private int totalPage = 99;

    public void matchShopByEs(MatchShopBean bean) {
        MatchShopBean.DataBean data = bean.data;
        if (data != null && data.records != null) {
            totalPage = data.total / 3;
            mAdapter = new MatchShopAdapter(data.records);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(0);
            mAdapter.setClickListener((item, position) -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", item.shopId);
                bundle.putParcelable("pojo", mMatchPojo);
                ActivityUtils.init().start(mActivity, ShopInfoActivity.class, "", bundle);
            });
        }
    }

    public void price(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mPriceRecyclerAdapter.setNewData(data);
        }
    }

    public void need(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mSkinRecyclerAdapter.setNewData(data);
        }
    }

    @SuppressLint("CheckResult")
    public void matchComplete() {

        Observable.timer(3, TimeUnit.SECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> {
                    mFlMatchAnim.setVisibility(View.INVISIBLE);
                    if (mGifDrawable != null) {
                        mGifDrawable.stop();
                    }
                    FeedBackUtil.init(this).stopMedia();
                }, throwable -> {
                });

    }
}
