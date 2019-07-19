package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.activity.SetPasswordActivity;
import com.wmtc.wmt.personal.pojo.VerLoginPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/20.
 * com.wmtc.wmt.personal.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PasswordResetPresenter extends BasePresenter<SetPasswordActivity> {

    private final CommonModel mModel;

    public PasswordResetPresenter(SetPasswordActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void resetPassword(String userId, String password) {
        VerLoginPojo verLoginPojo = new VerLoginPojo();
        verLoginPojo.password = password;
        verLoginPojo.userId = userId;
        mModel.setPassword(verLoginPojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.resetPassword();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }
}
