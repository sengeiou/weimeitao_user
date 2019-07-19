package com.wmtc.wmt.forum.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.bean.FollowListBean;

import java.util.List;
import java.util.Locale;

import cn.bingoogolapple.bgabanner.BGABanner;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.SpannedUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/6.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FollowListAdapter extends BaseQuickAdapter<FollowListBean.DataBean.RecordsBean, BaseViewHolder> {

    public FollowListAdapter(List<FollowListBean.DataBean.RecordsBean> data) {
        super(R.layout.adapter_follow_list, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, FollowListBean.DataBean.RecordsBean item) {
        BGABanner banner = helper.itemView.findViewById(R.id.banner);

        banner.setAdapter((BGABanner.Adapter<ImageView, String>) (banner1, itemView, model, position) -> {
            String videoUrl = model;
            if ("4".equals(item.forumType)) {
                videoUrl = model + "?x-oss-process=video/snapshot,t_0,f_jpg,w_600,h_800";
            }
            Glide.with(mContext).load(WmtApplication.url_host + videoUrl)
                    .apply(GlideUtils.init().options(R.mipmap.wmt_default))
                    .into(itemView);
        });
        banner.setData(item.attachmentList, null);

        ImageView ivUserAvatar = helper.itemView.findViewById(R.id.ivUserAvatar);
        Glide.with(mContext).load(WmtApplication.url_host + item.createUserAvatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(ivUserAvatar);

        ImageView ivCommentAvatar = helper.itemView.findViewById(R.id.ivCommentAvatar);
        Glide.with(mContext).load(WmtApplication.avatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(ivCommentAvatar);

        String time = StringUtils.init().fixNullStr(item.createTime);
        if (StringUtils.isNotBlank(item.createTime) && item.createTime.length() > 9) {
            time = time.substring(5, time.indexOf(" "));
        }


        List<FollowListBean.DataBean.RecordsBean.CommentarylistBean> commentarylist = item.commentarylist;
        if (commentarylist != null && commentarylist.size() > 0) {
            String key1 = StringUtils.init().fixNullStr(commentarylist.get(0).name);
            String value1 = StringUtils.init().fixNullStr(commentarylist.get(0).judgeContent);
            String str1 = key1 + ":" + value1;
            helper.setText(R.id.tvComment1, SpannedUtil.getForegroundColorSpan(str1, 0, key1.length() + 1,
                    mContext.getResources().getColor(R.color.color666)))
                    .setVisible(R.id.tvComment2, false)
                    .setVisible(R.id.tvCommentMore, false);
            if (commentarylist.size() > 1) {
                String key2 = StringUtils.init().fixNullStr(commentarylist.get(1).name);
                String value2 = StringUtils.init().fixNullStr(commentarylist.get(1).judgeContent);
                String str2 = key2 + ":" + value2;
                helper.setText(R.id.tvComment2, SpannedUtil.getForegroundColorSpan(str2, 0, key2.length() + 1,
                        mContext.getResources().getColor(R.color.color666)))
                        .setVisible(R.id.tvComment2, true)
                        .setVisible(R.id.tvCommentMore, true)
                        .setText(R.id.tvCommentMore, String.format(Locale.CHINA, "查看全部%s条评论", commentarylist.size()));
            }
            helper.setVisible(R.id.llComment, true);
        } else {
            helper.setVisible(R.id.llComment, false);
        }

        View ivInputZan = helper.itemView.findViewById(R.id.ivInputZan);
        ivInputZan.setSelected("1".equals(item.zanStatus));

        View ivInputSave = helper.itemView.findViewById(R.id.ivInputSave);
        ivInputSave.setSelected("1".equals(item.shoucangStatus));

        TextView tvUserFollow = helper.itemView.findViewById(R.id.tvUserFollow);
        tvUserFollow.setSelected(item.isFollow);
        if (item.isFollow) {
            tvUserFollow.setText("已关注");
        } else {
            tvUserFollow.setText("关注");
        }

        helper.setText(R.id.tvUserName, StringUtils.init().fixNullStr(item.createUser))
                .setText(R.id.tvContent, StringUtils.init().fixNullStr(item.content))
                .setText(R.id.tvSendDay, time)
                .setText(R.id.tvInputZan, StringUtils.init().fixNullStr(item.dianzanNum))
                .setText(R.id.tvInputSave, StringUtils.init().fixNullStr(item.shoucangNum))
                .addOnClickListener(R.id.tvUserFollow)
                .addOnClickListener(R.id.llInputZan)
                .addOnClickListener(R.id.edInput)
                .addOnClickListener(R.id.llInputSave);
        EditText edInput = helper.itemView.findViewById(R.id.edInput);
        edInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                if (mFocusListener != null) {
                    mFocusListener.hasFocus(helper.getAdapterPosition(), view);
                }
            }
        });
    }


    public void setFocusListener(FocusListener mFocusListener) {
        this.mFocusListener = mFocusListener;
    }

    public FocusListener mFocusListener;

    public interface FocusListener {
        void hasFocus(int position, View view);
    }
}
