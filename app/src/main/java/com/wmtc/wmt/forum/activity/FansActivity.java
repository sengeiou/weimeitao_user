package com.wmtc.wmt.forum.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.FansListAdapter;
import com.wmtc.wmt.forum.bean.FansListBean;
import com.wmtc.wmt.forum.presenter.ForumFansListPresenter;

import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FansActivity extends CommonWmtActivity {

    ForumFansListPresenter mPresenter;
    String mId;
    private FansListAdapter mAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_fans;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mPresenter = new ForumFansListPresenter(this);
        mAdapter = new FansListAdapter(null);
        showLoading();
        mId = mBundle.getString("id");
        mPresenter.fansList(mId);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {

            if (view1.getId() == R.id.tvUserFollow) {
                String status;
                if (view1.isSelected()) {
                    status = "-1";
                } else {
                    status = "1";
                }
                FansListBean.DataBean dataBean = mAdapter.getData().get(position);
                dataBean.attntionStatus = status;
                view1.setSelected(!view1.isSelected());
                mAdapter.notifyItemChanged(position);
                mPresenter.followShopOrUser(status, dataBean.attentionUserId);
            }
            return false;
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            FansListBean.DataBean dataBean = mAdapter.getData().get(position);
            bundle.putString("id", dataBean.attentionUserId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.fansList(mId);
    }

    public void fanList(FansListBean fansListBean) {
        responseSuccess();
        if (fansListBean.data != null && fansListBean.data.size() > 0) {
            mAdapter.setNewData(fansListBean.data);
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
