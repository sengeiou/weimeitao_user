package com.wmtc.wmt.forum.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;

import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/8.
 * com.wmtc.wmt.ui.diolog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NotFollowDialog extends BaseCustomDialog {

    private TextView mTvSubTitle;
    private TextView mTvTitle;

    public NotFollowDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> dismiss());
        mTvSubTitle = view.findViewById(R.id.tvSubTitle);
        mTvTitle = view.findViewById(R.id.tvTitle);
    }

    public NotFollowDialog setTitle(String title, int color) {
        mTvTitle.setText(title);
        if (color != -1) {
            mTvTitle.setTextColor(color);
        }
        return this;
    }

    public NotFollowDialog setSubTitle(String title, int color) {
        mTvSubTitle.setText(title);
        if (color != -1) {
            mTvSubTitle.setTextColor(color);
        }
        return this;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_not_follow;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(8);
    }

    @Override
    public int setHeight() {
        return ScreenUtils.dp2px(110);
    }
}
