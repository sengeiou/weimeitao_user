package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.OrderCommentActivity;
import com.wmtc.wmt.appoint.pojo.JudgePojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderCommentPresenter extends BasePresenter<OrderCommentActivity> {

    private final CommonModel mModel;

    public OrderCommentPresenter(OrderCommentActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void saveOrder(JudgePojo pojo) {
        mModel.saveOrderJudge(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.saveOrderJudgeOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }
}
