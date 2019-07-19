package com.wmtc.wmt.appoint.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkPicAdapter extends BaseQuickAdapter<ProBkInfoBean.DataBean.PicsBean, BaseViewHolder> {


    public ProBkPicAdapter(List<ProBkInfoBean.DataBean.PicsBean> data) {
        super(R.layout.adapter_pro_bk_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProBkInfoBean.DataBean.PicsBean item) {
        ImageView ivPic = helper.itemView.findViewById(R.id.ivPic);
        helper.setVisible(R.id.ivType, "video".equals(item.type));
        Glide.with(ivPic)
                .load(item.url)
                .apply(GlideUtils.init().roundTransFrom(mContext, 5, R.drawable.wmt_default))
                .into(ivPic);
    }
}
