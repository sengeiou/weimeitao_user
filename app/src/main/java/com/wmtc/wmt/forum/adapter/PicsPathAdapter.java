package com.wmtc.wmt.forum.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PicsPathAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PicsPathAdapter(List<String> data) {
        super(R.layout.adapter_pics, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivItemSrc = helper.itemView.findViewById(R.id.ivItemSrc);
        helper.addOnClickListener(R.id.ivDel);
        Glide.with(mContext).load(item).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(ivItemSrc);
    }
}