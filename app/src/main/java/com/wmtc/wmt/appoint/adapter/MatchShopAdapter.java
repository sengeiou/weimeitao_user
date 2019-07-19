package com.wmtc.wmt.appoint.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.MatchShopBean;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerViewAdapter;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchShopAdapter extends BaseViewPagerViewAdapter<MatchShopBean.DataBean.RecordsBean> {
    ClickListener mClickListener;

    public MatchShopAdapter(List<MatchShopBean.DataBean.RecordsBean> list) {
        super(list);
        addItemType(0, R.layout.adapter_match_shop);
    }

    @Override
    public void bindView(View view, MatchShopBean.DataBean.RecordsBean item, int position) {
        TextView tvLocal = view.findViewById(R.id.tvLocal);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvDetail = view.findViewById(R.id.tvDetail);
        TextView tvPos = view.findViewById(R.id.tvPos);
        tvTitle.setText(item.shopName);
        tvLocal.setText(item.shopAddress);
        String pos = position + 1 + "/3";
        tvPos.setText(pos);
        view.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(item, position);
            }
        });
    }

    public void setClickListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ClickListener {
        void onItemClick(MatchShopBean.DataBean.RecordsBean item, int position);
    }
}
