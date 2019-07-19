package com.wmtc.wmt.appoint.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/30.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinBkInfoAdapter extends BaseQuickAdapter<EfficacyDetailsBean.DataBean, BaseViewHolder> {
    public SkinBkInfoAdapter(List<EfficacyDetailsBean.DataBean> data) {
        super(R.layout.adapter_skin_info_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EfficacyDetailsBean.DataBean item) {
        List<EfficacyDetailsBean.DataBean.SubtitleListBean> subtitleList = item.subtitleList;
        if (subtitleList != null && subtitleList.size() > 0) {
            subtitleList.get(0).isSel = true;
            if (subtitleList.size() > 1) {
                RecyclerView recyclerView = helper.itemView.findViewById(R.id.recyclerViewCheck);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                CheckTextSubAdapter adapter = new CheckTextSubAdapter(subtitleList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener((adapter1, view, position) -> {
                    List<EfficacyDetailsBean.DataBean.SubtitleListBean> data = adapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            data.get(position).isSel = true;
                        } else {
                            data.get(i).isSel = false;
                        }
                        if (data.get(i).isSel) {
                            helper.setText(R.id.tvContent, subtitleList.get(i).content);
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
                helper.setVisible(R.id.viewLine, true);
            } else {
                helper.setVisible(R.id.viewLine, false);
            }
            helper.setText(R.id.tvContent, subtitleList.get(0).content);
        }
        helper.setText(R.id.tvTitle, item.title);
    }
}
