package com.wmtc.wmt.forum.presenter;

import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.fragment.CommunityMainFragment;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.forum.bean.FollowListBean;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.appoint.pojo.BannerPojo;
import com.wmtc.wmt.appoint.pojo.CommentSavePojo;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.networklibrary.net.tip.INetTip;

public class CommunityPresenter extends BasePresenter<CommunityMainFragment> {

    private CommonModel mModel;

    public CommunityPresenter(CommunityMainFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }


    public void getUserInfo() {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserId(WmtApplication.id);
        mModel.getUserInfo(userPojo).subscribe(new DefaultCallBackObserver<UserInfoBean>(this) {
            @Override
            public void responseSuccess(UserInfoBean bean) {
                mIView.getUserInfo(bean);
            }

            @Override
            public void responseFail(UserInfoBean bean) {

            }
        });
    }


    public void getBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("202");
        mModel.getBanner(pojo).subscribe(new DefaultCallBackObserver<CommentBannerBean>(this) {
            @Override
            public void responseSuccess(CommentBannerBean bean) {
                mIView.getBannerDate(bean);
            }

            @Override
            public void responseFail(CommentBannerBean bean) {

            }
        });
    }

    public void commentarySave(String subitemId, String subitemType, String content, String pCommentaryId, String privateStatus) {
        CommentSavePojo pojo = new CommentSavePojo();
        pojo.userId = WmtApplication.id;
        pojo.subitemId = subitemId;
        pojo.content = content;
        pojo.subitemType = subitemType;
        pojo.pCommentaryId = pCommentaryId;
        pojo.privateStatus = privateStatus;
        mModel.commentarySave(pojo).subscribe(new DefaultCallBackObserver<CommentSaveBean>(new LoaddingErrorImplTip(mIView.mActivity),this) {
            @Override
            public void responseSuccess(CommentSaveBean bean) {
                mIView.commentSave(bean);
            }

            @Override
            public void responseFail(CommentSaveBean forumCommentBean) {

            }
        });
    }


    public void findForum(int page,String type) {
        ForumPojo forumPojo = new ForumPojo();
        forumPojo.pageNo = page + "";
        forumPojo.type = type;
        forumPojo.userId = WmtApplication.id;
        mModel.findForum(forumPojo).subscribe(new DefaultCallBackObserver<ForumListBean>(this) {
            @Override
            public void responseSuccess(ForumListBean baseBean) {
                mIView.forumList(baseBean);
            }

            @Override
            public void responseFail(ForumListBean baseBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }

    public void findFollow(int page, INetTip tip) {
        ForumPojo forumPojo = new ForumPojo();
        forumPojo.pageNo = page + "";
        forumPojo.userId = WmtApplication.id;
        mModel.findFollow(forumPojo).subscribe(new DefaultCallBackObserver<FollowListBean>(tip,this) {
            @Override
            public void responseSuccess(FollowListBean baseBean) {
                mIView.followList(baseBean);
            }

            @Override
            public void responseFail(FollowListBean baseBean) {
                mIView.showEmpty();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.completeFollow();
            }
        });
    }

    public void zan(String status, String forumId, int position) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.status = status;
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumZan(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView.getActivity()),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.zan(position);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }


    public void forumCollection(String status, String forumId) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.status = status;
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumCollection(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView.getActivity()),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {

            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void followShopOrUser(String status, String businessId) {
        FollowPojo pojo = new FollowPojo();
        pojo.businessId = businessId;
        pojo.attentionType = "1";
        pojo.attentionStatus = status;
        pojo.createUser = WmtApplication.id;
        mModel.followShopOrUser(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView.getActivity()),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.setFollow();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

}
