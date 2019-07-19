package com.wmtc.wmt.forum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Obl on 2019/5/4.
 * com.wmtc.wmt.mvui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class HotForumAdapter extends TagAdapter<String> {
    public HotForumAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hot_forum, parent,
                false);
        String text = "#" + s;
        textView.setText(text);
        return textView;
    }
}
