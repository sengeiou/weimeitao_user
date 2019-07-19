package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.AllOrderListActivity;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.appoint.pojo.RefundPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class AllOrderListPresenter extends BasePresenter<AllOrderListActivity> {

    private CommonModel commonModel;

    public AllOrderListPresenter(AllOrderListActivity iView) {
        super(iView);
        commonModel = new CommonModel(WmtServer.class);
    }

    //获取全部订单
    public void getAllOrderList(OrderListPojo pojo) {
        commonModel.getAllOrderList(pojo).subscribe(new DefaultCallBackObserver<OrderListBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(OrderListBean bean) {
                mIView.getAllOrderListDate(bean);
            }

            @Override
            public void responseFail(OrderListBean bean) {

            }
        });
    }

    //删除订单
    public void delete(RefundPojo pojo) {
        commonModel.delete(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),
                this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.delete(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    } //取消订单，退款

    public void getRefund(RefundPojo pojo) {
        commonModel.getRefund(pojo).subscribe(new DefaultCallBackObserver<RefundBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(RefundBean bean) {
                mIView.getRefundDate(bean);
            }

            @Override
            public void responseFail(RefundBean bean) {

            }
        });
    }


    public void cancelOrder(RefundPojo pojo) {
        pojo.userId = WmtApplication.id;
        commonModel.cancelOrder(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.cancelOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

}
