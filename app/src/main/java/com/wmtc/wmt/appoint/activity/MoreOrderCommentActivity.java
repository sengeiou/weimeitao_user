package com.wmtc.wmt.appoint.activity;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.MoreOrderCommentAdapter;
import com.wmtc.wmt.appoint.bean.JudgeBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.presenter.MoreOrderCommentPresenter;
import com.wmtc.wmt.forum.activity.ForumMeActivity;
import com.wmtc.wmt.forum.adapter.ForumDetailAdapter;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.presenter.MoreCommentPresenter;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/5/15.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MoreOrderCommentActivity extends SuperBaseActivity {

    private MoreOrderCommentAdapter mAdapter;
    private MoreOrderCommentPresenter mPresenter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_more_comment;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.llToolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mAdapter = new MoreOrderCommentAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", mAdapter.getData().get(position).userId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mRecyclerView.setAdapter(mAdapter);
        showLoading();
        view.findViewById(R.id.ivDel).setOnClickListener(v -> finish());
        mPresenter = new MoreOrderCommentPresenter(this);
        mPresenter.getOrderJudge(mBundle.getString("id"));
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.getOrderJudge(mBundle.getString("id"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public void getAllJudgeDate(OrderJudgeBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.resultList != null && bean.data.resultList.size() > 0) {
            mAdapter.setNewData(bean.data.resultList);
        } else {
            showEmpty();
        }
    }
}
