package com.wmtc.wmt.forum.presenter;

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
public class MoreCommentPresenter extends BasePresenter<MoreCommentActivity> {

    private final CommonModel mModel;

    public MoreCommentPresenter(MoreCommentActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void forumComment(int page, String subitemId, BaseNetTip tip) {
        ForumPojo pojo = new ForumPojo();
        pojo.userId = WmtApplication.id;
        pojo.pageNo = page + "";
        pojo.subitemId = subitemId;
        mModel.forumComment(pojo).subscribe(new DefaultCallBackObserver<ForumCommentBean>(tip, this) {
            @Override
            public void responseSuccess(ForumCommentBean forumCommentBean) {
                mIView.commentList(forumCommentBean);
            }

            @Override
            public void responseFail(ForumCommentBean forumCommentBean) {

            }
        });
    }

    public void zan(String status, String forumId) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.status = status;
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumZan(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {

            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

}
