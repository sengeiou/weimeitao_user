package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.CheckAdapter;
import com.wmtc.wmt.appoint.adapter.CheckProAdapter;
import com.wmtc.wmt.appoint.bean.BaikeDateBean;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ShopProjectNameBean;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.appoint.presenter.MatchPresenter;
import com.wmtc.wmt.base.WmtApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.listener.OnCustomGestureListener;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchShopProActivity extends SuperBaseActivity {

    @BindView(R.id.tvProName)
    TextView mTvProName;
    @BindView(R.id.tvProContent)
    TextView mTvProContent;
    @BindView(R.id.tvProTime)
    TextView mTvProTime;
    @BindView(R.id.tvProPlace)
    TextView mTvProPlace;
    @BindView(R.id.tvProPrice)
    TextView mTvProPrice;
    @BindView(R.id.cardView)
    CardView mCardView;
    @BindView(R.id.ivUp)
    ImageView mIvUp;
    @BindView(R.id.ivDown)
    ImageView mIvDown;
    @BindView(R.id.llReset)
    LinearLayout mLlReset;
    @BindView(R.id.btnAppoint)
    TextView mBtnAppoint;
    @BindView(R.id.tvMore)
    TextView tvMore;
    @BindView(R.id.alphaBg)
    View alphaBg;
    @BindView(R.id.tvReset)
    TextView tvReset;
    @BindView(R.id.recyclerViewPro)
    RecyclerView recyclerViewPro;
    @BindView(R.id.recyclerViewPrice)
    RecyclerView recyclerViewPrice;
    @BindView(R.id.recyclerViewSkin)
    RecyclerView recyclerViewSkin;
    private Unbinder mBind;
    private MatchPresenter mPresenter;
    private MatchPojo mMatchPojo;
    private int animType = 0;
    private String proId;
    private CheckProAdapter mProRecyclerAdapter;
    private CheckAdapter mPriceRecyclerAdapter;
    private CheckAdapter mSkinRecyclerAdapter;
    private String mSkinId;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_match_shop;
    }


    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this, view);
        mPresenter = new MatchPresenter(this);
        mSkinId = mBundle.getString("skinId");
        mMatchPojo = mBundle.getParcelable("pojo");
        mPresenter.getProList(mSkinId);
        mPresenter.getNeed();
        mPresenter.getPrice();

        recyclerViewPro.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mProRecyclerAdapter = new CheckProAdapter(null);
        recyclerViewPro.setAdapter(mProRecyclerAdapter);


        recyclerViewPrice.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mPriceRecyclerAdapter = new CheckAdapter(null);
        recyclerViewPrice.setAdapter(mPriceRecyclerAdapter);


        recyclerViewSkin.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mSkinRecyclerAdapter = new CheckAdapter(null);
        recyclerViewSkin.setAdapter(mSkinRecyclerAdapter);
        tvReset.setOnClickListener(v -> {
            proId = "";
            mMatchPojo.xiangmuKeys = "";
            mCardView.setVisibility(View.GONE);
            for (ShopProjectNameBean.DataBean dataBean : mProRecyclerAdapter.getData()) {
                dataBean.isSel = false;
            }
            mProRecyclerAdapter.notifyDataSetChanged();
        });
        initClick();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.statusBar).keyboardEnable(true).init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClick() {
        alphaBg.setOnClickListener(v -> {
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
                                alphaBg.setVisibility(View.VISIBLE);
                            })
                            .onStop(() -> {
                                animType = 2;

                            })
                            .start();
                }
            }

        });

        mProRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mProRecyclerAdapter.getData().size(); i++) {
                ShopProjectNameBean.DataBean dataBean = mProRecyclerAdapter.getData().get(i);
                if (i == position) {
                    dataBean.isSel = !dataBean.isSel;
                    if (dataBean.isSel) {
                        ShopProjectNameBean.DataBean item = mProRecyclerAdapter.getData().get(position);
                        mPresenter.getProductBaike(item.dictProjectId);
                        mMatchPojo.xiangmuKeys = item.dictProjectName;
                        proId = item.dictProjectId;
                    } else {
                        proId = "";
                        mMatchPojo.xiangmuKeys = "";
                        mCardView.setVisibility(View.GONE);
                    }
                } else {
                    dataBean.isSel = false;
                }
            }
            mProRecyclerAdapter.notifyDataSetChanged();
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
            mSkinId = dataBean.id;
            mPresenter.getProList(mSkinId);
        });


        tvMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putString("proId", proId);
            bundle.putString("skinId", mSkinId);
            bundle.putString("proName", mMatchPojo.xiangmuKeys);
            ActivityUtils.init().start(mActivity, ProSkinBKActivity.class, bundle);
        });
        mBtnAppoint.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                LogUtil.e(mMatchPojo);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pojo", mMatchPojo);
                bundle.putString("proId", proId);
                ActivityUtils.init().start(mActivity, MatchAnimActivity.class, bundle);
            }
        });
        OnCustomGestureListener onCustomGestureListener = new OnCustomGestureListener();
        GestureDetector listViewGesture = new GestureDetector(mActivity, onCustomGestureListener);
        mIvUp.setOnTouchListener((v, event) -> {
            return listViewGesture.onTouchEvent(event);
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
                            alphaBg.setVisibility(View.GONE);
                        })
                        .onStop(() -> {
                            animType = 0;
                        })
                        .start();
            }
        }
    }

    public void baikeContent(BaikeDateBean bean) {
        mCardView.setVisibility(View.VISIBLE);
        BaikeDateBean.DataBean data = bean.getData();
        mTvProName.setText(StringUtils.init().fixNullStr(data.title));
        mTvProContent.setText(StringUtils.init().fixNullStr(data.content));
        mTvProTime.setText(StringUtils.init().fixNullStr(data.needTime));
        mTvProPlace.setText(StringUtils.init().fixNullStr(data.useLocation));
        mTvProPrice.setText(StringUtils.init().fixNullStr(data.price));
    }

    public void getProList(ShopProjectNameBean bean) {
        List<ShopProjectNameBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mProRecyclerAdapter.setNewData(data);
            mCardView.setVisibility(View.GONE);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

}
