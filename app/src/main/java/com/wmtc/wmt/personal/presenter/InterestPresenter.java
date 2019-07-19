package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.activity.InterestActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/6/17.
 * com.wmtc.wmt.personal.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class InterestPresenter extends BasePresenter<InterestActivity> {

    private final CommonModel mModel;

    public InterestPresenter(InterestActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }
    public void getInterest() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("interest_name");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getInterest(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }
}
