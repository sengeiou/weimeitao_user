package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.MatchShopProActivity;
import com.wmtc.wmt.appoint.bean.BaikeDateBean;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ShopProjectNameBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.ProListPojo;
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
public class MatchPresenter extends BasePresenter<MatchShopProActivity> {

    private final CommonModel mModel;

    public MatchPresenter(MatchShopProActivity iView) {
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

    public void getProList(String skinId) {
        ProListPojo pojo = new ProListPojo();
        pojo.skinId = skinId;
        mModel.matchPros(pojo).subscribe(new DefaultCallBackObserver<ShopProjectNameBean>(this) {
            @Override
            public void responseSuccess(ShopProjectNameBean shopProjectNameBean) {
                mIView.getProList(shopProjectNameBean);
            }

            @Override
            public void responseFail(ShopProjectNameBean shopProjectNameBean) {

            }
        });
    }

    public void getProductBaike(String titleId) {
        ProListPojo pojo = new ProListPojo();
        pojo.titleId = titleId;
        mModel.getProductBaike(pojo).subscribe(new DefaultCallBackObserver<BaikeDateBean>(this) {
            @Override
            public void responseSuccess(BaikeDateBean shopProjectNameBean) {
                mIView.baikeContent(shopProjectNameBean);
            }

            @Override
            public void responseFail(BaikeDateBean shopProjectNameBean) {

            }
        });
    }
}
