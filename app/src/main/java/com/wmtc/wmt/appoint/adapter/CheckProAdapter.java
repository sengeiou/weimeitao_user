package com.wmtc.wmt.appoint.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ShopProjectNameBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CheckProAdapter extends BaseQuickAdapter<ShopProjectNameBean.DataBean, BaseViewHolder> {


    public CheckProAdapter(List<ShopProjectNameBean.DataBean> data) {
        super(R.layout.adapter_skin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopProjectNameBean.DataBean item) {
        helper.setText(R.id.tvHot, item.dictProjectName);
        TextView tvHot = helper.itemView.findViewById(R.id.tvHot);
        tvHot.setSelected(item.isSel);
    }
}
