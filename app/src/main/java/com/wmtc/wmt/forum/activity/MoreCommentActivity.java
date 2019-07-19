package com.wmtc.wmt.forum.activity;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.adapter.CommunityListAdapter;
import com.wmtc.wmt.forum.adapter.ForumDetailAdapter;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.presenter.MoreCommentPresenter;

import java.util.Locale;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/5/15.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MoreCommentActivity extends SuperBaseActivity {

    private ForumDetailAdapter mAdapter;
    private MoreCommentPresenter mPresenter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_more_comment;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.llToolBar).keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mAdapter = new ForumDetailAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", mAdapter.getData().get(position).userId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mRecyclerView.setAdapter(mAdapter);
        showLoading();
        view.findViewById(R.id.ivDel).setOnClickListener(v -> finish());
        mPresenter = new MoreCommentPresenter(this);
        mPresenter.forumComment(1, mBundle.getString("id"), null);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.forumComment(1, mBundle.getString("id"), null);
    }

    public void commentList(ForumCommentBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.size() > 0) {
            mAdapter.setNewData(bean.data);
        } else {
            showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
