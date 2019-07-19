package com.wmtc.wmt.appoint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.base.WmtApplication;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachAdapter extends BaseQuickAdapter<TeachSelBean.DataBean, BaseViewHolder> {


    public TeachAdapter(List<TeachSelBean.DataBean> data) {
        super(R.layout.adapter_select_teacher, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeachSelBean.DataBean item) {

        ImageView ivAvatar = helper.itemView.findViewById(R.id.ivAvatar);

        Glide.with(mContext)
                .load(WmtApplication.url_host + item.avatar)
                .apply(GlideUtils.init().roundTransFrom(mContext, 5,R.drawable.wmt_default))
                .into(ivAvatar);

        helper.setText(R.id.tvName, item.technicianName)
                .addOnClickListener(R.id.flSelTeach)
                .setText(R.id.tvNotCanUse, item.notDo)
                .setVisible(R.id.tvSelTeach, StringUtils.isBlank(item.notDo))
                .setText(R.id.tvTitle, item.title)
                .setText(R.id.tvGoodAt, String.format(Locale.CHINA, "%s | 从业%s", item.goodAt, item.toDay));

        TextView tvSelTeach = helper.itemView.findViewById(R.id.tvSelTeach);
        tvSelTeach.setSelected(item.isSel);


    }
}
