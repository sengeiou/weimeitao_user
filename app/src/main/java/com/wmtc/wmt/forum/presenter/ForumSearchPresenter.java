package com.wmtc.wmt.forum.presenter;

import android.annotation.SuppressLint;

import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.HistorySearchBean;
import com.wmtc.wmt.forum.bean.HotSearchBean;
import com.wmtc.wmt.forum.bean.SearchUserBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.forum.model.ForumDaoModel;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.forum.pojo.SearchUserPojo;
import com.wmtc.wmt.forum.activity.ForumSearchActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;

/**
 * Created by Obl on 2019/5/5.
 * com.wmtc.wmt.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumSearchPresenter extends BasePresenter<ForumSearchActivity> {

    private final CommonModel mModel;
    private final ForumDaoModel mDaoModel;

    public ForumSearchPresenter(ForumSearchActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
        mDaoModel = new ForumDaoModel(mIView);

    }

    public void findForum(int page, String key,String type) {
        ForumPojo forumPojo = new ForumPojo();
        forumPojo.pageNo = page + "";
        forumPojo.type = type;
        forumPojo.userId = WmtApplication.id;
        forumPojo.searchKey = key;
        mModel.findForum(forumPojo).subscribe(new DefaultCallBackObserver<ForumListBean>(new ErrorResponseImplTip(mIView),this) {
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


    public void searchUser(String key) {
        SearchUserPojo searchUserPojo = new SearchUserPojo();
        searchUserPojo.searchUserKey = key;
        searchUserPojo.userId = WmtApplication.id;
        mModel.searchUser(searchUserPojo).subscribe(new DefaultCallBackObserver<SearchUserBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(SearchUserBean searchUserBean) {
                mIView.searchUserList(searchUserBean);
            }

            @Override
            public void responseFail(SearchUserBean searchUserBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.userComplete();
            }
        });
    }

    public void hotList() {
        mModel.hotSearch().subscribe(new DefaultCallBackObserver<HotSearchBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(HotSearchBean hotSearchBean) {
                mIView.hotList(hotSearchBean);
            }

            @Override
            public void responseFail(HotSearchBean hotSearchBean) {

            }
        });
    }

    public void zan(String status, String forumId, int position) {
        ForumZanPojo pojo = new ForumZanPojo();
        pojo.status = status;
        pojo.forumId = forumId;
        pojo.userId = WmtApplication.id;
        mModel.forumZan(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new ErrorResponseImplTip(mIView),this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.zan(position);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }


    @SuppressLint("CheckResult")
    public void history() {
        List<String> list = new ArrayList<>();
        List<HistorySearchBean> searchBeans = mDaoModel.queryAllbean();
        Observable.just(mDaoModel)
                .subscribeOn(Schedulers.io())
                .map(forumDaoModel -> mDaoModel.queryAllbean())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historySearchBeans -> {
                    Observable.fromIterable(historySearchBeans).subscribe(historySearchBean -> {
                        list.add(historySearchBean.name);
                    });
                    mIView.history(list);
                });
    }

    public void addHistory(String string, List<String> list) {
        if (list == null || !list.contains(string)) {
            HistorySearchBean bean = new HistorySearchBean();
            bean.name = string;
            mDaoModel.insertUser(bean);

        }
    }

    public void delHistory() {
        mDaoModel.deleteAll();
        mIView.delOk();
    }
}
