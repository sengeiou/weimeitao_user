package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.MoreOrderCommentActivity;
import com.wmtc.wmt.appoint.bean.JudgeBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.pojo.JudgeListPojo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.activity.MoreCommentActivity;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.BaseNetTip;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;

/**
 * Created by Obl on 2019/5/15.
 * com.wmtc.wmt.forum.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MoreOrderCommentPresenter extends BasePresenter<MoreOrderCommentActivity> {

    private final CommonModel mModel;

    public MoreOrderCommentPresenter(MoreOrderCommentActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }
    public void getOrderJudge(String id) {
        ShopInfoPojo pojo = new ShopInfoPojo();
        pojo.userId = WmtApplication.id;
        pojo.currentPage = "1";
        pojo.shopId = id;
        mModel.getOrderJudge(pojo).subscribe(new DefaultCallBackObserver<OrderJudgeBean>(this) {
            @Override
            public void responseSuccess(OrderJudgeBean orderJudgeBean) {
                mIView.getAllJudgeDate(orderJudgeBean);
            }

            @Override
            public void responseFail(OrderJudgeBean shopInfoBean) {

            }
        });
    }


}
