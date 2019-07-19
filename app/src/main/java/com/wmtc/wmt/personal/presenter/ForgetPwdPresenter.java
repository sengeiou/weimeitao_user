package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.personal.activity.ForgetPwdActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.pojo.LoginPojo;
import com.wmtc.wmt.personal.pojo.VerLoginPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdActivity> {

    private CommonModel mModel;

    public ForgetPwdPresenter(ForgetPwdActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void sendCode(SendCodePojo pojo) {
        mModel.sendCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.sendCodeDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }


    public void smsVer(VerLoginPojo pojo) {
        mModel.smsVer(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new GetImplTip(mIView)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsVerDate();
            }

            @Override
            public void responseFail(BaseBean verLoginBean) {

            }
        });
    }


    public void registByVerificationCode(LoginPojo pojo) {
        mModel.registByVerificationCode(pojo)
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

}
