package com.wmtc.wmt.forum.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.bean.ShopFollowBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopFollowAdapter extends BaseQuickAdapter<ShopFollowBean.DataBean, BaseViewHolder> {


    public ShopFollowAdapter(List<ShopFollowBean.DataBean> data) {
        super(R.layout.adapter_search_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopFollowBean.DataBean item) {
        ImageView ivUserAvatar = helper.itemView.findViewById(R.id.ivUserAvatar);
        List<String> urlList = item.urlList;
        String url = "";
        if (urlList != null && urlList.size() > 0) {
            url = urlList.get(0);
        }
        Glide.with(mContext).load(WmtApplication.url_host + url).apply(GlideUtils.init().options(R.drawable.wm_avatar)).into(ivUserAvatar);
        helper.setText(R.id.tvUserName, StringUtils.init().fixNullStr(item.shopName))
                .setText(R.id.tvUserSkin, StringUtils.init().fixNullStr(item.shopAddress))
                .setVisible(R.id.tvUserFollow, false);
    }
}
