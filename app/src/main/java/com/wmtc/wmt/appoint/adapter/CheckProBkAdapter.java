package com.wmtc.wmt.appoint.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CheckProBkAdapter extends BaseQuickAdapter<ProBkBean.DataBean.ListBean, BaseViewHolder> {


    public CheckProBkAdapter(List<ProBkBean.DataBean.ListBean> data) {
        super(R.layout.adapter_skin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProBkBean.DataBean.ListBean item) {
        helper.setText(R.id.tvHot, item.name);
        TextView tvHot = helper.itemView.findViewById(R.id.tvHot);
        tvHot.setSelected(item.isSel);
    }
}
