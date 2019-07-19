package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.activity.LocalCreateActivity;
import com.wmtc.wmt.personal.pojo.LocalPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalCreatePresenter extends BasePresenter<LocalCreateActivity> {

    private final CommonModel mModel;

    public LocalCreatePresenter(LocalCreateActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void addAddress(LocalPojo pojo) {
        mModel.addAddress(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.addAddress();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void updateAddress(LocalPojo pojo) {
        mModel.updateAddress(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.updateAddress();
                mIView.complete();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }
}
