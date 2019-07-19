package com.wmtc.wmt.forum.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.MarketBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/23.
 * com.wmtc.wmt.mvui
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TabAdapter extends BaseQuickAdapter<MarketBean.DataBean.TbkItemInfoGetResponseBean.ResultsBean.NTbkItemBean, BaseViewHolder> {
    public TabAdapter(List<MarketBean.DataBean.TbkItemInfoGetResponseBean.ResultsBean.NTbkItemBean> data) {
        super(R.layout.market_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketBean.DataBean.TbkItemInfoGetResponseBean.ResultsBean.NTbkItemBean item) {
        ImageView iv = helper.itemView.findViewById(R.id.im_pic);
        Glide.with(mContext).load(item.getPict_url()).apply(GlideUtils.init().options(R.mipmap.wmt_default)).into(iv);
        helper.setText(R.id.tv_name, StringUtils.init().fixNullStr(item.getTitle()))
                .setText(R.id.tv_price, String.format(Locale.CHINA, "￥%s", item.getZk_final_price()))
                .addOnClickListener(R.id.tbk_item);
    }
}
