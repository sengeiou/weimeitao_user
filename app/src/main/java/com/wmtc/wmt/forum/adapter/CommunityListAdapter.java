package com.wmtc.wmt.forum.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.bean.ForumListBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/28.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CommunityListAdapter extends BaseQuickAdapter<ForumListBean.DataBean.RecordsBean, BaseViewHolder> {
    public CommunityListAdapter(List<ForumListBean.DataBean.RecordsBean> data) {
        super(R.layout.adapter_community_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ForumListBean.DataBean.RecordsBean item) {
        ImageView ivSrc = helper.itemView.findViewById(R.id.ivSrc);
        ImageView ivAvatar = helper.itemView.findViewById(R.id.ivAvatar);
        ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
        ViewGroup.LayoutParams ivSrcLayoutParams = ivSrc.getLayoutParams();
        String tvType = "心得";
        String picture = item.picture;
        if ("1".equals(item.forumType)) {//心得
            ivSrcLayoutParams.height = ScreenUtils.dp2px(210);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if ("2".equals(item.forumType)) {//合集
            tvType = "合集";
            ivSrcLayoutParams.height = ScreenUtils.dp2px(170);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if ("3".equals(item.forumType)) {//日记
            tvType = "日记";
            ivSrcLayoutParams.height = ScreenUtils.dp2px(170);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if ("4".equals(item.forumType)) {//视频
            tvType = "视频";
            ivSrcLayoutParams.height = ScreenUtils.dp2px(170);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            picture += "?x-oss-process=video/snapshot,t_0,f_jpg,w_600,h_800";
        }

        Glide.with(mContext).load(WmtApplication.url_host + picture)
                .apply(GlideUtils.init().options(R.drawable.wmt_default)).into(ivSrc);

        Glide.with(mContext).load(WmtApplication.url_host + item.createUserAvatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar)).into(ivAvatar);

        View zanView = helper.itemView.findViewById(R.id.ivZan);
        zanView.setSelected(!"-1".equals(item.zanStatus));
        helper.setText(R.id.tvName, StringUtils.init().fixNullStr(item.createUser))
                .setText(R.id.tvZan, StringUtils.init().fixNullStr(item.dianzanNum))
                .setText(R.id.tvType, tvType)
                .setText(R.id.tvTitle, StringUtils.init().fixNullStr(item.content))
                .addOnClickListener(R.id.llZan);
        LogUtil.e(item);
        LogUtil.e("================");
    }
}
