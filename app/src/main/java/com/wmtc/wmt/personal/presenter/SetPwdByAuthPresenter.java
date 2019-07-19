package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.personal.activity.SetPwdByAuthActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.personal.bean.CodeLoginBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.personal.pojo.CodeLoginPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.forum.pojo.SetPwdPojo;
import com.wmtc.wmt.personal.pojo.PhonePojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class SetPwdByAuthPresenter extends BasePresenter<SetPwdByAuthActivity> {

    private CommonModel model;

    public SetPwdByAuthPresenter(SetPwdByAuthActivity iView) {
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

    public void authLoginBindTel(PhonePojo pojo) {
        model.authLoginBindTel(pojo).subscribe(new DefaultCallBackObserver<CodeLoginBean>(new LoaddingErrorImplTip(mIView)
                , this) {
            @Override
            public void responseSuccess(CodeLoginBean bean) {
                mIView.authLoginBindTel(bean);
            }

            @Override
            public void responseFail(CodeLoginBean bean) {

            }
        });
    }
}
