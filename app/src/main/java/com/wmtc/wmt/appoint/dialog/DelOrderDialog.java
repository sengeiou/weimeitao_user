package com.wmtc.wmt.appoint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DelOrderDialog extends BaseCustomDialog {


    public DelOrderDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        TextView tvCancel = view.findViewById(R.id.tvClose);
        tvCancel.setOnClickListener(v -> dismiss());
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_del_order;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return top.jplayer.baseprolibrary.R.style.AnimBottom;
    }
}
