package com.wmtc.wmt.forum.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.dialog.UseCouponDialog;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

public class UseCouponAdapter extends BaseQuickAdapter<CanCouponBean.DataBean.ListBean, BaseViewHolder> {

    UseCouponDialog mDialog;

    public UseCouponAdapter(List<CanCouponBean.DataBean.ListBean> data, UseCouponDialog useCouponDialog) {
        super(R.layout.item_use_coupon, data);
        this.mDialog = useCouponDialog;
    }

    @Override
    protected void convert(BaseViewHolder helper, CanCouponBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.discountedAmount))
                .setText(R.id.tv_mk, getMK(item))
                .setText(R.id.tv_name, item.couponName)
                .setText(R.id.tv_time, "有效期：" + item.couponStartTime + "至" + item.couponEndTime);
        if (StringUtils.isBlank(item.titleName)) {
            helper.setText(R.id.tv_fw, "使用范围：" + item.couponType);
        } else {
            helper.setText(R.id.tv_fw, "使用范围：" + item.titleName + getline(item));
        }
        TextView tvSel = helper.itemView.findViewById(R.id.tvSel);
        tvSel.setSelected(item.isSel);
        ConstraintLayout itemSel = helper.itemView.findViewById(R.id.rl_item);
        itemSel.setSelected(item.isSel);
        if (mDialog.isNotUse) {
            itemSel.setBackgroundResource(R.drawable.coupon_not_use);
            tvSel.setVisibility(View.GONE);
        } else {
            tvSel.setVisibility(View.VISIBLE);
            itemSel.setBackgroundResource(R.drawable.selector_sel_coupon);
            helper.addOnClickListener(R.id.tvSel)
                    .addOnClickListener(R.id.rl_item);
        }
    }

    private String getMK(CanCouponBean.DataBean.ListBean item) {
        if ("0".equals(item.minPayAmount)) {
            return "无门槛使用";
        } else {
            return "满" + StringUtils.init().fixNullStrMoney(item.minPayAmount) + "使用";
        }
    }

    private String getline(CanCouponBean.DataBean.ListBean item) {
        if ("0".equals(item.onlineType)) {
            return "，线上线下通用";
        } else {
            return "，仅线上使用";
        }
    }
}
