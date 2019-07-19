package com.wmtc.wmt.appoint.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.RedBagDetailsActivity;
import com.wmtc.wmt.appoint.activity.RedBagHistoryActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.appoint.pojo.CouponPojo;
import com.wmtc.wmt.appoint.presenter.RedBagPresenter;
import com.wmtc.wmt.forum.adapter.DiscountsAdapter;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

public class RedBagFragment extends SuperBaseFragment {
    private TextView tv_num;
    private RelativeLayout rl_ls;
    private DiscountsAdapter mAdapter;
    private RedBagPresenter mPresenter;

    @Override
    public int initLayout() {
        return R.layout.fragment_redbag;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mPresenter = new RedBagPresenter(this);
        mAdapter = new DiscountsAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        showLoading();
        //初始化
        initView(rootView);
        getCouponList("2", "", "1");
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            DiscountsBean.DataBean.ListBean bean = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("couponId", bean.couponId);
            bundle.putString("ownerId", bean.ownerId);
            bundle.putString("couponName", bean.couponName);
            bundle.putString("discountedAmount", bean.discountedAmount);
            bundle.putString("minPayAmount", bean.minPayAmount);
            bundle.putString("couponStartTime", bean.couponStartTime);
            bundle.putString("couponEndTime", bean.couponEndTime);
            bundle.putString("titleName", bean.titleName);
            bundle.putString("onlineType", bean.onlineType);
            bundle.putString("dayNum", bean.dayNum);
            bundle.putString("couponType", bean.couponType);
            bundle.putString("couponTypeCode", bean.couponTypeCode);
            bundle.putString("shopId", StringUtils.init().fixNullStr(bean.shopId));
            ActivityUtils.init().start(mActivity, RedBagDetailsActivity.class, "红包详情", bundle);
        });
    }

    //初始化
    private void initView(View view) {
        tv_num = view.findViewById(R.id.tv_num);
        rl_ls = view.findViewById(R.id.rl_ls);
        rl_ls.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", "红包历史");
            ActivityUtils.init().start(mActivity, RedBagHistoryActivity.class, "红包历史", bundle);
        });
    }

    private void getCouponList(String couponConfigType, String couponType, String couponStatus) {
        CouponPojo pojo = new CouponPojo();
        pojo.setCouponConfigType(couponConfigType);
        pojo.setCouponType(couponType);
        pojo.setCouponStatus(couponStatus);
        pojo.setOwnerId(WmtApplication.id);
        mPresenter.getCouponList(pojo);
    }

    public void getCouponListDate(DiscountsBean bean) {
        responseSuccess();
        DiscountsBean.DataBean data = bean.data;
        if (data != null && data.list != null && data.list.size() > 0) {
            tv_num.setText("共" + data.list.size() + "张可用");
            mAdapter.setNewData(data.list);
        } else {
            showEmpty();
        }
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        getCouponList("2", "", "1");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
