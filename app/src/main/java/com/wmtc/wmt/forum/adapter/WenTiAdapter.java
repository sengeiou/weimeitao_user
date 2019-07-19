package com.wmtc.wmt.forum.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;

import java.util.List;

public class WenTiAdapter extends BaseQuickAdapter<EfficacyDetailsBean.DataBean, BaseViewHolder> {

    public WenTiAdapter(List<EfficacyDetailsBean.DataBean> data) {
        super(R.layout.item_wenti, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EfficacyDetailsBean.DataBean item) {
        helper.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_text, Html.fromHtml(item.subtitleList.get(0).content, null, null));

    }
}
