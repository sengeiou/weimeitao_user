package com.wmtc.wmt.appoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

public class RedBagDetailsActivity extends CommonWmtActivity {

    private TextView tv_money, tv_mk, tv_name, tv_fw, tv_time;
    private TextView tv_1;
    private TextView tv_2;
    private TextView btn_use;
    private String couponId = "", ownerId = "", couponName = "", discountedAmount = "", minPayAmount = "", couponStartTime = "",
            couponEndTime = "", onlineType = "", dayNum = "", couponType = "", couponTypeCode = "", shopId = "", titleName = "";

    @Override
    public int initAddLayout() {
        return R.layout.activity_redbag_details;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        couponId = mBundle.getString("couponId");
        ownerId = mBundle.getString("ownerId");
        couponName = mBundle.getString("couponName");
        discountedAmount = mBundle.getString("discountedAmount");
        minPayAmount = mBundle.getString("minPayAmount");
        couponStartTime = mBundle.getString("couponStartTime");
        couponEndTime = mBundle.getString("couponEndTime");
        titleName = mBundle.getString("titleName");
        onlineType = mBundle.getString("onlineType");
        dayNum = mBundle.getString("dayNum");
        couponType = mBundle.getString("couponType");
        couponTypeCode = mBundle.getString("couponTypeCode");
        shopId = mBundle.getString("shopId");
        initView();
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    //初始化
    private void initView() {
        tv_money = findViewById(R.id.tv_money);
        tv_money.setText(StringUtils.init().fixNullStrMoney(discountedAmount, "￥"));
        tv_mk = findViewById(R.id.tv_mk);
        if ("0".equals(minPayAmount)) {
            tv_mk.setText("无门槛使用");
        } else {
            String delMoney = "满" + StringUtils.init().fixNullStrMoney(minPayAmount) + "使用";
            tv_mk.setText(delMoney);
        }
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(couponName);
        tv_fw = findViewById(R.id.tv_fw);
        if (StringUtils.isBlank(titleName)) {
            String limit = "使用范围：" + couponType;
            tv_fw.setText(limit);
        } else {
            String limit = "使用范围：" + titleName + getline(onlineType);
            tv_fw.setText(limit);
        }
        tv_time = findViewById(R.id.tv_time);
        if ("0".equals(dayNum)) {
            tv_time.setText("(今天过期)");
        } else if ("3".equals(dayNum) || "2".equals(dayNum) || "1".equals(dayNum)) {
            String day = "(仅剩" + dayNum + "天)";
            tv_time.setText(day);
        } else {
            String time = "有效期：" + couponStartTime + "至" + couponEndTime;
            tv_time.setText(time);
        }
        btn_use = findViewById(R.id.btn_use);
        if ("".equals(shopId)) {
            btn_use.setVisibility(View.GONE);
        } else {
            btn_use.setVisibility(View.VISIBLE);
            btn_use.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id",shopId);
                bundle.putParcelable("pojo", null);
                ActivityUtils.init().start(mActivity, ShopInfoActivity.class, "", bundle);
            });
        }
        tv_1 = findViewById(R.id.tv_1);
        if ("0".equals(minPayAmount)) {
            tv_1.setText("无门槛使用");
        } else {
            String text = "满" + StringUtils.init().fixNullStrMoney(minPayAmount) + "元可用";
            tv_1.setText(text);
        }
        tv_2 = findViewById(R.id.tv_2);
        String text = "限" + couponStartTime + "至" + couponEndTime + "可用";
        tv_2.setText(text);
    }

    private String getline(String onlineType) {
        if ("0".equals(onlineType)) {
            return "，线上线下通用";
        } else {
            return "，仅线上使用";
        }
    }

}
