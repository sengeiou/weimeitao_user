package com.wmtc.wmt.forum.presenter;

import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.bean.ForumDetailBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.CommentSavePojo;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.forum.activity.ForumDetailActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.BaseNetTip;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumDetailPresenter extends BasePresenter<ForumDetailActivity> {

    private final CommonModel mModel;

    public ForumDetailPresenter(ForumDetailActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void forumDetail(String forumId, BaseNetTip tip) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumDetail(pojo).subscribe(new DefaultCallBackObserver<ForumDetailBean>(tip,this) {
            @Override
            public void responseSuccess(ForumDetailBean forumDetailBean) {
                mIView.forumDetail(forumDetailBean);
            }

            @Override
            public void responseFail(ForumDetailBean forumDetailBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }

    public void forumComment(int page, String subitemId, BaseNetTip tip) {
        ForumPojo pojo = new ForumPojo();
        pojo.userId = WmtApplication.id;
        pojo.pageNo = page + "";
        pojo.subitemId = subitemId;
        mModel.forumComment(pojo).subscribe(new DefaultCallBackObserver<ForumCommentBean>(tip,this) {
            @Override
            public void responseSuccess(ForumCommentBean forumCommentBean) {
                mIView.commentList(forumCommentBean);
            }

            @Override
            public void responseFail(ForumCommentBean forumCommentBean) {

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
        mModel.commentarySave(pojo).subscribe(new DefaultCallBackObserver<CommentSaveBean>(new LoaddingErrorImplTip(mIView),this) {
            @Override
            public void responseSuccess(CommentSaveBean bean) {
                mIView.commentSave(bean);
            }

            @Override
            public void responseFail(CommentSaveBean forumCommentBean) {

            }
        });
    }

    public void zan(String status, String forumId) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.status = status;
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumZan(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.zan();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void del(String forumId) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.detTeizi(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.del();
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
        mModel.forumCollection(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView)
                ,this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.zan();
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
        mModel.followShopOrUser(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.follow();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

}
