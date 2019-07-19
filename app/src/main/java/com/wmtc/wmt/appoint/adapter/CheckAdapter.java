package com.wmtc.wmt.appoint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CheckAdapter extends BaseQuickAdapter<DictListBean.DataBean, BaseViewHolder> {


    public CheckAdapter(List<DictListBean.DataBean> data) {
        super(R.layout.adapter_skin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictListBean.DataBean item) {
        helper.setText(R.id.tvHot, item.name);
        TextView tvHot = helper.itemView.findViewById(R.id.tvHot);
        tvHot.setSelected(item.isSel);
    }
}
