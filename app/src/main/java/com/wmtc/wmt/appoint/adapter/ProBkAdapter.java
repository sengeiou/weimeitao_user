package com.wmtc.wmt.appoint.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkAdapter extends TagAdapter<ProBkBean.DataBean.ListBean> {
    public ProBkAdapter(List<ProBkBean.DataBean.ListBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, ProBkBean.DataBean.ListBean bean) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dict, parent, false);
        textView.setText(bean.name);
        return textView;
    }
}
