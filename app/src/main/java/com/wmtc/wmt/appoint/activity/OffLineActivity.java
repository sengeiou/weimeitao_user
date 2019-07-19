package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.ShopHbBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.dialog.PayOffLineDialog;
import com.wmtc.wmt.appoint.event.PayOffLineEvent;
import com.wmtc.wmt.appoint.presenter.OffLinePresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;
import com.wmtc.wmt.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.utils.klog.KLog;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OffLineActivity extends CommonWmtActivity {

    @BindView(R.id.ivShopPic)
    ImageView mIvShopPic;
    @BindView(R.id.tvShopName)
    TextView mTvShopName;
    @BindView(R.id.tvPay)
    TextView tvPay;
    @BindView(R.id.edPrice)
    EditText mEdPrice;
    private OffLinePresenter mPresenter;
    private String mId;
    private Unbinder mBind;
    private PayOffLineDialog mLineDialog;

    @Override
    public int initAddLayout() {
        return R.layout.activity_offline;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mId = mBundle.getString("id");
        mPresenter = new OffLinePresenter(this);
        mPresenter.shopInfo(mId);
        mPresenter.getOffLineHb(mId);
        tvPay.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEdPrice)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入需要支付的金额");
                return;
            }
            mLineDialog = new PayOffLineDialog(this);
            mLineDialog.setInput(mEdPrice.getText().toString());
                mLineDialog.setBean(mDataBean);
            mLineDialog.show();
        });
    }

    @Subscribe
    public void onEvent(PayOffLineEvent event) {
        LogUtil.e(event);
        String couponId = "";
        if (mDataBean != null) {
            couponId = mDataBean.couponId;
        }
        mPresenter.getPayInfo(couponId, event.type, mId,event.aprice+"" );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void shopInfo(ShopInfoBean bean) {
        if (bean != null && bean.data != null && bean.data.shops != null) {
            ShopInfoBean.DataBean.ShopsBean shops = bean.data.shops;
            mTvShopName.setText(shops.shopName);
            List<ShopInfoBean.DataBean.ShopsBean.BannerBean> banner = shops.banner;
            if (banner != null && banner.size() > 0) {
                Glide.with(mActivity)
                        .load(WmtApplication.url_host + banner.get(0).url)
                        .apply(GlideUtils.init().roundTransFrom(mActivity, 5))
                        .into(mIvShopPic);
            }
        }
    }

    ShopHbBean.DataBean mDataBean;

    public void shopHb(ShopHbBean bean) {
        this.mDataBean = bean.data;
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
//                        mPresenter.getOrderDetails(mId);
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
//        mPresenter.getOrderDetails(mId);
    }

    public void next4PayOk() {
        KLog.e("---------支付成功----");
        ToastUtils.init().showSuccessToast(mActivity, "支付成功");
        finish();
    }


}
