package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.MainActivity;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.pojo.ShopCouponPojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.pojo.VersionPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;
import top.jplayer.baseprolibrary.utils.SharePreUtil;

public class MainPresenter extends BasePresenter<MainActivity> {
    private CommonModel mModel;

    public MainPresenter(MainActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getNewUseCoupon(UserPojo pojo) {
        mModel.getNewUseCoupon(pojo).subscribe(new DefaultCallBackObserver<CouponDialogbean>() {
            @Override
            public void responseSuccess(CouponDialogbean bean) {
                mIView.getNewUseCouponDate(bean);
            }

            @Override
            public void responseFail(CouponDialogbean bean) {

            }
        });
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

    public void getNewVersion() {
        VersionPojo pojo = new VersionPojo();
        pojo.app = "1";
        mModel.getNewVersion(pojo).subscribe(new DefaultCallBackObserver<VersionBean>() {

            @Override
            public void responseSuccess(VersionBean bean) {
                mIView.version(bean);
            }

            @Override
            public void responseFail(VersionBean bean) {
                SharePreUtil.saveData(mIView, "curVersion", "");
            }

        });
    }

    public void updateUserInfo() {
        UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
        pojo.setJiguangId(WmtApplication.mJPushUdid);
        pojo.setUserId(WmtApplication.id);
        mModel.updateUserInfo(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {

            @Override
            public void responseSuccess(BaseBean bean) {

            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
