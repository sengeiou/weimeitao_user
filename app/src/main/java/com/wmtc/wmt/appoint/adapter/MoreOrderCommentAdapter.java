package com.wmtc.wmt.appoint.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
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
public class MoreOrderCommentAdapter extends BaseQuickAdapter<OrderJudgeBean.DataBean.ResultListBean, BaseViewHolder> {

    public MoreOrderCommentAdapter(List<OrderJudgeBean.DataBean.ResultListBean> data) {
        super(R.layout.adapter_forum_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderJudgeBean.DataBean.ResultListBean item) {
        ImageView ivAvatar = helper.itemView.findViewById(R.id.ivAvatar);
        Glide.with(mContext).load(WmtApplication.url_host + item.avatar).apply(GlideUtils.init().options(R.drawable.wm_avatar)).into(ivAvatar);
        helper.setText(R.id.tvName, StringUtils.init().fixNullStr(item.name))
                .setText(R.id.tvTime, StringUtils.init().fixNullStr(item.createTime))
                .setText(R.id.tvContent, StringUtils.init().fixNullStr(item.judgeContent));
    }
}
