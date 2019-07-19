package com.wmtc.wmt.forum.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CanCouponBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

public class UnUseCouponAdapter extends BaseQuickAdapter<CanCouponBean.DataBean.ListBean, BaseViewHolder> {

    public UnUseCouponAdapter(List<CanCouponBean.DataBean.ListBean> data) {
        super(R.layout.item_un_use_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CanCouponBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_money, "￥" + StringUtils.init().fixNullStrMoney(item.discountedAmount))
                .setText(R.id.tv_mk, getMK(item))
                .setText(R.id.tv_name, item.couponName)
                .setText(R.id.tv_time, "有效期：" + item.couponStartTime + "-" + item.couponEndTime);
        helper.setText(R.id.tv_fw, "不可用原因：" + item.couponType);
    }

    private String getMK(CanCouponBean.DataBean.ListBean item) {
        if ("0".equals(item.minPayAmount)) {
            return "无门槛";
        } else {
            return "满" + item.minPayAmount + "减";
        }
    }

}
