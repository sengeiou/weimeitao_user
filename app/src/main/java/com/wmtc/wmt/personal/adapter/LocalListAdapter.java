package com.wmtc.wmt.personal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.personal.bean.LocalListBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalListAdapter extends BaseQuickAdapter<LocalListBean.DataBean, BaseViewHolder> {
    public LocalListAdapter(List<LocalListBean.DataBean> data) {
        super(R.layout.adapter_local_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalListBean.DataBean item) {
        CheckBox checkbox = helper.itemView.findViewById(R.id.checkbox);
        String local = String.format(Locale.CHINA, "%s%s%s%s", item.province, item.city, item.area, item.address);
        helper.setText(R.id.tvLocalName, StringUtils.init().fixNullStr(item.deliveryName))
                .setText(R.id.tvLocalPhone, StringUtils.init().fixNullStr(item.deliveryTel))
                .setText(R.id.tvPersonLocal, StringUtils.init().fixNullStr(local))
                .addOnClickListener(R.id.localEdit)
                .addOnClickListener(R.id.checkbox)
                .addOnClickListener(R.id.localDel);
        checkbox.setChecked(1 == (item.isDefault));
    }
}
