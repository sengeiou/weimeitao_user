package com.wmtc.wmt.appoint.fragment;

import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.forum.adapter.UnUseCouponAdapter;

import java.util.List;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;

public class UnUsecouponFragment extends SuperBaseFragment {

    private UnUseCouponAdapter mAdapter;

    @Override
    public int initLayout() {
        return R.layout.activity_unusecoupon;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mAdapter = new UnUseCouponAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setBean(List<CanCouponBean.DataBean.ListBean> data) {
        if (data != null && data.size() > 0) {
            mAdapter.setNewData(data);
        }
    }
}
