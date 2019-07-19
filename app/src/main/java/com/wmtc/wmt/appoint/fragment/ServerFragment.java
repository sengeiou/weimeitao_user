package com.wmtc.wmt.appoint.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.CityOpenActivity;
import com.wmtc.wmt.appoint.activity.MatchShopProActivity;
import com.wmtc.wmt.appoint.activity.OrderCommentActivity;
import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.appoint.activity.ProSkinBKActivity;
import com.wmtc.wmt.appoint.activity.ProjectInfoActivity;
import com.wmtc.wmt.appoint.adapter.CheckAdapter;
import com.wmtc.wmt.appoint.adapter.OrderHistoryAdapter;
import com.wmtc.wmt.appoint.bean.CityListBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.appoint.bean.TitleBean;
import com.wmtc.wmt.appoint.event.JudgeEvent;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.appoint.presenter.ServerPresenter;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.activity.NearByLocalActivity;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.wmtc.wmt.forum.event.LocalSelEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.utils.klog.KLog;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ServerFragment extends SuperBaseFragment implements AMapLocationListener {
    @BindView(R.id.tvMatch)
    TextView mTvMatch;
    @BindView(R.id.tvHistory)
    TextView mTvHistory;
    @BindView(R.id.tvCurLocal)
    TextView mTvCurLocal;
    @BindView(R.id.flHistory)
    FrameLayout flHistory;
    @BindView(R.id.flMatch)
    FrameLayout flMatch;
    Unbinder unbinder;
    @BindView(R.id.appointBar)
    View mAppointBar;
    @BindView(R.id.banner)
    BGABanner mBanner;

    @BindView(R.id.btnAppoint)
    TextView mBtnAppoint;
    @BindView(R.id.bannerMiddle)
    BGABanner mBannerMiddle;
    @BindView(R.id.tvNameBK)
    TextView mTvNameBK;
    @BindView(R.id.tvBKContent)
    TextView mTvBKContent;
    @BindView(R.id.llBk)
    LinearLayout mLlBk;
    @BindView(R.id.tvNameSkin)
    TextView mTvNameSkin;
    @BindView(R.id.tvSkinContent)
    TextView mTvSkinContent;
    @BindView(R.id.llSkin)
    LinearLayout mLlSkin;
    @BindView(R.id.recyclerViewSkin)
    RecyclerView recyclerViewSkin;
    @BindView(R.id.recyclerViewPrice)
    RecyclerView recyclerViewPrice;
    @BindView(R.id.smartRefreshLayoutMatch)
    SmartRefreshLayout mSmartRefreshLayoutMatch;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String curCity = "";
    private List<CityListBean.DataBean> cityList = new ArrayList<>();
    private boolean isFirst = true;
    private boolean isAnimTop = false;
    int duration = 125;
    private int mScreenWidth;
    private ServerPresenter mPresenter;
    private OrderHistoryAdapter mHistoryAdapter;
    private MatchPojo mMatchPojo;
    private String mSkinId;
    private CheckAdapter mSkinRecyclerAdapter;
    private CheckAdapter mPriceRecyclerAdapter;

    @Override
    public int initLayout() {
        return R.layout.fragment_server;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.appointBar).init();
    }

    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        initRefreshStatusView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mScreenWidth = ScreenUtils.getScreenWidth();
        flHistory.setTranslationX(-mScreenWidth);
        mPresenter = new ServerPresenter(this);
        mMatchPojo = new MatchPojo();

        mMatchPojo.longlat = (String) SharePreUtil.getData(mActivity, "longlat", "");

        mPresenter.getHeaderBanner();
        mPresenter.getMiddleBanner();
        mPresenter.getTitle();
        mPresenter.getNeed();
        mPresenter.getPrice();
        mPresenter.cityLick();

        mHistoryAdapter = new OrderHistoryAdapter(null);
        showLoading();
        recyclerViewSkin.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mSkinRecyclerAdapter = new CheckAdapter(null);
        recyclerViewSkin.setAdapter(mSkinRecyclerAdapter);

        recyclerViewPrice.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mPriceRecyclerAdapter = new CheckAdapter(null);
        recyclerViewPrice.setAdapter(mPriceRecyclerAdapter);

        mRecyclerView.setAdapter(mHistoryAdapter);
        AndPermission.with(Objects.requireNonNull(getActivity()))
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION)
                .onGranted(permissions -> {
                    responseLocation();
                })
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(Objects.requireNonNull(getActivity()), permissions))
                .start();
        initClick();

    }

    @Override
    protected void onShowFragment() {
        super.onShowFragment();
        responseLocation();
    }

    private void responseLocation() {
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationOption.setNeedAddress(true);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        OrderListPojo pojo = new OrderListPojo();
        pojo.setUserId(WmtApplication.id);
        pojo.setOrderStatus("6");
        mPresenter.getAllOrderList(pojo);
    }

    public void getAllOrderList(OrderListBean bean) {
        responseSuccess();
        OrderListBean.DataBean data = bean.data;
        if (data != null && data.list != null && data.list.size() > 0) {
            mHistoryAdapter.setNewData(data.list);
        } else {
            showEmpty();
        }
    }

    private void initClick() {

        mSmartRefreshLayoutMatch.setOnRefreshListener(refreshLayout -> {
            Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                mSmartRefreshLayoutMatch.finishRefresh();
            });
            mPresenter.getHeaderBanner();
            mPresenter.getMiddleBanner();
            mPresenter.getTitle();
            mPresenter.getNeed();
            mPresenter.getPrice();
        });
        mBanner.setAdapter((BGABanner.Adapter<ImageView, CommentBannerBean.DataBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model.path)
                        .apply(GlideUtils.init().options(R.drawable.wmt_default))
                        .into(itemView);
                itemView.setOnClickListener(v -> {
                    LogUtil.e(model.linkType);
                    WmtUtil.init().bannerType(mActivity, model.linkType, model.linkUrl);
                });
            }
        });
        mBannerMiddle.setAdapter((BGABanner.Adapter<ImageView, CommentBannerBean.DataBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model.path)
                        .apply(GlideUtils.init().options(R.drawable.wmt_default))
                        .into(itemView);
                itemView.setOnClickListener(v -> {
                    LogUtil.e(model.linkType);
                    WmtUtil.init().bannerType(mActivity, model.linkType, model.linkUrl);
                });
            }
        });

        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", mHistoryAdapter.getData().get(position).id);
            ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
        });

        mHistoryAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderListBean.DataBean.ListBean listBean = mHistoryAdapter.getData().get(position);
            if (view.getId() == R.id.tvComment) {
                Bundle bundle = new Bundle();
                listBean.proPic = WmtApplication.url_host + listBean.proPic;
                if ("已评价".equals(listBean.commentStatus)) {
                    return false;
                }
                Gson gson = new Gson();
                String json = gson.toJson(listBean);
                OneOrderBean.DataBean jsonBean = gson.fromJson(json, OneOrderBean.DataBean.class);
                bundle.putParcelable("order", jsonBean);
                ActivityUtils.init().start(mActivity, OrderCommentActivity.class, "", bundle);

            } else if (view.getId() == R.id.tvNewOne) {
                Bundle bundle = new Bundle();
                bundle.putString("id", listBean.projectId);
                ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, "", bundle);
            }
            return false;
        });

        mTvCurLocal.setOnClickListener(v -> {
            AndPermission.with(Objects.requireNonNull(getActivity()))
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION)
                    .onGranted(permissions -> {
                        ActivityUtils.init().start(getActivity(), NearByLocalActivity.class, "");
                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(Objects.requireNonNull(getActivity()), permissions))
                    .start();

        });


        mTvMatch.setOnClickListener(v -> {
            startAnim(!isFirst, flHistory, -mScreenWidth, flMatch, mScreenWidth, mTvMatch, mTvHistory, true);
        });

        mTvHistory.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                startAnim(isFirst, flMatch, mScreenWidth, flHistory, -mScreenWidth, mTvHistory, mTvMatch, false);
            }
        });
        mLlBk.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            ActivityUtils.init().start(mActivity, ProSkinBKActivity.class, bundle);
        });
        mLlSkin.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            ActivityUtils.init().start(mActivity, ProSkinBKActivity.class, bundle);
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
            mSkinId = dataBean.id;
            mSkinRecyclerAdapter.notifyDataSetChanged();
        });
        mBtnAppoint.setOnClickListener(v -> {
            if (StringUtils.isEmpty(mMatchPojo.hufuxuKeys)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择护肤需求");
                return;
            }
            if (StringUtils.isEmpty(mMatchPojo.prices)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择价格区间");
                return;
            }
            LogUtil.e(mMatchPojo);

            for (CityListBean.DataBean dataBean : cityList) {
                if (dataBean.city.contains(curCity) && dataBean.isOpen == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("skinId", mSkinId);
                    bundle.putParcelable("pojo", mMatchPojo);
                    ActivityUtils.init().start(mActivity, MatchShopProActivity.class, "项目选择", bundle);
                    return;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putString("city", curCity);
            ActivityUtils.init().start(mActivity, CityOpenActivity.class, "该城市暂未开放", bundle);
        });

    }


    public void price(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mMatchPojo.prices = data.get(0).name;
            data.get(0).isSel = true;
            mPriceRecyclerAdapter.setNewData(data);
        }
    }

    public void need(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mMatchPojo.hufuxuKeys = data.get(0).name;
            mSkinId = data.get(0).id;
            data.get(0).isSel = true;
            mSkinRecyclerAdapter.setNewData(data);

        }
    }


    public void cityLick(CityListBean bean) {
        if (bean.data != null) {
            cityList = bean.data;
        }
    }

    public void headerBanner(CommentBannerBean bean) {
        mBanner.setData(bean.data, null);
    }

    public void middleBanner(CommentBannerBean bean) {
        mBannerMiddle.setData(bean.data, null);
    }

    public void title(TitleBean bean) {
        List<TitleBean.DataBean> dataBeans = bean.data;
        if (dataBeans.size() > 1) {
            mTvBKContent.setText(dataBeans.get(0).name);
            mTvSkinContent.setText(dataBeans.get(1).name);
        }
    }


    private void startAnim(boolean b, FrameLayout fl2, int screenWidth, FrameLayout fl1, int i, TextView tv1,
                           TextView tv2, boolean b2) {
        if (b && !isAnimTop) {
            ViewAnimator.animate(fl2).duration(duration).translationX(0, screenWidth)
                    .andAnimate(fl1).duration(duration).translationX(i, 0)
                    .onStart(() -> {
                        isAnimTop = true;
                    })
                    .onStop(() -> {
                        isAnimTop = false;
                        if (!isFirst) {
                            OrderListPojo pojo = new OrderListPojo();
                            pojo.setOrderStatus("6");
                            pojo.setUserId(WmtApplication.id);
                            mPresenter.getAllOrderList(pojo);
                        }

                    })
                    .start();


            setBold(tv1, 30, R.color.black, Typeface.BOLD);
            setBold(tv2, 14, R.color.color999, Typeface.NORMAL);
            isFirst = b2;
        }
    }

    private void setBold(TextView tvScans, int i, int p, int bold) {
        tvScans.setTextSize(i);
        tvScans.setTextColor(getResources().getColor(p));
        tvScans.setTypeface(Typeface.defaultFromStyle(bold));
    }

    @Subscribe
    public void onEvent(JudgeEvent event) {
        OrderListPojo pojo = new OrderListPojo();
        pojo.setOrderStatus("6");
        pojo.setUserId(WmtApplication.id);
        mPresenter.getAllOrderList(pojo);
    }

    @Subscribe
    public void onEvent(LocalSelEvent event) {
        LogUtil.e(event, "ServerFragment");
        mTvCurLocal.setText(event.local);
        mMatchPojo.longlat = event.lot + "," + event.lat;
        curCity = event.city;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtil.e("=================");
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String street = amapLocation.getStreet();
                String poiname = amapLocation.getPoiName();
                String aoiname = amapLocation.getAoiName();
                amapLocation.getDistrict();
                KLog.e("默认位置定位：", "(" + latitude + "," + longitude + ")" + street + "/" + poiname + "/" + aoiname);
                mTvCurLocal.setText(aoiname);
                mMatchPojo.longlat = longitude + "," + String.valueOf(latitude);
                SharePreUtil.saveData(mActivity, "longlat", longitude + "," + latitude);
                SharePreUtil.saveData(mActivity, "aoi", aoiname);
                curCity = amapLocation.getCity();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }


}
