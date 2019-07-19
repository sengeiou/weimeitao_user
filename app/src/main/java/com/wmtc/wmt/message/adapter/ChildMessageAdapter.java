package com.wmtc.wmt.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.fragment.ChildMessageFragment;

import java.util.List;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChildMessageAdapter extends BaseQuickAdapter<MessageBean.DataBean, BaseViewHolder> {
    private ChildMessageFragment fragment;

    public ChildMessageAdapter(List<MessageBean.DataBean> data, ChildMessageFragment fragment) {
        super(R.layout.adapter_child_message, data);
        this.fragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.DataBean item) {
        if (item.createTime != null && item.createTime.length() > 10) {
            try {
                String time = item.createTime.substring(5, item.createTime.indexOf(" "));
                helper.setText(R.id.tvTime, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        helper.setText(R.id.tvTitle, item.messageTitle)
                .setImageResource(R.id.ivMesAvatar, "notice".equals(fragment.type) ? R.drawable.message : R.mipmap.logo)
                .setText(R.id.tvSubTitle, item.messageContent)
                .addOnClickListener(R.id.right)
                .addOnClickListener(R.id.content);
    }
}
