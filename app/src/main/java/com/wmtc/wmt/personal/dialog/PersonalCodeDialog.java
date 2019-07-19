package com.wmtc.wmt.personal.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.SizeUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/30.
 * com.wmtc.wmt.personal.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PersonalCodeDialog extends BaseCustomDialog {
    private Bitmap mBitmapUser;
    private TextView mTvTitle;
    private ImageView mIvQCode;

    public PersonalCodeDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
        mIvQCode = view.findViewById(R.id.ivQCode);
        mTvTitle = view.findViewById(R.id.tvTitle);
        Glide.with(view.getContext())
                .load(WmtApplication.avatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(ivAvatar);
        view.setOnClickListener(v -> dismiss());
    }

    public PersonalCodeDialog setName(String name) {
        mTvTitle.setText(name);
        return this;
    }

    @SuppressLint("CheckResult")
    public PersonalCodeDialog setUrl(String url) {
        int dp2px = SizeUtils.dp2px(200);
        mBitmapUser = null;
        Observable.just(url).subscribeOn(Schedulers.io()).map(s ->
                CodeUtils.createImage(s, dp2px, dp2px,
                        BitmapUtil.drawableToBitmap(getContext().getResources().getDrawable(R.mipmap.logo))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    mBitmapUser = bitmap;
                    mIvQCode.setImageBitmap(mBitmapUser);
                });
        return this;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(9);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_personal_code;
    }
}
