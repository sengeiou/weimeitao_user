package com.wmtc.wmt.appoint.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.appoint.activity.ProjectInfoActivity;
import com.wmtc.wmt.appoint.activity.SaveAfterSaleActivity;
import com.wmtc.wmt.appoint.activity.ShopInfoActivity;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.dialog.CustomDialog;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.message.activity.CustomChatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/5/25.
 * com.wmtc.wmt.appoint.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderOkFragment extends SuperBaseFragment {
    @BindView(R.id.ivOrderType)
    ImageView mIvOrderType;
    @BindView(R.id.tvOrderType)
    TextView mTvOrderType;
    @BindView(R.id.tvOrderStatusStr)
    TextView mTvOrderStatusStr;
    @BindView(R.id.ivProPic)
    ImageView mIvProPic;
    @BindView(R.id.tvProTitle)
    TextView mTvProTitle;
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvServerPhone)
    TextView mTvServerPhone;
    @BindView(R.id.tvShouHou)
    TextView mTvShouHou;
    @BindView(R.id.llServerPhone)
    LinearLayout mLlServerPhone;
    @BindView(R.id.tvToTime)
    TextView mTvToTime;
    @BindView(R.id.tvWhoServer)
    TextView mTvWhoServer;
    @BindView(R.id.tvManNum)
    TextView mTvManNum;
    @BindView(R.id.tvShopName)
    TextView mTvShopName;
    @BindView(R.id.tvShopLocal)
    TextView mTvShopLocal;
    @BindView(R.id.llOrderChat)
    LinearLayout mLlOrderChat;
    @BindView(R.id.llOrderPhone)
    LinearLayout mLlOrderPhone;
    @BindView(R.id.llOrderChatOrPhone)
    LinearLayout mLlOrderChatOrPhone;
    @BindView(R.id.tvFirstOrderPrice)
    TextView mTvFirstOrderPrice;
    @BindView(R.id.tvHB)
    TextView mTvHB;
    @BindView(R.id.llHb)
    LinearLayout mLlHb;
    @BindView(R.id.tvCoupon)
    TextView mTvCoupon;
    @BindView(R.id.tvDelPrice)
    TextView mTvDelPrice;
    @BindView(R.id.tvEndOrderPrice)
    TextView mTvEndOrderPrice;
    @BindView(R.id.tvMsg)
    TextView mTvMsg;
    @BindView(R.id.tvOrderCopy)
    TextView mTvOrderCopy;
    @BindView(R.id.tvOrderNum)
    TextView mTvOrderNum;
    @BindView(R.id.tvOrderCreateTime)
    TextView mTvOrderCreateTime;
    @BindView(R.id.tvOrderFirstPayTime)
    TextView mTvOrderFirstPayTime;
    @BindView(R.id.tvOrderShopSureTime)
    TextView mTvOrderShopSureTime;
    @BindView(R.id.tvOrderEndPayTime)
    TextView mTvOrderEndPayTime;
    @BindView(R.id.llOrderCoupon)
    LinearLayout mLlOrderCoupon;
    @BindView(R.id.viewClickToPro)
    View viewClickToPro;
    Unbinder unbinder;
    private OrderInfoActivity mInfoActivity;
    private OrderDetailsBean.DataBean mDataBean;

    @Override
    public int initLayout() {
        return R.layout.fragment_order_info_ok;
    }

    @Override
    protected void initData(View rootView) {
        mInfoActivity = (OrderInfoActivity) mActivity;
        initRefreshStatusView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        showLoading();
        mTvServerPhone.setOnClickListener(v -> {
            if (StringUtils.isNotBlank(mInfoActivity.phone)) {
                mInfoActivity.callPhone(mInfoActivity.phone);
            }
        });

    }

    public void orderInfo(OrderDetailsBean.DataBean bean) {
        this.mDataBean = bean;
        responseSuccess();
        mTvOrderType.setText(WmtUtil.getStatus(bean.orderStatus));
        if ("6".equals(bean.orderStatus)) {
            if ("1".equals(bean.isComment)) {
                mTvOrderType.setText("已评价");
            }
        }
        mTvOrderStatusStr.setText(StringUtils.init().fixNullStr(bean.orderDesc));
        Glide.with(mActivity)
                .load(WmtApplication.url_host + bean.picUrl)
                .apply(GlideUtils.init().roundTransFrom(mActivity, 5, R.drawable.wmt_default))
                .into(mIvProPic);
        mTvProTitle.setText(StringUtils.init().fixNullStr(bean.projectName));
        viewClickToPro.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.projectId);
            ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, bundle);
        });
        mTvShopName.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.shopId);
            ActivityUtils.init().start(mActivity, ShopInfoActivity.class, bundle);
        });
        int allPrice = 0;
        int endPrice = Integer.parseInt(bean.projectPriceEnd);
        try {
            allPrice = Integer.parseInt(bean.projectPriceFirst) + endPrice;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mTvPrice.setText(StringUtils.init().fixNullStrMoney(allPrice + "", "￥"));

        boolean is6AndComment = "6".equals(bean.orderStatus);
        mLlServerPhone.setVisibility(is6AndComment ? View.VISIBLE : View.GONE);
        mTvShouHou.setOnClickListener(v -> {
            try {
                int paidProjectPriceFirst = Integer.parseInt(bean.paidProjectPriceFirst);
                int paidProjectPriceEnd = Integer.parseInt(bean.paidProjectPriceEnd);
                int projectPriceFirst = Integer.parseInt(bean.projectPriceFirst);
                int projectPriceEnd = Integer.parseInt(bean.projectPriceEnd);
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.id);
                bundle.putString("picUrl", bean.picUrl);
                bundle.putString("projectName", bean.projectName);
                bundle.putString("shopName", bean.shopName);
                bundle.putString("createTime", bean.createTime);
                bundle.putString("appointTime", bean.arrivalTime);
                bundle.putInt("paidPay", paidProjectPriceFirst + paidProjectPriceEnd);
                bundle.putInt("unpayPay", projectPriceFirst + projectPriceEnd);
                ActivityUtils.init().start(mActivity, SaveAfterSaleActivity.class, "申请售后", bundle);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        mTvToTime.setText(StringUtils.init().fixNullStr(bean.arrivalTime));
        mTvWhoServer.setText(StringUtils.init().fixNullStr(bean.technicianName));

        mTvShopName.setText(StringUtils.init().fixNullStr(bean.shopName));
        mTvShopLocal.setText(StringUtils.init().fixNullStr(bean.shopAddress));
        mTvFirstOrderPrice.setText(StringUtils.init().fixNullStrMoney(bean.projectPriceFirst, "￥"));

        mTvShopLocal.setOnClickListener(v -> {
            String longlat = (String) SharePreUtil.getData(mActivity, "longlat", "");
            if (StringUtils.isNotBlank(longlat) && StringUtils.contains(longlat, ",")) {
                String[] split = longlat.split(",");
                double startLng = Double.parseDouble(split[0]);
                double startLat = Double.parseDouble(split[1]);
                String aoi = (String) SharePreUtil.getData(mActivity, "aoi", "");
                Poi start = new Poi(aoi, new LatLng(startLat, startLng), "");
                Poi end = new Poi(mDataBean.shopAddress, new LatLng(Double.parseDouble(mDataBean.locationLat),
                        Double.parseDouble(mDataBean.locationLong)),
                        "");
                AmapNaviPage.getInstance().showRouteActivity(mActivity, new AmapNaviParams(start, null, end,
                        AmapNaviType.DRIVER), null);

            }
        });
        if (StringUtils.isBlank(bean.couponId)) {
            mLlOrderCoupon.setVisibility(View.GONE);
        }
        if (StringUtils.isBlank(bean.hongbaoId)) {
            mLlHb.setVisibility(View.GONE);
        }

        int couponPrice = bean.couponPrice;
        int hbPrice = 0;
        try {
            if (StringUtils.isBlank(bean.hongbaoPrice)) {
                hbPrice = 0;
            } else {
                hbPrice = Integer.parseInt(bean.hongbaoPrice);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mTvCoupon.setText(StringUtils.init().fixNullStrMoney(couponPrice + "", "-￥"));
        mTvHB.setText(StringUtils.init().fixNullStrMoney(bean.hongbaoPrice, "-￥"));

        int delPrice = hbPrice + couponPrice;
        mTvDelPrice.setText(StringUtils.init().fixNullStrMoney(delPrice + "", "已抵扣：￥"));
        int fixPrice = endPrice - delPrice;
        if (fixPrice < 1) {
            fixPrice = 1;
        }
        mTvEndOrderPrice.setText(StringUtils.init().fixNullStrMoney(fixPrice + "", "￥"));
        String msg = bean.leavingMsg;
        if (StringUtils.isBlank(msg)) {
            msg = "暂无留言";
        }
        mTvMsg.setText(StringUtils.init().fixNullStr(msg));
        mLlOrderPhone.setOnClickListener(v -> {
            mInfoActivity.callPhone(bean.shopTel);
        });
        mTvOrderNum.setText(String.format(Locale.CHINA, "预约订单编号：%s",
                StringUtils.init().fixNullStr(bean.id)));
        mTvOrderCreateTime.setText(String.format(Locale.CHINA, "预约单创建时间：%s",
                StringUtils.init().fixNullStr(bean.createTime)));


        if (StringUtils.isBlank(bean.projectPriceFirstPaidTime)) {
            mTvOrderFirstPayTime.setVisibility(View.GONE);
        }
        mTvOrderFirstPayTime.setText(String.format(Locale.CHINA, "预约金支付时间：%s",
                StringUtils.init().fixNullStr(bean.projectPriceFirstPaidTime)));

        if (StringUtils.isBlank(bean.shopConfirmTime)) {
            mTvOrderShopSureTime.setVisibility(View.GONE);
        }
        mTvOrderShopSureTime.setText(String.format(Locale.CHINA, "商家确认时间：%s",
                StringUtils.init().fixNullStr(bean.shopConfirmTime)));

        if (StringUtils.isBlank(bean.projectPriceEndPaidTime)) {
            mTvOrderEndPayTime.setVisibility(View.GONE);
        }
        mTvOrderEndPayTime.setText(String.format(Locale.CHINA, "到店再付时间：%s",
                StringUtils.init().fixNullStr(bean.projectPriceEndPaidTime)));
        mTvOrderCopy.setOnClickListener(v -> {
            StringUtils.init().copyString(mActivity, StringUtils.init().fixNullStr(bean.id));
            ToastUtils.init().showSuccessToast(mActivity, "订单编号已复制");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void chat(CustomChatBean.DataBean data) {
        mLlOrderChat.setVisibility(View.VISIBLE);
        mLlOrderChat.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            String jmessageUserName = data.jmessageUserName;
            bundle.putString("name", jmessageUserName);
            String key = jmessageUserName.contains("B_") ? WmtApplication.B_JMKEY : WmtApplication.C_JMKEY;
            JMessageClient.getUserInfo(jmessageUserName, key, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (userInfo != null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("title", mDataBean.projectName);
                        map.put("url", mDataBean.picUrl);
                        map.put("price", mTvPrice.getText().toString());
                        map.put("orderId", mDataBean.id);
                        map.put("type", "jm_send_goods");
//                        Message customMessage = JMessageClient.createSingleCustomMessage(jmessageUserName, key, map);
//                        LogUtil.e(map);
//                        LogUtil.e(customMessage);
//                        JMessageClient.sendMessage(customMessage);
                        bundle.putSerializable("goodsCustom", map);
                        ActivityUtils.init().startWithTask(mActivity, CustomChatActivity.class, userInfo.getNickname(), bundle);
                    }
                }
            });
        });
    }
}
