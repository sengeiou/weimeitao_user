package com.wmtc.wmt.message.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.message.adapter.ChildMessageAdapter;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.presenter.ChildMessagePresenter;

import java.util.List;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChildMessageFragment extends SuperBaseFragment {

    private ChildMessageAdapter mAdapter;
    private ChildMessagePresenter mPresenter;
    public String type;

    @Override
    public int initLayout() {
        return R.layout.fragment_child_message;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mAdapter = new ChildMessageAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ChildMessagePresenter(this);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.right) {
                mPresenter.deleteMessage(mAdapter.getData().get(position).messageId);
                mAdapter.remove(position);
            } else if (view.getId() == R.id.content) {
                MessageBean.DataBean dataBean = mAdapter.getData().get(position);
                if ("orderDetail".equals(dataBean.urlName)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", dataBean.urlParam);
                    ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
                }
            }
            return false;
        });

    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.requestMessage(type);
    }

    public void requestList(String type) {
        showLoading();
        this.type = type;
        mPresenter.requestMessage(type);
    }

    public void responseMessage(MessageBean bean) {
        responseSuccess();
        List<MessageBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mAdapter.setNewData(data);
        } else {
            showEmpty();
        }
    }

    public void delMessage() {

    }
}
