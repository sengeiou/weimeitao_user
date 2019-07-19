package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.appoint.fragment.UsedFragment;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.CouponPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class UsedPresenter extends BasePresenter<UsedFragment> {
    private CommonModel mModel;

    public UsedPresenter(UsedFragment iView) {
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
        });
    }
}
