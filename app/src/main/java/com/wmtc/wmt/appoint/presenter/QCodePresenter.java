package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.ActivityCustomCapture;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.pojo.ShopCouponPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class QCodePresenter extends BasePresenter<ActivityCustomCapture> {

    private final CommonModel mModel;

    public QCodePresenter(ActivityCustomCapture iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }
    public void getShopCoupon(String shopId) {
        ShopCouponPojo pojo = new ShopCouponPojo();
        pojo.shopId = shopId;
        pojo.userId = WmtApplication.id;
        mModel.getShopCoupon(pojo).subscribe(new DefaultCallBackObserver<ShopCouponBean>(new PostImplTip(mIView), this) {
            @Override
            public void responseSuccess(ShopCouponBean shopCouponBean) {
                mIView.getShopCoupon(shopCouponBean);
            }

            @Override
            public void responseFail(ShopCouponBean shopCouponBean) {

            }
        });
    }

}
