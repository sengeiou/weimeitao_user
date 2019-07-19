package com.wmtc.wmt.forum.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

public class DialogAdapter extends BaseQuickAdapter<CouponDialogbean.DataBean, BaseViewHolder> {


    public DialogAdapter(List<CouponDialogbean.DataBean> data) {
        super(R.layout.item_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponDialogbean.DataBean item) {
        helper.setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.minPayAmount,"￥"))
                .setText(R.id.tv_mk, getMK(item))
                .setText(R.id.tv_fw, StringUtils.init().fixNullStr(item.scopeName))
                .setText(R.id.tv_name, item.couponName)
                .setText(R.id.tv_time, "有效期：" + item.startTime + "至" + item.endTime);
    }

    private String getMK(CouponDialogbean.DataBean item) {
        if ("0".equals(item.minPayAmount)) {
            return "无门槛";
        } else {
            return "满" + StringUtils.init().fixNullStrMoney(item.minPayAmount) + "可用";
        }
    }
}

