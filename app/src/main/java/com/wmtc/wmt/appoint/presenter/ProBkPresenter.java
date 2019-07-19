package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.bean.BannerBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;
import com.wmtc.wmt.appoint.fragment.ProBkFragment;
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
public class ProBkPresenter extends BasePresenter<ProBkFragment> {

    private final CommonModel mModel;

    public ProBkPresenter(ProBkFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("142");
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

    public void getProductBaike(String titileId) {
        DictCodePojo pojo = new DictCodePojo();
        pojo.titleId = titileId;
        mModel.getProductBaike(pojo).subscribe(new DefaultCallBackObserver<ProBkInfoBean>(this) {
            @Override
            public void responseSuccess(ProBkInfoBean proBkInfoBean) {
                mIView.productBk(proBkInfoBean);
            }

            @Override
            public void responseFail(ProBkInfoBean proBkInfoBean) {

            }
        });
    }

    public void getFunctionList() {

        mModel.getFunctionList().subscribe(new DefaultCallBackObserver<ProBkBean>(this) {
            @Override
            public void responseSuccess(ProBkBean bean) {
                mIView.getFunctionList(bean);
            }

            @Override
            public void responseFail(ProBkBean bean) {

            }
        });
    }
}
