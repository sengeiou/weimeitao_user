package com.wmtc.wmt.appoint.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerViewAdapter;

/**
 * Created by Obl on 2019/5/28.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkViewPagerAdapter extends BaseViewPagerViewAdapter<ProBkBean.DataBean> {
    public ProBkViewPagerAdapter(List<ProBkBean.DataBean> list) {
        super(list);
        addItemType(DEF_TYPE, R.layout.adapter_pro_bk_child);
    }

    @Override
    public void bindView(View view, ProBkBean.DataBean dataBean, int position) {
        TagFlowLayout flowLayout = view.findViewById(R.id.flowLayout);
        ProBkAdapter adapter = new ProBkAdapter(dataBean.list);
        flowLayout.setAdapter(adapter);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).name;
    }
}
