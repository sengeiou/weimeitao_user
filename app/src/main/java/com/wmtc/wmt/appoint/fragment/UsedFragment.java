package com.wmtc.wmt.appoint.fragment;

import android.view.View;

import top.jplayer.baseprolibrary.utils.klog.KLog;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.RedBagHistoryActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.appoint.pojo.CouponPojo;
import com.wmtc.wmt.personal.presenter.UsedPresenter;
import com.wmtc.wmt.forum.adapter.UasedAdapter;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;

public class UsedFragment extends SuperBaseFragment {

    private UasedAdapter mAdapter;
    private UsedPresenter mPresenter;
    private String type = "1";

    @Override
    public int initLayout() {
        return R.layout.activity_used;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mPresenter = new UsedPresenter(this);
        mAdapter = new UasedAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        RedBagHistoryActivity activity = (RedBagHistoryActivity) mActivity;
        type = activity.type;
        KLog.e("TAG", "已使用-获得到的type" + type);
        if ("优惠劵历史".equals(type)) {
            this.type = "1";
        } else {
            this.type = "2";
        }
        getCouponList(type, "", "2");
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
            mAdapter.setNewData(data.list);
        } else {
            showEmpty();
        }
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        getCouponList(type, "", "2");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
