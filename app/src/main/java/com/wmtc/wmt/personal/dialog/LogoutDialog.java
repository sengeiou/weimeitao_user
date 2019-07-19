package com.wmtc.wmt.personal.dialog;

import android.content.Context;
import android.view.View;

import com.wmtc.wmt.R;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LogoutDialog extends BaseCustomDialog {
    public LogoutDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> dismiss());
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(8);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_logout_wm;
    }
}
