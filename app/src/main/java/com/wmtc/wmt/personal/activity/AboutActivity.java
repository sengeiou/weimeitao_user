package com.wmtc.wmt.personal.activity;

import android.opengl.GLU;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.event.VersionCheckEvent;
import com.wmtc.wmt.personal.presenter.AboutPresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/7/16.
 * com.wmtc.wmt.personal.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AboutActivity extends CommonWmtActivity {
    @BindView(R.id.ivLogo)
    ImageView mIvLogo;
    @BindView(R.id.tvVersion)
    TextView mTvVersion;
    @BindView(R.id.tvVersionName)
    TextView mTvVersionName;
    @BindView(R.id.tvVersionTime)
    TextView mTvVersionTime;
    @BindView(R.id.tvVersionContent)
    TextView mTvVersionContent;
    private Unbinder mBind;
    private AboutPresenter mPresenter;
    private VersionBean.DataBean mData;

    @Override
    public int initAddLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mPresenter = new AboutPresenter(this);
        mPresenter.getNewVersion();
        Glide.with(mActivity).load(R.mipmap.logo)
                .apply(GlideUtils.init().roundTransFrom(mActivity, 5))
                .into(mIvLogo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    public void version(VersionBean bean) {
        mData = bean.data;
        if (mData != null) {
            mTvVersion.setText(mData.versionName);
            mTvVersionName.setText(mData.versionName);
            mTvVersionContent.setText(mData.description);
            mTvVersionTime.setText(mData.time);

//            DialogLogout dialog =
//                    new DialogLogout(mActivity).setForce("1".equals(mData.isForce)).setTitle("更新提示").setSubTitle(mData.description);
//            dialog.show(R.id.btnSure, view -> {
//                dialog.dismiss();
//                AndPermission.with(this)
//                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
//                        .onGranted(permissions -> {
//                            EventBus.getDefault().post(new VersionCheckEvent());
//                            finish();
//                        })
//                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
//                        .start();
//            });


        }

    }
}
