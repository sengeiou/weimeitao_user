package com.wmtc.wmt.forum.presenter;

import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.activity.ForumMeActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.ForumMeBean;
import com.wmtc.wmt.forum.bean.MeForumCountBean;
import com.wmtc.wmt.forum.pojo.ForumCountPojo;
import com.wmtc.wmt.forum.pojo.ForumMeListPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/9.
 * com.wmtc.wmt.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumMePresenter extends BasePresenter<ForumMeActivity> {

    private final CommonModel mModel;

    public ForumMePresenter(ForumMeActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getUserInfo(String uid) {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserId(uid);
        mModel.getUserInfo(userPojo).subscribe(new DefaultCallBackObserver<UserInfoBean>(new ErrorResponseImplTip(mIView), this) {

            @Override
            public void responseSuccess(UserInfoBean bean) {
                mIView.getUserInfo(bean);
            }

            @Override
            public void responseFail(UserInfoBean bean) {

            }
        });
    }

    public void del(String forumId) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.detTeizi(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView), this) {

            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.del();
                LogUtil.e(baseBean);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }


    public void zan(String status, String forumId, int position) {
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


    public void zanfansNum(String uid) {
        ForumPojo pojo = new ForumPojo();
        pojo.userId = uid;
        mModel.findCommunityHomeStatisticCount(pojo).subscribe(new DefaultCallBackObserver<ForumMeBean>(new ErrorResponseImplTip(mIView), this) {

            @Override
            public void responseSuccess(ForumMeBean forumMeBean) {
                mIView.zanFansNum(forumMeBean);
            }

            @Override
            public void responseFail(ForumMeBean forumMeBean) {

            }
        });
    }

    public void socialHomeForumList(String forumType, String operationType, int page, String uid) {
        ForumMeListPojo listPojo = new ForumMeListPojo();
        listPojo.userId = uid;
        listPojo.forumType = forumType;
        listPojo.pageNo = page + "";
        listPojo.operationType = operationType;
        mModel.socialHomeForumList(listPojo).subscribe(new DefaultCallBackObserver<ForumListBean>(new ErrorResponseImplTip(mIView), this) {

            @Override
            public void responseSuccess(ForumListBean listBean) {
                mIView.socialHomeForumList(listBean);
            }

            @Override
            public void responseFail(ForumListBean listBean) {

            }
        });
    }

    public void findFourmCount(String type, int position, String uid) {
        ForumCountPojo pojo = new ForumCountPojo();
        pojo.userId = uid;
        pojo.type = type;
        DefaultCallBackObserver<MeForumCountBean> observer = new DefaultCallBackObserver<MeForumCountBean>(new ErrorResponseImplTip(mIView), this) {
            @Override
            public void responseSuccess(MeForumCountBean bean) {
                mIView.forumCountBean(bean);
            }

            @Override
            public void responseFail(MeForumCountBean bean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.setTabComplete();
            }
        };
        mModel.findFourmCount(pojo).subscribe(observer);
    }

    public void updateUserInfo(String content) {
        UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
        pojo.setIntroduce(content);
        pojo.setUserId(WmtApplication.id);
        mModel.updateUserInfo(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updateUserInfo(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }


}
