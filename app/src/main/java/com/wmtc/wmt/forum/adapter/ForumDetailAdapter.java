package com.wmtc.wmt.forum.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.bean.ForumCommentBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumDetailAdapter extends BaseQuickAdapter<ForumCommentBean.DataBean, BaseViewHolder> {

    public ForumDetailAdapter(List<ForumCommentBean.DataBean> data) {
        super(R.layout.adapter_forum_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ForumCommentBean.DataBean item) {
        ImageView ivAvatar = helper.itemView.findViewById(R.id.ivAvatar);
        ImageView ivZan = helper.itemView.findViewById(R.id.ivZan);
        Glide.with(mContext).load(WmtApplication.url_host + item.avatar).apply(GlideUtils.init().options(R.drawable.wm_avatar)).into(ivAvatar);
        helper.setText(R.id.tvName, StringUtils.init().fixNullStr(item.name))
                .setText(R.id.tvTime, StringUtils.init().fixNullStr(item.createTime))
                .setText(R.id.tvContent, StringUtils.init().fixNullStr(item.judgeContent))
                .setText(R.id.tvNum, StringUtils.init().fixNullStr(item.dianzanNum));
    }
}
