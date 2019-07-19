package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.OrderCustomPojo;
import com.wmtc.wmt.appoint.pojo.OrderDetailPojo;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderInfoPresenter extends BasePresenter<OrderInfoActivity> {

    private final CommonModel mModel;

    public OrderInfoPresenter(OrderInfoActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }


    public void getRefund(RefundPojo pojo) {
        mModel.getRefund(pojo).subscribe(new DefaultCallBackObserver<RefundBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(RefundBean bean) {
                mIView.getRefundDate(bean);
            }

            @Override
            public void responseFail(RefundBean bean) {

            }
        });
    }

    public void getRefund(String id) {
        RefundPojo pojo = new RefundPojo();
        pojo.id = id;
        pojo.userId = WmtApplication.id;
        mModel.getRefund(pojo).subscribe(new DefaultCallBackObserver<RefundBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(RefundBean bean) {
                mIView.getRefund(bean);
            }

            @Override
            public void responseFail(RefundBean bean) {

            }
        });
    }


    public void cancelOrder(RefundPojo pojo) {
        pojo.userId = WmtApplication.id;
        mModel.cancelOrder(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.cancelOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }


    public void cancelOrder(String id) {
        RefundPojo pojo = new RefundPojo();
        pojo.orderId = id;
        pojo.userId = WmtApplication.id;
        mModel.cancelOrder(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.cancelOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
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



    public void getServerPhone() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("service_tel");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.callPhone(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }

    public void getCustomerService(String id) {
        OrderCustomPojo orderPojo = new OrderCustomPojo();
        orderPojo.shopId = id;
        orderPojo.userType = "1";
        mModel.getCustomerService(orderPojo).subscribe(new DefaultCallBackObserver<CustomChatBean>(this) {
            @Override
            public void responseSuccess(CustomChatBean customChatBean) {
                mIView.chatBean(customChatBean);
            }

            @Override
            public void responseFail(CustomChatBean customChatBean) {

            }
        });
    }

}
