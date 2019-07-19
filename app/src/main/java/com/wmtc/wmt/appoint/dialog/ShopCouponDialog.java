package com.wmtc.wmt.appoint.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.RedBagWillActivity;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.base.WmtApplication;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopCouponDialog extends BaseCustomDialog {

    private TextView mTvLimit;
    private TextView mTvPrice;
    private TextView mTvTypeName;
    private TextView mTvTypeUse;

    public ShopCouponDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mTvLimit = view.findViewById(R.id.tvLimit);
        mTvPrice = view.findViewById(R.id.tvPrice);
        mTvTypeName = view.findViewById(R.id.tvTypeName);
        mTvTypeUse = view.findViewById(R.id.tvTypeUse);
        view.findViewById(R.id.tvToHbList).setOnClickListener(v -> {
            ActivityUtils.init().start((Activity) view.getContext(), RedBagWillActivity.class, "红包卡券");
        });
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_shop_coupon;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void bindBean(ShopCouponBean.DataBean data) {
        if (data != null) {
            mTvPrice.setText(StringUtils.init().fixNullStrMoney(data.couponAmount, "￥"));
            String limit = "无门槛使用";
            if (!"0".equals(data.minPayAmount)) {
                limit = "满" + StringUtils.init().fixNullStrMoney(data.minPayAmount) + "使用";
            }
            mTvLimit.setText(limit);
            mTvTypeName.setText(StringUtils.init().fixNullStr(data.shopOrProjectName));
            mTvTypeUse.setText(StringUtils.init().fixNullStr(data.scopeName));
        }
    }
}
