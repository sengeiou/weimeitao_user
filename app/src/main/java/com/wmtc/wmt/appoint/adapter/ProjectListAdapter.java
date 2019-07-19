package com.wmtc.wmt.appoint.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectListAdapter extends BaseQuickAdapter<ShopInfoBean.DataBean.ShopsBean.ProsBean, BaseViewHolder> {

    public ProjectListAdapter(List<ShopInfoBean.DataBean.ShopsBean.ProsBean> data) {
        super(R.layout.adapter_project_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfoBean.DataBean.ShopsBean.ProsBean item) {
        ImageView ivPic = helper.itemView.findViewById(R.id.ivProPic);
        List<String> banner = item.banner;
        if (banner != null && banner.size() > 0) {
            Glide.with(mContext)
                    .load(banner.get(0))
                    .apply(GlideUtils.init().options(R.drawable.wmt_default))
                    .into(ivPic);
        }
        helper.setText(R.id.tvProName, StringUtils.init().fixNullStr(item.pTitle))
                .setText(R.id.tvProPrice, StringUtils.init().fixNullStrMoney(item.pPrice,"￥"))
                .setText(R.id.tvProEffect, StringUtils.init().fixNullStr(item.pEffect));
    }
}
