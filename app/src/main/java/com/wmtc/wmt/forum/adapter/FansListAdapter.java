package com.wmtc.wmt.forum.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.bean.FansListBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/5.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FansListAdapter extends BaseQuickAdapter<FansListBean.DataBean, BaseViewHolder> {


    public FansListAdapter(List<FansListBean.DataBean> data) {
        super(R.layout.adapter_search_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FansListBean.DataBean item) {
        ImageView ivUserAvatar = helper.itemView.findViewById(R.id.ivUserAvatar);
        Glide.with(mContext).load(WmtApplication.url_host + StringUtils.init().fixNullStr(item.avatar)).apply(GlideUtils.init().options(R.drawable.wm_avatar)).into(ivUserAvatar);
        helper.setText(R.id.tvUserName, StringUtils.init().fixNullStr(item.name))
                .addOnClickListener(R.id.tvUserFollow);
        TextView tvUserFollow = helper.itemView.findViewById(R.id.tvUserFollow);
        boolean isFollow = "1".equals(item.attntionStatus);
        tvUserFollow.setSelected(isFollow);
        if (isFollow) {
            tvUserFollow.setText("已关注");
        } else {
            tvUserFollow.setText("回粉");
        }
        if (StringUtils.isNotBlank(item.birthday) && item.birthday.length() > 3) {
            helper.setText(R.id.tvUserTime, String.format(Locale.CHINA, "%s0后", item.birthday.substring(2, 3)));
        }
        String skin = StringUtils.init().fixNullStr(item.fuzhi);
        if (StringUtils.isNotBlank(skin)) {
            helper.setText(R.id.tvUserSkin, String.format(Locale.CHINA, "·%s", skin));
        }

    }
}
