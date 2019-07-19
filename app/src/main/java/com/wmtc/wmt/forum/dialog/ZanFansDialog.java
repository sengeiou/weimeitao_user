package com.wmtc.wmt.forum.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/8.
 * com.wmtc.wmt.ui.diolog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ZanFansDialog extends BaseCustomDialog {

    private TextView mTvContent;
    private TextView mTvName;

    public ZanFansDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mTvContent = view.findViewById(R.id.tvContent);
        mTvName = view.findViewById(R.id.tvName);
    }

    public ZanFansDialog setContent(String name, String content) {
        mTvName.setText(name);
        mTvContent.setText(content);
        return this;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_zan_fans;
    }


    @Override
    public int setWidth(int i) {
        return super.setWidth(7);
    }
}
