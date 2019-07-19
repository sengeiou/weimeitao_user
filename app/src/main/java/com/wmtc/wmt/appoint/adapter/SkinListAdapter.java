package com.wmtc.wmt.appoint.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ProInfoBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Obl on 2019/5/4.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinListAdapter extends TagAdapter<ProjectInfoBean.DataBean.SkinSBean> {
    public SkinListAdapter(List<ProjectInfoBean.DataBean.SkinSBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, ProjectInfoBean.DataBean.SkinSBean s) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_skin_list,
                parent, false);
        textView.setText(s.dictHufuxuqiuName);
        return textView;
    }
}
