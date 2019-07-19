package com.wmtc.wmt.personal.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.event.ShareAllEvent;
import com.wmtc.wmt.forum.event.ShareOneEvent;
import com.wmtc.wmt.personal.event.ShareCodeEvent;

import org.greenrobot.eventbus.EventBus;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.ui.diolog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShareDialog extends BaseCustomDialog {

    private LinearLayout mTvOneCode;

    public ShareDialog(Context context) {
        super(context);
    }

    private String url;

    public ShareDialog setUrl(String url) {
        this.url = url;
        return this;
    }

    public ShareDialog setCode() {
        mTvOneCode.setVisibility(View.VISIBLE);
        mTvOneCode.setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareCodeEvent(url));
            dismiss();
        });
        return this;
    }

    @Override
    protected void initView(View view) {
        mTvOneCode = view.findViewById(R.id.tvOneCode);
        view.findViewById(R.id.tvOneShare).setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareOneEvent(url));
            this.dismiss();
        });
        view.findViewById(R.id.tvAllShare).setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareAllEvent(url));
            this.dismiss();
        });
        view.findViewById(R.id.tvClose).setOnClickListener(v -> this.dismiss());
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_share_award;
    }
}
