package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.personal.activity.PhoneLoginActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.bean.QLoginBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.personal.pojo.AuthLoginPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.pojo.LoginPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class PhoneLoginPresenter extends BasePresenter<PhoneLoginActivity> {

    private CommonModel model;

    public PhoneLoginPresenter(PhoneLoginActivity iView) {
        super(iView);
        model = new CommonModel(WmtServer.class);
    }

    public void sendCode(SendCodePojo pojo) {
        model.sendCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.sendCodeDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

    public void registByVerificationCode(LoginPojo pojo) {
        model.registByVerificationCode(pojo)
                .subscribe(new DefaultCallBackObserver<LoginByCodeBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
                    @Override
                    public void responseSuccess(LoginByCodeBean bean) {
                        mIView.registerAndLogin(bean);
                    }

                    @Override
                    public void responseFail(LoginByCodeBean bean) {

                    }
                });
    }

    public void authLogin(AuthLoginPojo pojo) {
        model.authLogin(pojo).subscribe(new DefaultCallBackObserver<QLoginBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(QLoginBean bean) {
                mIView.authLoginDate(bean);
            }

            @Override
            public void responseFail(QLoginBean bean) {

            }
        });
    }

}
