package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.activity.SetPhoneActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.personal.pojo.PhonePojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class SetPhonePresenter extends BasePresenter<SetPhoneActivity> {

    private CommonModel commonModel;

    public SetPhonePresenter(SetPhoneActivity iView) {
        super(iView);
        commonModel = new CommonModel(WmtServer.class);
    }

    //设置手机号
    public void setPhone(PhonePojo pojo) {
        commonModel.setPhone(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView),
                this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.setPhoneDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {
                mIView.setPhoneDateFail(bean);
            }
        });
    }
    public void sendCode(SendCodePojo pojo) {
        commonModel.sendCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),
                this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.sendCodeDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
}
