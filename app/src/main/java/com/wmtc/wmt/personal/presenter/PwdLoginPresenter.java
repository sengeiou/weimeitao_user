package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.personal.activity.PwdLoginActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.personal.bean.LoginDateBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.personal.pojo.PwdLoginPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class PwdLoginPresenter extends BasePresenter<PwdLoginActivity> {

    private CommonModel mModel;

    public PwdLoginPresenter(PwdLoginActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void pwdLogin(PwdLoginPojo Pojo) {
        mModel.pwdLogin(Pojo).subscribe(new DefaultCallBackObserver<LoginDateBean>(new LoaddingErrorImplTip(mIView),this) {
            @Override
            public void responseSuccess(LoginDateBean bean) {
                mIView.pwdLoginDate(bean);
            }

            @Override
            public void responseFail(LoginDateBean bean) {

            }
        });
    }
}
