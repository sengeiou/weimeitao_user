package com.wmtc.wmt.appoint.activity;

import android.widget.FrameLayout;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.TeachAdapter;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.appoint.event.TeachSelEvent;
import com.wmtc.wmt.appoint.presenter.TeachSelPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SelectTeachActivity extends CommonWmtActivity {

    private TeachSelPresenter mPresenter;
    private TeachAdapter mAdapter;
    private String mArrivalTime;
    private String mShopId;

    @Override
    public int initAddLayout() {
        return R.layout.activity_select_teach;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mAdapter = new TeachAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new TeachSelPresenter(this);
        showLoading();
        mArrivalTime = mBundle.getString("arrivalTime");
        mShopId = mBundle.getString("id");
        mPresenter.selTeach(mShopId, mArrivalTime);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                TeachSelBean.DataBean dataBean = mAdapter.getData().get(i);
                if (i == position) {
                    dataBean.isSel = true;
                } else {
                    dataBean.isSel = false;
                }
            }
            mAdapter.notifyDataSetChanged();
            EventBus.getDefault().post(new TeachSelEvent(mAdapter.getData().get(position)));
            finish();
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                TeachSelBean.DataBean dataBean = mAdapter.getData().get(i);
                if (i == position) {
                    dataBean.isSel = true;
                } else {
                    dataBean.isSel = false;
                }
            }
            mAdapter.notifyDataSetChanged();
            EventBus.getDefault().post(new TeachSelEvent(mAdapter.getData().get(position)));
            return false;
        });
    }


    public void selTeach(TeachSelBean bean) {
        responseSuccess();
        if (bean.data != null) {
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
