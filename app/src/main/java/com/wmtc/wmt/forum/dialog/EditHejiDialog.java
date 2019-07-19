package com.wmtc.wmt.forum.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.adapter.FollowListAdapter;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/7.
 * com.wmtc.wmt.ui.diolog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class EditHejiDialog extends BaseCustomDialog {

    public EditHejiDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.dialog_edit_heji;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public float setAlpha() {
        return 0;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    public int setSoftInputState() {
        return WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    }
}
