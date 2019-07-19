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
public class EditCommentDialog extends BaseCustomDialog {

    private EditText mEdDialogInput;
    private ImageView mDialogCommentAvatar;
    private int position = -1;

    public EditCommentDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mEdDialogInput = view.findViewById(R.id.edDialogInput);
        mEdDialogInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (mFocusListener != null) {
                    mFocusListener.hasFocus(position, mEdDialogInput);
                }
            }
        });
        mDialogCommentAvatar = view.findViewById(R.id.ivDialogCommentAvatar);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_edit_comment;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    public EditCommentDialog setViewData(String avatar, int position) {
        Glide.with(getContext())
                .load(avatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(mDialogCommentAvatar);
        this.position = position;
        return this;
    }

    public EditCommentDialog setFocusListener(FollowListAdapter.FocusListener mFocusListener) {
        this.mFocusListener = mFocusListener;
        return this;
    }

    public FollowListAdapter.FocusListener mFocusListener;

    public interface FocusListener {
        void hasFocus(int position, View view);
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
