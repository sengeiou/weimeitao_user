package com.wmtc.wmt.appoint.activity;

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
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.ProPicAdapter;
import com.wmtc.wmt.appoint.adapter.SkinListAdapter;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.presenter.ProInfoPresenter;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.listener.ToolBarChangeScrollListener;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/5/22.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectInfoActivity extends SuperBaseActivity {

    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.tvProName)
    TextView mTvProName;
    @BindView(R.id.tvShopName)
    TextView mTvShopName;
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.tvShopLocal)
    TextView mTvShopLocal;
    @BindView(R.id.ivChat)
    ImageView mIvChat;
    @BindView(R.id.tvPlace)
    TextView mTvPlace;
    @BindView(R.id.tvXMGX)
    TextView mTvXMGX;
    @BindView(R.id.tvSYCP)
    TextView mTvSYCP;
    @BindView(R.id.tvPHYQ)
    TextView mTvPHYQ;
    @BindView(R.id.tvFWLC)
    TextView mTvFWLC;
    @BindView(R.id.tvZZFW)
    TextView mTvZZFW;
    @BindView(R.id.tvPos)
    TextView tvPos;
    private ProInfoPresenter mPresenter;
    private View mHeader;
    private Unbinder mBind;
    private ProPicAdapter mAdapter;
    private String mProId;
    private TextView mTvScrollTitle;
    private ProjectInfoBean.DataBean mDataBean;
    private TextView mTvFirstPrice;
    private TextView mTvEndPrice;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new ProInfoPresenter(this);
        mHeader = View.inflate(mActivity, R.layout.header_project_info, null);
        mBind = ButterKnife.bind(this, mHeader);
        mBanner.setAdapter((BGABanner.Adapter<ImageView, String>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model)
                        .apply(GlideUtils.init().options(R.drawable.wmt_default))
                        .into(itemView);
            }
        });
        view.findViewById(R.id.flBack).setOnClickListener(v -> finish());
        view.findViewById(R.id.flBackScroll).setOnClickListener(v -> finish());
        mTvFirstPrice = view.findViewById(R.id.tvFirstPrice);
        mTvEndPrice = view.findViewById(R.id.tvEndPrice);
        mProId = mBundle.getString("id");
        mPresenter.proInfo(mProId);
        mPresenter.getOrderJudge(mProId);
        mAdapter = new ProPicAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeader);
        View unScroll = view.findViewById(R.id.flUnScroll);
        View scroll = view.findViewById(R.id.flScroll);
        mTvScrollTitle = view.findViewById(R.id.tvScrollTitle);
        mRecyclerView.addOnScrollListener(new ToolBarChangeScrollListener() {
            @Override
            public void changeMethod(int alpha, float percent, boolean b) {
                super.changeMethod(alpha, percent, b);
                unScroll.setAlpha(1 - percent);
                scroll.setAlpha(percent);

            }

        });
        mAdapter.addFooterView(View.inflate(mActivity, R.layout.footer_need, null), 0);
        if (mBanner != null) {
            mBanner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (mBanner != null) {
                        tvPos.setText(String.format(Locale.CHINA, "%d/%d", position + 1, mBanner.getItemCount()));
                    }
                }
            });

        }
        view.findViewById(R.id.tvToAppoint).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("proInfo", mDataBean);
            ActivityUtils.init().start(mActivity, PreAppointOrderActivity.class, "预定详情", bundle);
        });
    }

    public void proInfo(ProjectInfoBean bean) {
        mDataBean = bean.data;
        if (mDataBean != null) {
            if (mDataBean.banner != null) {
                mBanner.setData(mDataBean.banner, null);
            }
            if (mDataBean.infos != null) {
                mAdapter.setNewData(mDataBean.infos);
            }
            ProjectInfoBean.DataBean.ShopInfoBean shopInfo = mDataBean.shopInfo;
            mTvShopName.setText(StringUtils.init().fixNullStr(shopInfo.shopName));
            String title =
                    StringUtils.init().fixNullStr(mDataBean.pIdValue) + " " + StringUtils.init().fixNullStr(mDataBean.pTitle);
            mTvProName.setText(title);
            mTvShopLocal.setText(StringUtils.init().fixNullStr(shopInfo.address));
            mTvPlace.setText(StringUtils.init().fixNullStr(mDataBean.pPlaceValue));
            mTvFWLC.setText(StringUtils.init().fixNullStr(mDataBean.pProcess));
            mTvXMGX.setText(StringUtils.init().fixNullStr(mDataBean.pEffect));
            mTvSYCP.setText(StringUtils.init().fixNullStr(mDataBean.pProduct));
            mTvZZFW.setText(StringUtils.init().fixNullStr(mDataBean.pIncrement));
            mTvPHYQ.setText(StringUtils.init().fixNullStr(mDataBean.pInstrument));
            mTvScrollTitle.setText(mDataBean.pTitle);
            if (mDataBean.skinS != null) {
                mFlowLayout.setAdapter(new SkinListAdapter(mDataBean.skinS));
            }
            mPresenter.getCustomerService(shopInfo.id);
            mTvFirstPrice.setText(StringUtils.init().fixNullStrMoney(mDataBean.pPriceFirst + "", "￥"));
            mTvEndPrice.setText(StringUtils.init().fixNullStrMoney(mDataBean.pPriceEnd + "", "到店再付：￥"));

            mTvShopLocal.setOnClickListener(v -> {
                String longlat = (String) SharePreUtil.getData(mActivity, "longlat", "");
                if (StringUtils.isNotBlank(longlat) && StringUtils.contains(longlat, ",")) {
                    String[] split = longlat.split(",");
                    double startLng = Double.parseDouble(split[0]);
                    double startLat = Double.parseDouble(split[1]);
                    String aoi = (String) SharePreUtil.getData(mActivity, "aoi", "");
                    Poi start = new Poi(aoi, new LatLng(startLat, startLng), "");
                    Poi end = new Poi(shopInfo.address, new LatLng(Double.parseDouble(shopInfo.locationLat),
                            Double.parseDouble(shopInfo.locationLong)),
                            "");
                    AmapNaviPage.getInstance().showRouteActivity(mActivity, new AmapNaviParams(start, null, end,
                            AmapNaviType.DRIVER), null);

                }
            });

        }
    }

    public void getOrderJudge(OrderJudgeBean bean) {
        OrderJudgeBean.DataBean dataBean = bean.data;
        if (dataBean != null) {
            List<OrderJudgeBean.DataBean.ResultListBean> resultList = dataBean.resultList;
            if (resultList != null && resultList.size() > 0) {
                View footer = View.inflate(mActivity, R.layout.footer_order_comment, null);
                mAdapter.addFooterView(footer, 1);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();
    }

    public void chatBean(CustomChatBean bean) {
        mIvChat.setVisibility(View.VISIBLE);

        mIvChat.setOnClickListener(v -> {
            if (bean.data != null && StringUtils.isNotBlank(bean.data.jmessageUserName)) {
                Bundle bundle = new Bundle();
                String jmessageUserName = bean.data.jmessageUserName;
                bundle.putString("name", jmessageUserName);
                String key = jmessageUserName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY;
                JMessageClient.getUserInfo(jmessageUserName, key, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (userInfo != null && mDataBean != null) {

//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("title", mDataBean.pTitle);
//                        List<String> banner = mDataBean.banner;
//                        if (banner != null && banner.size() > 0) {
//                            map.put("url", banner.get(0).replace(WmtApplication.url_host, ""));
//                        } else {
//                            map.put("url", "");
//                        }
//                        map.put("price", mDataBean.pPrice + "");
//                        map.put("orderId", "12313123123123");
//                        map.put("type", "jm_goods");
//                        Message customMessage = JMessageClient.createSingleCustomMessage(jmessageUserName, key, map);
//                        LogUtil.e(customMessage);
//                        JMessageClient.sendMessage(customMessage);
                            ActivityUtils.init().startWithTask(mActivity, CustomChatActivity.class, userInfo.getNickname(), bundle);
                        }
                    }
                });
            } else {
                ToastUtils.init().showInfoToast(mActivity, "当前项目未配置客服");
            }
        });

    }
}
