package com.wmtc.chatmodule.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.MessageAdapter;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class MessageBaseHolder<T extends MessageBaseInfo> extends BaseViewHolder {
    public @LayoutRes
    int layout;
    public Context mContext;
    public MessageAdapter mAdapter;


    public MessageBaseHolder(View view, MessageAdapter adapter) {
        super(view);
        this.mAdapter = adapter;
        this.mContext = view.getContext();
    }

    public void bindData(T msg, int position) {
        ImageView ivAvatarLeft = itemView.findViewById(R.id.ivAvatarLeft);
        ImageView ivAvatarRight = itemView.findViewById(R.id.ivAvatarRight);
        if (ivAvatarLeft != null) {
            Glide.with(mContext)
                    .load(msg.fromAvatar)
                    .apply(GlideUtils.init().roundTransFrom(mContext, 45, R.drawable.a1))
                    .into(ivAvatarLeft);
        }
        if (ivAvatarRight != null) {
            Glide.with(mContext)
                    .load(msg.fromAvatar)
                    .apply(GlideUtils.init().roundTransFrom(mContext, 45, R.drawable.a2))
                    .into(ivAvatarRight);
        }

        setText(R.id.tvCreateTime, msg.createTime)
                .setVisible(R.id.llLeft, msg.isLeft())
                .setVisible(R.id.tvCreateTime, StringUtils.isNotBlank(msg.createTime))
                .setVisible(R.id.llRight, !msg.isLeft());
    }
}
