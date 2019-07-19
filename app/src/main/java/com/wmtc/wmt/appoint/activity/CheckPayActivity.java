package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.dialog.UseCouponDialog;
import com.wmtc.wmt.appoint.event.SelCouponEvent;
import com.wmtc.wmt.appoint.event.SelHbEvent;
import com.wmtc.wmt.appoint.presenter.CheckPayPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;
import com.wmtc.wmt.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CheckPayActivity extends CommonWmtActivity {
    @BindView(R.id.ivProPic)
    ImageView mIvProPic;
    @BindView(R.id.tvProTitle)
    TextView mTvProTitle;
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvToTime)
    TextView mTvToTime;
    @BindView(R.id.tvWhoServer)
    TextView mTvWhoServer;
    @BindView(R.id.tvManNum)
    TextView mTvManNum;
    @BindView(R.id.view3)
    View mView3;
    @BindView(R.id.tvFirstOrderPrice)
    TextView mTvFirstOrderPrice;
    @BindView(R.id.tvCoupon)
    TextView mTvCoupon;
    @BindView(R.id.tvEndOrderPrice)
    TextView mTvEndOrderPrice;
    @BindView(R.id.tvMsg)
    TextView mTvMsg;
    @BindView(R.id.tvDelPrice)
    TextView mTvDelPrice;
    @BindView(R.id.view4)
    View mView4;
    @BindView(R.id.tvTip)
    TextView mTvTip;
    @BindView(R.id.tvCheckZfb)
    TextView mTvCheckZfb;
    @BindView(R.id.flZfb)
    FrameLayout mFlZfb;
    @BindView(R.id.tvCheckWx)
    TextView mTvCheckWx;
    @BindView(R.id.flWx)
    FrameLayout mFlWx;
    @BindView(R.id.llTipContent)
    LinearLayout mLlTipContent;
    @BindView(R.id.llHb)
    LinearLayout mLlHb;
    @BindView(R.id.smart)
    SmartRefreshLayout mSmart;
    @BindView(R.id.tvTipFirst)
    TextView mTvTipFirst;
    @BindView(R.id.tvHB)
    TextView mTvHB;
    @BindView(R.id.tvFirstPrice)
    TextView mTvFirstPrice;
    @BindView(R.id.tvToAppoint)
    TextView mTvToAppoint;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private Unbinder mBind;
    private CheckPayPresenter mPresenter;
    private String mId;
    private OrderDetailsBean.DataBean mOrderBean;
    private CanCouponBean.DataBean.ListBean mCouponBean;
    private String mCouponId;
    private CanCouponBean.DataBean.ListBean mHbBean;

    @Override
    public int initAddLayout() {
        return R.layout.activity_check_pay;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mPresenter = new CheckPayPresenter(this);
        EventBus.getDefault().register(this);
        mId = mBundle.getString("id");
        mPresenter.getOrderDetails(mId);
        mTvToAppoint.setOnClickListener(v -> {
            if (!mTvCheckZfb.isSelected() && !mTvCheckWx.isSelected()) {
                ToastUtils.init().showInfoToast(mActivity, "请选择支付方式");
                mScrollView.smoothScrollTo(0, 1000);
                return;
            }
            String couponId = "";
            if (mCouponBean != null) {
                couponId = mCouponBean.couponId;
            }
            String hbId = "";
            if (mHbBean != null) {
                hbId = mHbBean.couponId;
            }
            if (mTvCheckZfb.isSelected()) {
                mPresenter.getPayInfo(couponId, hbId, "zfb", mId);
            } else {
                mPresenter.getPayInfo(couponId, hbId, "wx", mId);
            }

        });
        mTvCheckWx.setOnClickListener(v -> {
            mTvCheckWx.setSelected(true);
            mTvCheckZfb.setSelected(false);
        });
        mTvCheckZfb.setOnClickListener(v -> {
            mTvCheckZfb.setSelected(true);
            mTvCheckWx.setSelected(false);
        });
    }

    public void getCanHbList(CanCouponBean bean) {
        List<CanCouponBean.DataBean.ListBean> can = bean.data.can;
        if (can != null) {
            mTvHB.setText(String.format(Locale.CHINA, "%d张可用", can.size()));
            mTvHB.setOnClickListener(v -> {
                UseCouponDialog dialog = new UseCouponDialog(mActivity);
                dialog.setType("红包");
                dialog.setBean(bean.data);
                dialog.show();
            });
        }
    }

    public void getCanList(CanCouponBean bean) {

        List<CanCouponBean.DataBean.ListBean> can = bean.data.can;
        if (can != null) {
            mTvCoupon.setText(String.format(Locale.CHINA, "%d张可用", can.size()));
            mTvCoupon.setOnClickListener(v -> {
                UseCouponDialog dialog = new UseCouponDialog(mActivity);
                dialog.setType("优惠券");
                dialog.setBean(bean.data);
                dialog.show();
            });
        }
    }

    public void getOrderDetails(OrderDetailsBean bean) {
        mOrderBean = bean.data;
        if (mOrderBean != null) {
            Glide.with(mActivity)
                    .load(WmtApplication.url_host + mOrderBean.picUrl)
                    .apply(GlideUtils.init().roundTransFrom(mActivity, 5))
                    .into(mIvProPic);
            mTvProTitle.setText(StringUtils.init().fixNullStr(mOrderBean.projectName));
            int allPrice = Integer.parseInt(mOrderBean.projectPriceFirst) + Integer.parseInt(mOrderBean.projectPriceEnd);
            mTvPrice.setText(StringUtils.init().fixNullStrMoney(allPrice + "", "￥"));
            mTvToTime.setText(StringUtils.init().fixNullStr(mOrderBean.arrivalTime));
            mTvWhoServer.setText(StringUtils.init().fixNullStr(mOrderBean.technicianName));
            mTvFirstOrderPrice.setText(StringUtils.init().fixNullStrMoney(mOrderBean.projectPriceFirst, "￥"));

            mTvEndOrderPrice.setText(StringUtils.init().fixNullStrMoney(mOrderBean.projectPriceEnd, "￥"));
            String leavingMsg = mOrderBean.leavingMsg;
            if (StringUtils.isBlank(leavingMsg)) {
                leavingMsg = "暂无留言";
            }
            mTvMsg.setText(StringUtils.init().fixNullStr(leavingMsg));

            if (!"1".equals(mOrderBean.isPay) && !"2".equals(mOrderBean.isPay)) {//1，2不能选优惠券
                mTvCoupon.setEnabled(true);
                mPresenter.getCanList(mOrderBean.projectId, mOrderBean.shopId);
            } else {
                mTvCoupon.setEnabled(false);
                mTvCoupon.setText(StringUtils.init().fixNullStrMoney(mOrderBean.couponPrice + "",
                        StringUtils.init().fixNullStr(mOrderBean.couponName) + " -￥"));
            }

            if (!"2".equals(mOrderBean.isPay)) {
                mTvHB.setEnabled(true);
                mPresenter.getCanHbList(mOrderBean.projectId, mOrderBean.shopId);
            } else {
                mTvHB.setEnabled(false);
                mTvHB.setText(StringUtils.init().fixNullStrMoney(mOrderBean.hongbaoPrice + "",
                        StringUtils.init().fixNullStr(mOrderBean.hongbaoName) + " -￥"));
            }

            int disCoupon = mOrderBean.couponPrice;
            int disHb = 0;
            try {
                disHb = Integer.parseInt(mOrderBean.hongbaoPrice);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mTvDelPrice.setText(String.format(Locale.CHINA, "已抵扣:￥%s",
                    StringUtils.init().fixNullStrMoney(disCoupon + disHb + "")));
            String projectPriceEnd = mOrderBean.projectPriceEnd;
            int endPrice = Integer.parseInt(projectPriceEnd);
            int fixMoney = endPrice - disCoupon - disHb;
            if (fixMoney < 1) {
                fixMoney = 1;
            }
            mTvEndOrderPrice.setText(StringUtils.init().fixNullStrMoney(fixMoney + "", "￥"));

            String orderStatus = mOrderBean.orderStatus;
            if ("1".equals(orderStatus)) {
                mTvTipFirst.setText("支付预约金：");
                mTvFirstPrice.setText(StringUtils.init().fixNullStrMoney(mOrderBean.projectPriceFirst, "￥"));
            } else if ("5".equals(orderStatus)) {
                mTvTipFirst.setText("到店支付：");
                mTvFirstPrice.setText(StringUtils.init().fixNullStrMoney(fixMoney + "", "￥"));
                mLlHb.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe
    public void onEvent(SelCouponEvent event) {
        mCouponBean = event.listBean;
        LogUtil.e(mCouponBean);
        String discountedAmount = mCouponBean.discountedAmount;
        mTvDelPrice.setText(String.format(Locale.CHINA, "已抵扣:￥%s",
                StringUtils.init().fixNullStrMoney(discountedAmount)));
        int disPrice = Integer.parseInt(discountedAmount);
        String projectPriceEnd = mOrderBean.projectPriceEnd;
        int endPrice = Integer.parseInt(projectPriceEnd);
        int fixMoney = endPrice - disPrice;
        if (fixMoney < 1) {
            fixMoney = 1;
        }
        mTvCoupon.setText(StringUtils.init().fixNullStrMoney(mCouponBean.discountedAmount, mCouponBean.couponName + "：-￥"));
        mTvEndOrderPrice.setText(StringUtils.init().fixNullStrMoney(fixMoney + "", "￥"));
    }

    @Subscribe
    public void onEvent(SelHbEvent event) {
        mHbBean = event.listBean;
        LogUtil.e(mHbBean);

        int couponPrice = mOrderBean.couponPrice;
        int disPrice = Integer.parseInt(mHbBean.discountedAmount);
        mTvDelPrice.setText(String.format(Locale.CHINA, "已抵扣:￥%s",
                StringUtils.init().fixNullStrMoney(disPrice + couponPrice + "")));
        String projectPriceEnd = mOrderBean.projectPriceEnd;
        int endPrice = Integer.parseInt(projectPriceEnd);
        int fixMoney = endPrice - disPrice - couponPrice;
        if (fixMoney < 1) {
            fixMoney = 1;
        }
        mTvHB.setText(StringUtils.init().fixNullStrMoney(mHbBean.discountedAmount, mHbBean.couponName + "：-￥"));
        mTvEndOrderPrice.setText(StringUtils.init().fixNullStrMoney(fixMoney + "", "￥"));
        mTvFirstPrice.setText(StringUtils.init().fixNullStrMoney(fixMoney + "", "￥"));
    }


    IWXAPI api;

    // 检查微信是否安装
    private boolean checkWX() {
        boolean bErr = false;
        try {
            if (!api.isWXAppInstalled() || !api.isWXAppSupportAPI()) {
                bErr = true;
            }
        } catch (Exception e) {
            bErr = true;
            e.printStackTrace();
        }
        if (!bErr) {
            return true;
        }
        return false;
    }

    public void wxPay(WxPayInfoBean response) {
        WxPayInfoBean.DataBean orderStrBean = response.data;
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, response.data.appid, true);
        }
        if (!checkWX()) {
            ToastUtils.init().showInfoToast(this, "您手机尚未安装微信，请安装后再试");
            return;
        }
        api.registerApp(response.data.appid);
        PayReq request = new PayReq();
        request.appId = orderStrBean.appid;
        request.partnerId = orderStrBean.partnerid;
        request.prepayId = orderStrBean.prepayid;
        request.packageValue = orderStrBean.packageX;
        request.nonceStr = orderStrBean.noncestr;
        request.timeStamp = orderStrBean.timestamp;
        request.sign = orderStrBean.sign;
        api.sendReq(request);
    }

    @SuppressLint("CheckResult")
    public void aliPay(final AliPayInfoBean response) {
        Observable.just(response).subscribeOn(Schedulers.io())
                .map(aliPayInfoBean -> {
                    PayTask alipay = new PayTask(this);
                    return alipay.payV2(response.data, true);
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.get("resultStatus").equals("9000")) {
                        payAliOk();
                    } else if (result.get("resultStatus").equals("8000")) {
                        ToastUtils.init().showErrorToast(this, "支付处理中，请稍后");
                    } else {
                        ToastUtils.init().showErrorToast(this, "订单支付失败");
                        mPresenter.getOrderDetails(mId);
                    }
                });
    }

    public void payAliOk() {
        next4PayOk();
    }

    @Subscribe
    public void payWxOk(WXPayEntryActivity.WxPayEvent event) {
        next4PayOk();
    }

    @Subscribe
    public void payWxOk(WXPayEntryActivity.WxPayCancelEvent event) {
        mPresenter.getOrderDetails(mId);
    }

    public void next4PayOk() {
        KLog.e("---------支付成功----");
        Bundle bundle = new Bundle();
        bundle.putString("id", mId);
        bundle.putBoolean("payEnd", !"1,3".contains(mOrderBean.orderStatus));
        bundle.putString("price", mTvFirstPrice.getText().toString());
        ActivityUtils.init().start(mActivity, PayOkActivity.class, bundle);

        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
    }

}
