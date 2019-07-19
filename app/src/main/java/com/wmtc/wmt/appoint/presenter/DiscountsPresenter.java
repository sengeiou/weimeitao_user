package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.fragment.DiscountsFragment;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.CouponPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class DiscountsPresenter extends BasePresenter<DiscountsFragment> {

    private CommonModel mModel;

    public DiscountsPresenter(DiscountsFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getCouponList(CouponPojo pojo) {
        mModel.getCouponList(pojo).subscribe(new DefaultCallBackObserver<DiscountsBean>(this) {
            @Override
            public void responseSuccess(DiscountsBean bean) {
                mIView.getCouponListDate(bean);
            }

            @Override
            public void responseFail(DiscountsBean bean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }
}
