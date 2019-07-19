package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;
import com.wmtc.wmt.appoint.fragment.ProBkFragment;
import com.wmtc.wmt.appoint.fragment.SkinBkFragment;
import com.wmtc.wmt.appoint.pojo.BannerPojo;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.bean.CommentBannerBean;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/5/28.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinBkPresenter extends BasePresenter<SkinBkFragment> {

    private final CommonModel mModel;

    public SkinBkPresenter(SkinBkFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("143");
        mModel.getBanner(pojo).subscribe(new DefaultCallBackObserver<CommentBannerBean>(this) {
            @Override
            public void responseSuccess(CommentBannerBean commentBannerBean) {
                mIView.getBanner(commentBannerBean);
            }

            @Override
            public void responseFail(CommentBannerBean commentBannerBean) {

            }
        });
    }

    public void getSkin() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("sys_hufu_function");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getSkin(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }
}
