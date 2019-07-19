package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.OffLineActivity;
import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.ShopHbBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.pojo.PayPoJo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OffLinePresenter extends BasePresenter<OffLineActivity> {

    private final CommonModel mModel;

    public OffLinePresenter(OffLineActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void shopInfo(String shopId) {
        ShopInfoPojo pojo = new ShopInfoPojo();
        pojo.id = shopId;
        pojo.userId = WmtApplication.id;
        mModel.shopInfo(pojo).subscribe(new DefaultCallBackObserver<ShopInfoBean>(new LoaddingErrorImplTip(mIView),
                this) {
            @Override
            public void responseSuccess(ShopInfoBean shopInfoBean) {
                mIView.shopInfo(shopInfoBean);
            }

            @Override
            public void responseFail(ShopInfoBean shopInfoBean) {

            }
        });
    }

    public void getOffLineHb(String shopId) {
        ShopInfoPojo pojo = new ShopInfoPojo();
        pojo.shopId = shopId;
        pojo.userId = WmtApplication.id;
        mModel.getOfflineCan(pojo).subscribe(new DefaultCallBackObserver<ShopHbBean>(this) {
            @Override
            public void responseSuccess(ShopHbBean shopInfoBean) {
                mIView.shopHb(shopInfoBean);
            }

            @Override
            public void responseFail(ShopHbBean shopInfoBean) {

            }
        });
    }

    public void getPayInfo(String couponId, String payType, String shopId,String totalAmountYuan) {
        PayPoJo payPoJo = new PayPoJo();
        payPoJo.couponId = couponId;
        payPoJo.shopId = shopId;
        payPoJo.payType = payType;
        payPoJo.userId = WmtApplication.id;
        payPoJo.totalAmountYuan = totalAmountYuan;
        if (payType.equals("wx")) {
            mModel.getPayOrderAppWx(payPoJo).subscribe(new DefaultCallBackObserver<WxPayInfoBean>(new LoaddingErrorImplTip(mIView), this) {
                @Override
                public void responseSuccess(WxPayInfoBean wxPayInfoBean) {
                    mIView.wxPay(wxPayInfoBean);
                }

                @Override
                public void responseFail(WxPayInfoBean wxPayInfoBean) {

                }
            });
        } else {
            mModel.getPayOrderAppAli(payPoJo).subscribe(new DefaultCallBackObserver<AliPayInfoBean>(new LoaddingErrorImplTip(mIView), this) {
                @Override
                public void responseSuccess(AliPayInfoBean aliPayInfoBean) {
                    mIView.aliPay(aliPayInfoBean);
                }

                @Override
                public void responseFail(AliPayInfoBean aliPayInfoBean) {

                }
            });
        }

    }


}
