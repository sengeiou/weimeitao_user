package com.wmtc.wmt.forum.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.forum.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.DataBean.ListBean, BaseViewHolder> {
    public OrderListAdapter(List<OrderListBean.DataBean.ListBean> data) {
        super(R.layout.adapter_order_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean.ListBean item) {
        ImageView iv = helper.itemView.findViewById(R.id.ivOrderPic);
        Glide.with(mContext)
                .load(WmtApplication.url_host + item.proPic)
                .apply(GlideUtils.init().roundTransFrom(mContext, 5, R.drawable.wmt_default))
                .into(iv);

        int couponPrice = item.couponPrice;
        int hbPrice = 0;
        try {
            if (StringUtils.isNotBlank(item.hongbaoPrice)) {
                hbPrice = Integer.parseInt(item.hongbaoPrice);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int firstPrice = Integer.parseInt(item.projectPriceFirst);
        int endPrice = Integer.parseInt(item.projectPriceEnd);
        int fixPrice = endPrice - (hbPrice + couponPrice);
        if (fixPrice < 1) {
            fixPrice = 1;
        }

        helper.setText(R.id.tvOrderName, StringUtils.init().fixNullStr(item.projectName))
                .setText(R.id.tvOrderShop, StringUtils.init().fixNullStr(item.shopName))
                .setText(R.id.tvOrderAppointTime, "到店时间:" + StringUtils.init().fixNullStr(item.arrivalTime))
                .setText(R.id.tvPayFirst, StringUtils.init().fixNullStrMoney(item.projectPriceFirst, "支付预约金:￥"))
                .setText(R.id.tvPayEnd, StringUtils.init().fixNullStrMoney(fixPrice + "", "到店再付:￥"))
                .setText(R.id.tvOrderStatus, WmtUtil.getStatus(item.orderStatus))
                .setText(R.id.tvOrderPrice, StringUtils.init().fixNullStrMoney(firstPrice + endPrice + "", "￥"))
                .setText(R.id.tvWhyClose, item.orderStatusName)
                .setText(R.id.tvOrderTime, item.createTime)
                .setText(R.id.tvOrderRefundPrice, "4".equals(item.orderStatus) ?
                        "退款金额：" + StringUtils.init().fixNullStrMoney(firstPrice + "", "￥") :
                        "退款金额：" + StringUtils.init().fixNullStrMoney(firstPrice + fixPrice + "", "￥"))
                .setVisible(R.id.tvWhyClose, !"1,3,5,6".contains(item.orderStatus))
                .setVisible(R.id.ivDelOrder, !"1,3,5".contains(item.orderStatus))
                .setVisible(R.id.tvCancel, "1,3,5".contains(item.orderStatus))
                .setVisible(R.id.tvOrderRefundPrice, "4,9,13".contains(item.orderStatus))
                .setVisible(R.id.tvCommit, "6".contains(item.orderStatus))
                .setVisible(R.id.tvCreateNew, !"1,3,5".contains(item.orderStatus))
                .setVisible(R.id.tvRiji, "6".contains(item.orderStatus))
                .setVisible(R.id.tvWaitShop, "3".contains(item.orderStatus))
                .setVisible(R.id.tvPayFirst, "1".contains(item.orderStatus))
                .setVisible(R.id.tvPayEnd, "5".contains(item.orderStatus))
                .addOnClickListener(R.id.ivDelOrder)
                .addOnClickListener(R.id.tvCancel)
                .addOnClickListener(R.id.tvPayFirst)
                .addOnClickListener(R.id.tvPayEnd)
                .addOnClickListener(R.id.tvRiji)
                .addOnClickListener(R.id.tvCommit)
                .addOnClickListener(R.id.tvCreateNew);
        TextView tvComment = helper.itemView.findViewById(R.id.tvCommit);
        if ("未评价".equals(item.commentStatus)) {
            tvComment.setEnabled(true);
        } else {
            tvComment.setEnabled(false);
            tvComment.setText("已评价");
        }
    }
}
