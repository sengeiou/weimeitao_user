package com.wmtc.wmt.forum.adapter;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NearByAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public NearByAdapter(List<PoiItem> data) {
        super(R.layout.adapter_near_by, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tvName, String.format(Locale.CHINA, "%s,%s", item.getTitle(), item.getSnippet()));
    }
}
