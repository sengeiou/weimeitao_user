package com.wmtc.wmt.appoint.adapter;


import android.opengl.GLU;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.base.WmtApplication;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderHistoryAdapter extends BaseQuickAdapter<OrderListBean.DataBean.ListBean, BaseViewHolder> {


    public OrderHistoryAdapter(List<OrderListBean.DataBean.ListBean> data) {
        super(R.layout.adapter_order_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean.ListBean item) {
        ImageView ivPic = helper.itemView.findViewById(R.id.ivPic);
        helper.setText(R.id.tvProName, item.projectName)
                .setText(R.id.tvName, item.shopName)
                .addOnClickListener(R.id.tvComment)
                .addOnClickListener(R.id.tvNewOne)
                .setText(R.id.tvComment, "已评价".equals(item.commentStatus) ? "已评价" : "评价")
                .setText(R.id.tvPrice, StringUtils.init().fixNullStrMoney(item.projectPriceFirst, "￥"))
                .setText(R.id.tvTime, item.createTime);
        Glide.with(mContext).load(WmtApplication.url_host + item.proPic)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(ivPic);
    }
}
