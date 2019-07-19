package com.wmtc.wmt.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.fragment.ChildChatFragment;
import com.wmtc.wmt.message.fragment.ChildMessageFragment;

import java.util.List;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChildChatAdapter extends BaseQuickAdapter<MessageBean.DataBean, BaseViewHolder> {

    public ChildChatAdapter(List<MessageBean.DataBean> data) {
        super(R.layout.adapter_child_chat, data);
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
                .setText(R.id.tvSubTitle, item.messageContent);
    }
}
