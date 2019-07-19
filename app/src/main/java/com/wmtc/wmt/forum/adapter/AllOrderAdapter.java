package com.wmtc.wmt.forum.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.OrderListBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/23.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AllOrderAdapter extends BaseQuickAdapter<OrderListBean.DataBean.ListBean, BaseViewHolder> {
    public AllOrderAdapter(List<OrderListBean.DataBean.ListBean> data) {
        super(R.layout.item_order_style, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean.ListBean item) {
        ImageView iv = helper.itemView.findViewById(R.id.im_pic);
        Glide.with(mContext)
                .load(WmtApplication.url_host + item.proPic)
                .apply(GlideUtils.init().roundTransFrom(mContext, 5, R.drawable.wmt_default))
                .into(iv);
        int couponPrice = item.couponPrice;
        int hbPrice = 0;
        try {
            if (StringUtils.isBlank(item.hongbaoPrice)) {
                hbPrice = 0;
            } else {
                hbPrice = Integer.parseInt(item.hongbaoPrice);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int endPrice = Integer.parseInt(item.projectPriceEnd);
        int fixPrice = endPrice - (hbPrice + couponPrice);
        if (fixPrice < 1) {
            fixPrice = 1;
        }
        helper.setText(R.id.tv_name, StringUtils.init().fixNullStr(item.projectName))
                .setText(R.id.tv_shop, StringUtils.init().fixNullStr(item.shopName))
                .setText(R.id.tv_time, "到店时间:" + StringUtils.init().fixNullStr(item.arrivalTime))
                .setVisible(R.id.rl_wait_order, getStatus(item).equals("待支付"))
                .setVisible(R.id.tv_dtime, getStatus(item).equals("待支付"))
                .setText(R.id.tv_wait_order, StringUtils.init().fixNullStrMoney(item.projectPriceFirst, "支付预约金:￥"))
                .addOnClickListener(R.id.tv_wait_order)
                .setVisible(R.id.rl_daodian_order, getStatus(item).equals("待到店"))
                .setText(R.id.tv_daodian_order, StringUtils.init().fixNullStrMoney(fixPrice + "", "到店再付:￥"))
                .addOnClickListener(R.id.tv_daodian_order)
                .addOnClickListener(R.id.tv_quxiao)
                .setText(R.id.tt_t, StringUtils.init().fixNullStr(item.afterArrivalTime.split(" ")[1] + "之前免责取消"))
                .addOnClickListener(R.id.tt_t)
                .setVisible(R.id.rl_pingjia_order, getStatus(item).equals("待评价"))
                .addOnClickListener(R.id.tv_pingjia_r)
                .addOnClickListener(R.id.tv_pingjia_m)
                .setText(R.id.tv_pingjia_m, item.commentStatus)
                .addOnClickListener(R.id.tv_pingjia_l)
                .setVisible(R.id.rl_delete_order, getStatus(item).equals("可删除"))
                .addOnClickListener(R.id.tv_delete)
                .setText(R.id.tv_state, item.orderStatusName)
                .addOnClickListener(R.id.tv_state);
        View tvComment = helper.itemView.findViewById(R.id.tv_pingjia_m);
        if ("未评价".equals(item.commentStatus)) {
            tvComment.setEnabled(true);
        } else {
            tvComment.setEnabled(false);
        }
    }

    public String getStatus(OrderListBean.DataBean.ListBean item) {
        if ("1".equals(item.orderStatus)) {
            return "待支付";
        } else if ("5".equals(item.orderStatus)) {
            return "待到店";
        } else if ("6".equals(item.orderStatus)) {
            return "待评价";
        }
        return "可删除";
    }
}
