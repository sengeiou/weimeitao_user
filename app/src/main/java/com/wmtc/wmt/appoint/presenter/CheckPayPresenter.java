package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.CheckPayActivity;
import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.pojo.CanCouponPojo;
import com.wmtc.wmt.appoint.pojo.OrderDetailPojo;
import com.wmtc.wmt.appoint.pojo.PayPoJo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CheckPayPresenter extends BasePresenter<CheckPayActivity> {

    private final CommonModel mModel;

    public CheckPayPresenter(CheckPayActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getOrderDetails(String id) {
        OrderDetailPojo pojo = new OrderDetailPojo();
        pojo.id = id;
        pojo.userId = WmtApplication.id;
        mModel.getOrderDetails(pojo).subscribe(new DefaultCallBackObserver<OrderDetailsBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(OrderDetailsBean bean) {
                mIView.getOrderDetails(bean);
            }

            @Override
            public void responseFail(OrderDetailsBean bean) {

            }
        });
    }


    public void getPayInfo(String couponId,String hbId, String payType, String id) {
        PayPoJo payPoJo = new PayPoJo();
        payPoJo.couponId = couponId;
        payPoJo.hongbaoId = hbId;
        payPoJo.id = id;
        payPoJo.payType = payType;
        payPoJo.userId = WmtApplication.id;
        if (payType.equals("wx")) {
            mModel.getPayOrderWX(payPoJo).subscribe(new DefaultCallBackObserver<WxPayInfoBean>(new LoaddingErrorImplTip(mIView), this) {
                @Override
                public void responseSuccess(WxPayInfoBean wxPayInfoBean) {
                    mIView.wxPay(wxPayInfoBean);
                }

                @Override
                public void responseFail(WxPayInfoBean wxPayInfoBean) {

                }
            });
        } else {
            mModel.getPayOrderAli(payPoJo).subscribe(new DefaultCallBackObserver<AliPayInfoBean>(new LoaddingErrorImplTip(mIView), this) {
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


    public void getCanList(String proId, String shopId) {
        CanCouponPojo pojo = new CanCouponPojo();
        pojo.setCouponConfigType("1");
        pojo.setProjectId(proId);
        pojo.setShopId(shopId);
        pojo.setUserId(WmtApplication.id);
        mModel.getCanList(pojo).subscribe(new DefaultCallBackObserver<CanCouponBean>(this) {
            @Override
            public void responseSuccess(CanCouponBean bean) {
                mIView.getCanList(bean);
            }

            @Override
            public void responseFail(CanCouponBean bean) {

            }
        });
    }

    public void getCanHbList(String proId, String shopId) {
        CanCouponPojo pojo = new CanCouponPojo();
        pojo.setCouponConfigType("2");
        pojo.setProjectId(proId);
        pojo.setShopId(shopId);
        pojo.setUserId(WmtApplication.id);
        mModel.getCanList(pojo).subscribe(new DefaultCallBackObserver<CanCouponBean>(this) {
            @Override
            public void responseSuccess(CanCouponBean bean) {
                mIView.getCanHbList(bean);
            }

            @Override
            public void responseFail(CanCouponBean bean) {

            }
        });
    }


}
