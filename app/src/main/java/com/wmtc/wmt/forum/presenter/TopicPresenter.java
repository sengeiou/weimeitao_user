package com.wmtc.wmt.forum.presenter;

import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.activity.TopicActivity;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TopicPresenter extends BasePresenter<TopicActivity> {

    private final CommonModel mModel;

    public TopicPresenter(TopicActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void topic(String keyword) {
        DictCodePojo dictCodePojo = new DictCodePojo();
        dictCodePojo.setCode("topic");
        dictCodePojo.setKeyword(keyword);
        mModel.getxingqu(dictCodePojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getTopic(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }
}
