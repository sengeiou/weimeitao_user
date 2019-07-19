package com.wmtc.wmt.appoint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ProInfoBean;
import com.wmtc.wmt.base.WmtApplication;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/5/22.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ProPicAdapter(List<String> data) {
        super(R.layout.adapter_pro_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPic = helper.itemView.findViewById(R.id.ivProPic);
        Glide.with(mContext)
                .load(item)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(ivPic);
    }
}
