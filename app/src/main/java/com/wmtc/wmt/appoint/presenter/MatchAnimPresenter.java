package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.MatchAnimActivity;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.MatchShopBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchAnimPresenter extends BasePresenter<MatchAnimActivity> {

    private final CommonModel mModel;

    public MatchAnimPresenter(MatchAnimActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getNeed() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("sys_hufu_xuqiu");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean commentBannerBean) {
                mIView.need(commentBannerBean);
            }

            @Override
            public void responseFail(DictListBean commentBannerBean) {

            }
        });
    }

    public void matchShopByES(MatchPojo pojo) {

        mModel.matchShopByES(pojo).subscribe(new DefaultCallBackObserver<MatchShopBean>(this) {
            @Override
            public void responseSuccess(MatchShopBean bean) {
                mIView.matchShopByEs(bean);
            }

            @Override
            public void responseFail(MatchShopBean bean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.matchComplete();
            }
        });
    }

    public void getPrice() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("sys_jiagequjian");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean commentBannerBean) {
                mIView.price(commentBannerBean);
            }

            @Override
            public void responseFail(DictListBean commentBannerBean) {

            }
        });
    }

}
