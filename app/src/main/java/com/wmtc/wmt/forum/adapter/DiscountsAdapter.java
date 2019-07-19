package com.wmtc.wmt.forum.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DiscountsBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

public class DiscountsAdapter extends BaseQuickAdapter<DiscountsBean.DataBean.ListBean, BaseViewHolder> {


    public DiscountsAdapter(List<DiscountsBean.DataBean.ListBean> data) {
        super(R.layout.item_discount, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscountsBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.discountedAmount, "￥"))
                .setText(R.id.tv_mk, getMK(item))
                .setText(R.id.tv_name, item.couponName)
                .setText(R.id.tv_time, "有效期：" + item.couponStartTime + "至" + item.couponEndTime);
        if (StringUtils.isBlank(item.titleName)) {
            helper.setText(R.id.tv_fw, "使用范围：" + item.couponType);
        } else {
            helper.setText(R.id.tv_fw, "使用范围：" + item.titleName + getline(item));
        }
    }

    private String getMK(DiscountsBean.DataBean.ListBean item) {
        if ("0".equals(item.minPayAmount)) {
            return "无门槛使用";
        } else {
            return "满" + StringUtils.init().fixNullStrMoney(item.minPayAmount) + "使用";
        }
    }

    private String getline(DiscountsBean.DataBean.ListBean item) {
        if ("0".equals(item.onlineType)) {
            return "，线上线下通用";
        } else {
            return "，仅线上使用";
        }
    }

}
