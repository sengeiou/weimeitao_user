package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.bean.CityListBean;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.appoint.bean.TitleBean;
import com.wmtc.wmt.appoint.fragment.ServerFragment;
import com.wmtc.wmt.appoint.pojo.BannerPojo;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.bean.CommentBannerBean;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ServerPresenter extends BasePresenter<ServerFragment> {

    private final CommonModel mModel;

    public ServerPresenter(ServerFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getHeaderBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("141");
        mModel.getBanner(pojo).subscribe(new DefaultCallBackObserver<CommentBannerBean>(this) {
            @Override
            public void responseSuccess(CommentBannerBean commentBannerBean) {
                mIView.headerBanner(commentBannerBean);
            }

            @Override
            public void responseFail(CommentBannerBean commentBannerBean) {

            }
        });
    }

    public void cityLick() {
        mModel.cityLick().subscribe(new DefaultCallBackObserver<CityListBean>(this) {
            @Override
            public void responseSuccess(CityListBean bean) {
                mIView.cityLick(bean);
            }

            @Override
            public void responseFail(CityListBean bean) {

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

    public void getAllOrderList(OrderListPojo pojo) {
        mModel.getAllOrderList(pojo).subscribe(new DefaultCallBackObserver<OrderListBean>() {
            @Override
            public void responseSuccess(OrderListBean bean) {
                mIView.getAllOrderList(bean);
            }

            @Override
            public void responseFail(OrderListBean bean) {

            }
        });
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

    public void getTitle() {
        mModel.getTitle().subscribe(new DefaultCallBackObserver<TitleBean>(this) {
            @Override
            public void responseSuccess(TitleBean titleBean) {
                mIView.title(titleBean);
            }

            @Override
            public void responseFail(TitleBean titleBean) {

            }
        });
    }

    public void getMiddleBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("144");
        mModel.getBanner(pojo).subscribe(new DefaultCallBackObserver<CommentBannerBean>(this) {
            @Override
            public void responseSuccess(CommentBannerBean commentBannerBean) {
                mIView.middleBanner(commentBannerBean);
            }

            @Override
            public void responseFail(CommentBannerBean commentBannerBean) {

            }
        });
    }
}
