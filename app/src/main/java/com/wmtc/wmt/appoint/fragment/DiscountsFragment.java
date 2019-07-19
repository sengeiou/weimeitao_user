package com.wmtc.wmt.appoint.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.jplayer.baseprolibrary.utils.klog.KLog;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.RedBagDetailsActivity;
import com.wmtc.wmt.appoint.activity.RedBagHistoryActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.appoint.pojo.CouponPojo;
import com.wmtc.wmt.appoint.presenter.DiscountsPresenter;
import com.wmtc.wmt.forum.adapter.DiscountsAdapter;

import java.util.Locale;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

public class DiscountsFragment extends SuperBaseFragment {

    private TextView tv_all;
    private TextView tv_shop;
    private TextView tv_xm;
    private TextView tv_ty;
    private TextView tv_num;
    private RelativeLayout rl_ls;
    private String type = "";
    private DiscountsAdapter mAdapter;
    private DiscountsPresenter mPresenter;

    @Override
    public int initLayout() {
        return R.layout.fragment_discounts;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mPresenter = new DiscountsPresenter(this);
        initView(rootView);
        mAdapter = new DiscountsAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        showLoading();
        getCouponList();
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
            bundle.putString("onlineType", bean.onlineType);
            bundle.putString("dayNum", bean.dayNum);
            bundle.putString("couponType", bean.couponType);
            bundle.putString("couponTypeCode", bean.couponTypeCode);
            bundle.putString("shopId", StringUtils.init().fixNullStr(bean.shopId));
            ActivityUtils.init().start(mActivity, RedBagDetailsActivity.class, "优惠券详情", bundle);
        });
    }

    private void getCouponList() {
        CouponPojo pojo = new CouponPojo();
        pojo.setCouponConfigType("1");
        pojo.setCouponType(type);
        pojo.setCouponStatus("1");
        pojo.setOwnerId(WmtApplication.id);
        mPresenter.getCouponList(pojo);
    }

    public void getCouponListDate(DiscountsBean bean) {
        responseSuccess();
        DiscountsBean.DataBean data = bean.data;
        int size = 0;
        if (data.list != null) {
            size = data.list.size();
        }
        tv_num.setText(String.format(Locale.CHINA, "共%d张可用", size));
        if (data.list != null && data.list.size() > 0) {
            mAdapter.setNewData(data.list);
        } else {
            showEmpty();
        }
    }

    private void initView(View view) {
        tv_all = view.findViewById(R.id.tv_all);
        tv_num = view.findViewById(R.id.tv_num);
        tv_all.setOnClickListener(v -> {
            tv_all.setBackgroundResource(R.drawable.btn_hb_bg_on);
            tv_all.setTextColor(Color.parseColor("#FF5B61"));
            tv_shop.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_shop.setTextColor(Color.parseColor("#222222"));
            tv_xm.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_xm.setTextColor(Color.parseColor("#222222"));
            tv_ty.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_ty.setTextColor(Color.parseColor("#222222"));
            type = "";
            getCouponList();
            tv_all.setClickable(false);
            tv_shop.setClickable(false);
            tv_xm.setClickable(false);
            tv_ty.setClickable(false);
        });
        tv_shop = view.findViewById(R.id.tv_shop);
        tv_shop.setOnClickListener(v -> {
            tv_all.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_all.setTextColor(Color.parseColor("#222222"));
            tv_shop.setBackgroundResource(R.drawable.btn_hb_bg_on);
            tv_shop.setTextColor(Color.parseColor("#FF5B61"));
            tv_xm.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_xm.setTextColor(Color.parseColor("#222222"));
            tv_ty.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_ty.setTextColor(Color.parseColor("#222222"));
            type = "1";
            getCouponList();
            tv_all.setClickable(false);
            tv_shop.setClickable(false);
            tv_xm.setClickable(false);
            tv_ty.setClickable(false);
        });
        tv_xm = view.findViewById(R.id.tv_xm);
        tv_xm.setOnClickListener(v -> {
            tv_all.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_all.setTextColor(Color.parseColor("#222222"));
            tv_shop.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_shop.setTextColor(Color.parseColor("#222222"));
            tv_xm.setBackgroundResource(R.drawable.btn_hb_bg_on);
            tv_xm.setTextColor(Color.parseColor("#FF5B61"));
            tv_ty.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_ty.setTextColor(Color.parseColor("#222222"));
            type = "2";
            getCouponList();
            tv_all.setClickable(false);
            tv_shop.setClickable(false);
            tv_xm.setClickable(false);
            tv_ty.setClickable(false);
        });
        tv_ty = view.findViewById(R.id.tv_ty);
        tv_ty.setOnClickListener(v -> {
            tv_all.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_all.setTextColor(Color.parseColor("#222222"));
            tv_shop.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_shop.setTextColor(Color.parseColor("#222222"));
            tv_xm.setBackgroundResource(R.drawable.btn_hb_bg_off);
            tv_xm.setTextColor(Color.parseColor("#222222"));
            tv_ty.setBackgroundResource(R.drawable.btn_hb_bg_on);
            tv_ty.setTextColor(Color.parseColor("#FF5B61"));
            type = "3";
            getCouponList();
            tv_all.setClickable(false);
            tv_shop.setClickable(false);
            tv_xm.setClickable(false);
            tv_ty.setClickable(false);
        });
        rl_ls = view.findViewById(R.id.rl_ls);
        rl_ls.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", "优惠劵历史");
            ActivityUtils.init().start(mActivity, RedBagHistoryActivity.class, "优惠劵历史", bundle);
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        getCouponList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public void complete() {
        tv_all.setClickable(true);
        tv_shop.setClickable(true);
        tv_xm.setClickable(true);
        tv_ty.setClickable(true);
    }
}
