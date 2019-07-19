package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.ProjectListAdapter;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.dialog.CustomDialog;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.appoint.presenter.ShopInfoPresenter;
import com.wmtc.wmt.base.EmptyUiVideoPlayer;
import com.wmtc.wmt.base.WmtApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.listener.ToolBarChangeScrollListener;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopInfoActivity extends SuperBaseActivity {

    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.player)
    EmptyUiVideoPlayer mVideoPlayer;
    @BindView(R.id.tvShopName)
    TextView mTvShopName;
    @BindView(R.id.tvVideo)
    TextView tvVideo;
    @BindView(R.id.tvVr)
    TextView tvVr;
    @BindView(R.id.tvPic)
    TextView tvPic;
    ImageView ivVideoImg;
    @BindView(R.id.tvShopAverage)
    TextView mTvShopAverage;
    @BindView(R.id.tvShopOpenTime)
    TextView mTvShopOpenTime;
    @BindView(R.id.tvShopLocal)
    TextView mTvShopLocal;
    @BindView(R.id.tvPos)
    TextView tvPos;

    private View mHeader;
    private ProjectListAdapter mAdapter;
    private ShopInfoPresenter mPresenter;
    private List<ShopInfoBean.DataBean.ShopsBean.ProsBean> mProsBeanList;
    private Unbinder mBind;
    private TextView mTvScrollTitle;
    private TextView mTvFollow;
    private TextView mTvVRShow;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_shop_info;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new ShopInfoPresenter(this);
        mTvVRShow = findViewById(R.id.tvVRShow);
        mTvVRShow.setVisibility(View.INVISIBLE);
        mHeader = View.inflate(mActivity, R.layout.header_shop_info, null);
        mBind = ButterKnife.bind(this, mHeader);
        //增加title
        mVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        mVideoPlayer.getBackButton().setVisibility(View.GONE);
        //增加封面
        ivVideoImg = new ImageView(this);
        ivVideoImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mVideoPlayer.setThumbImageView(ivVideoImg);
        mAdapter = new ProjectListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeader);
        mProsBeanList = new ArrayList<>();
        mBanner.setAdapter((BGABanner.Adapter<ImageView, ShopInfoBean.DataBean.ShopsBean.BannerBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(WmtApplication.url_host + model.url)
                        .apply(GlideUtils.init().options(R.drawable.wmt_default))
                        .into(itemView);
            }
        });
        mBanner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mBanner != null) {
                    tvPos.setText(String.format(Locale.CHINA, "%d/%d", position + 1, mBanner.getItemCount()));
                }
            }
        });
        ShopInfoPojo pojo = new ShopInfoPojo();

        Uri uridata = this.getIntent().getData();
        if (uridata != null && "shopinfo".equals(uridata.getHost())) {
            LogUtil.e(uridata.getHost());
            String id = uridata.getQueryParameter("id");
            if (StringUtils.isNotBlank(id)) {
                pojo.id = id;
            }
        } else {
            pojo.id = mBundle.getString("id");
        }
        mPresenter.shopInfo(pojo);
        mPresenter.getOrderJudge(pojo.id);
        View unScroll = view.findViewById(R.id.flUnScroll);
        View scroll = view.findViewById(R.id.flScroll);
        mTvScrollTitle = view.findViewById(R.id.tvScrollTitle);
        view.findViewById(R.id.flBack).setOnClickListener(v -> finish());
        view.findViewById(R.id.flBackScroll).setOnClickListener(v -> finish());
        mTvFollow = view.findViewById(R.id.tvFollow);
        mTvFollow.setOnClickListener(v -> {
            String status;
            String text;
            if ("关注".equals(mTvFollow.getText().toString())) {
                status = "1";
                text = "已关注";
            } else {
                status = "-1";
                text = "关注";
            }
            mTvFollow.setText(text);
            mPresenter.followShopOrUser(status, pojo.id);
        });
        mRecyclerView.addOnScrollListener(new ToolBarChangeScrollListener() {
            @Override
            public void changeMethod(int alpha, float percent, boolean b) {
                super.changeMethod(alpha, percent, b);
                scroll.setAlpha(percent);
                unScroll.setVisibility(!b ? View.GONE : View.VISIBLE);

            }

        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            ShopInfoBean.DataBean.ShopsBean.ProsBean prosBean = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", prosBean.id);
            ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, "", bundle);
        });
        tvVideo.setOnClickListener(v -> {
            mVideoPlayer.setVisibility(View.VISIBLE);
            mVideoPlayer.startPlayLogic();
            tvVideo.setEnabled(false);
            tvVr.setEnabled(true);
            tvPic.setEnabled(true);
        });
        tvVr.setOnClickListener(v -> {

            mVideoPlayer.setVisibility(View.INVISIBLE);
            mVideoPlayer.getCurrentPlayer().onVideoPause();
            tvVr.setEnabled(false);
            tvVideo.setEnabled(true);
            tvPic.setEnabled(true);
        });
        tvPic.setOnClickListener(v -> {
            mVideoPlayer.setVisibility(View.INVISIBLE);
            mVideoPlayer.onVideoPause();
            tvPic.setEnabled(false);
            tvVr.setEnabled(true);
            tvVideo.setEnabled(true);
        });
    }

    public boolean isPlay = false;
    public boolean isPause;

    private void initVideo(String url) {
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setLooping(true)
                .build(mVideoPlayer);

    }

    @SuppressLint("CheckResult")
    public void shopInfo(ShopInfoBean bean) {
        ShopInfoBean.DataBean data = bean.data;
        if (data != null && data.shops != null) {
            ShopInfoBean.DataBean.ShopsBean shopsBean = data.shops;
            mProsBeanList.clear();
            mProsBeanList.addAll(shopsBean.mainPros);
            mProsBeanList.addAll(shopsBean.secondPros);
            mProsBeanList.addAll(shopsBean.lastPros);
            mAdapter.setNewData(mProsBeanList);
            if (shopsBean.banner != null) {
                tvPos.setText(String.format(Locale.CHINA, "1/%d", shopsBean.banner.size()));
                mBanner.setData(shopsBean.banner, null);
            }
            mTvShopAverage.setText(String.format(Locale.CHINA, "￥%s/人", StringUtils.init().fixNullStr(shopsBean.average)));
            mTvShopOpenTime.setText(String.format(Locale.CHINA, "营业时间：%s-%s",
                    StringUtils.init().fixNullStr(shopsBean.openTime),
                    StringUtils.init().fixNullStr(shopsBean.endTime)));
            String local = StringUtils.init().fixNullStr(shopsBean.area) + StringUtils.init().fixNullStr(shopsBean.address);
            mTvShopLocal.setText(local);
            mTvShopName.setText(StringUtils.init().fixNullStr(shopsBean.shopName));
            mTvScrollTitle.setText(StringUtils.init().fixNullStr(shopsBean.shopName));
            mTvFollow.setText("1".equals(shopsBean.attentionStatus) ? "已关注" : "关注");

            mTvShopLocal.setOnClickListener(v -> {
                String longlat = (String) SharePreUtil.getData(mActivity, "longlat", "");
                if (StringUtils.isNotBlank(longlat) && StringUtils.contains(longlat, ",")) {
                    String[] split = longlat.split(",");
                    double startLng = Double.parseDouble(split[0]);
                    double startLat = Double.parseDouble(split[1]);
                    String aoi = (String) SharePreUtil.getData(mActivity, "aoi", "");
                    Poi start = new Poi(aoi, new LatLng(startLat, startLng), "");
                    Poi end = new Poi(shopsBean.address, new LatLng(Double.parseDouble(shopsBean.locationLat),
                            Double.parseDouble(shopsBean.locationLong)),
                            "");
                    AmapNaviPage.getInstance().showRouteActivity(mActivity, new AmapNaviParams(start, null, end,
                            AmapNaviType.DRIVER), null);

                }
            });

            if (StringUtils.isNotBlank(data.shops.vrUrl)) {
                mTvVRShow.setVisibility(View.VISIBLE);
                String url = String.format(Locale.CHINA, "http://yinsi.weimeitao.com/h5wmt/app/vr/threeJs/index.html?VRImg=%s&imgHost=%d",
                        data.shops.vrUrl, BuildConfig.DEBUG ? 0 : 1);
                LogUtil.e(url);
                mTvVRShow.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    ActivityUtils.init().start(mActivity, WebViewActivity.class, "", bundle);
                });
            }


            String videoUrl = WmtApplication.url_host + data.shops.videoUrl;
            initVideo(videoUrl);
            Observable.just(videoUrl).subscribeOn(Schedulers.io())
                    .map(BitmapUtil::getVideoThumb)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        ivVideoImg.setImageBitmap(bitmap);
                    }, throwable -> {
                    });

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();

        if (isPlay) {
            mVideoPlayer.getCurrentPlayer().release();
        }
    }

    protected void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;

    }

    @Override
    public void onPause() {
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    private void callPhone(String phone) {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setDismissButton(CustomDialog.Type.TEXTVIEW);
        customDialog.show();
        customDialog.init(phone, "", "取消", "呼叫", (dialog, type) -> {
            switch (type) {
                case LEFT:
                    customDialog.dismiss();
                    break;
                case RIGHT:
                    Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);
                    break;
            }
        });

    }

    public void getOrderJudge(OrderJudgeBean bean) {
        OrderJudgeBean.DataBean dataBean = bean.data;
        if (dataBean != null) {
            List<OrderJudgeBean.DataBean.ResultListBean> resultList = dataBean.resultList;
            if (resultList != null && resultList.size() > 0) {
                View footer = View.inflate(mActivity, R.layout.footer_order_comment, null);
                mAdapter.addFooterView(footer);
                ImageView ivAvatar = footer.findViewById(R.id.ivCommentAvatar);
                OrderJudgeBean.DataBean.ResultListBean resultListBean = resultList.get(0);
                Glide.with(mActivity)
                        .load(WmtApplication.url_host + resultListBean.avatar)
                        .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                        .into(ivAvatar);
                TextView tvTime = footer.findViewById(R.id.tvTime);
                tvTime.setText(resultListBean.createTime);
                TextView tvName = footer.findViewById(R.id.tvName);
                tvName.setText(resultListBean.name);
                TextView tvContent = footer.findViewById(R.id.tvContent);
                tvContent.setText(resultListBean.judgeContent);
                TextView tvOrderCommentMore = footer.findViewById(R.id.tvOrderCommentMore);
                tvOrderCommentMore.setText(String.format(Locale.CHINA, "查看全部%d条评论", resultList.size()));
                tvOrderCommentMore.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    ActivityUtils.init().start(mActivity, MoreOrderCommentActivity.class, "全部评论", bundle);
                });
            }
        }
    }

}
