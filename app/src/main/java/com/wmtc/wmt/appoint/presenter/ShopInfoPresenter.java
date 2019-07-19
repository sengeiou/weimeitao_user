package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.ShopInfoActivity;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.pojo.FollowPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopInfoPresenter extends BasePresenter<ShopInfoActivity> {

    private final CommonModel mModel;

    public ShopInfoPresenter(ShopInfoActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void shopInfo(ShopInfoPojo pojo) {
        pojo.userId = WmtApplication.id;
        mModel.shopInfo(pojo).subscribe(new DefaultCallBackObserver<ShopInfoBean>(new LoaddingErrorImplTip(mIView),
                this) {
            @Override
            public void responseSuccess(ShopInfoBean shopInfoBean) {
                mIView.shopInfo(shopInfoBean);
            }

            @Override
            public void responseFail(ShopInfoBean shopInfoBean) {

            }
        });
    }

    public void getOrderJudge(String id) {
        ShopInfoPojo pojo = new ShopInfoPojo();
        pojo.userId = WmtApplication.id;
        pojo.currentPage = "1";
        pojo.shopId = id;
        mModel.getOrderJudge(pojo).subscribe(new DefaultCallBackObserver<OrderJudgeBean>(this) {
            @Override
            public void responseSuccess(OrderJudgeBean orderJudgeBean) {
                mIView.getOrderJudge(orderJudgeBean);
            }

            @Override
            public void responseFail(OrderJudgeBean shopInfoBean) {

            }
        });
    }

    public void followShopOrUser(String status, String businessId) {
        FollowPojo pojo = new FollowPojo();
        pojo.businessId = businessId;
        pojo.attentionType = "2";
        pojo.attentionStatus = status;
        pojo.createUser = WmtApplication.id;
        mModel.followShopOrUser(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }


}
