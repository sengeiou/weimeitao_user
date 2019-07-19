package com.wmtc.wmt.forum.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.activity.FansActivity;
import com.wmtc.wmt.forum.bean.FansListBean;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumFansPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumFansListPresenter extends BasePresenter<FansActivity> {

    private final CommonModel mModel;

    public ForumFansListPresenter(FansActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void fansList(String id) {
        ForumFansPojo pojo = new ForumFansPojo();
        pojo.createUser = id;
        mModel.fensList(pojo).subscribe(new DefaultCallBackObserver<FansListBean>(this) {
            @Override
            public void responseSuccess(FansListBean fansListBean) {
                mIView.fanList(fansListBean);
            }

            @Override
            public void responseFail(FansListBean fansListBean) {

            }
        });
    }
    public void followShopOrUser(String status, String businessId) {
        FollowPojo pojo = new FollowPojo();
        pojo.businessId = businessId;
        pojo.attentionType = "1";
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
