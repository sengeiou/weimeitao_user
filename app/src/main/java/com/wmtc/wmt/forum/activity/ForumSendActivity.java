package com.wmtc.wmt.forum.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaiky.imagespickers.ImageSelectorActivity;

import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.event.FinishSendStartEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhaoss.weixinrecorded.activity.RecordedActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumSendActivity extends SuperBaseActivity {
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ivXinde)
    ImageView mIvXinde;
    @BindView(R.id.ivHeji)
    ImageView mIvHeji;
    @BindView(R.id.ivVideo)
    ImageView ivVideo;
    @BindView(R.id.ivDel)
    ImageView mIvDel;
    private Unbinder mBind;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private ArrayList<File> mFileArrayList;
    private String type;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_send;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this, view);
        mIvDel.setOnClickListener(v -> finish());
        mFileArrayList = new ArrayList<>();
        EventBus.getDefault().register(this);
        mIvXinde.setOnClickListener(v -> {
            type = "xinde";
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCameraWithSize(this.mActivity, 9))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        mIvHeji.setOnClickListener(v -> {
            type = "heji";
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        ivVideo.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> {
                        Intent intent = new Intent(this, RecordedActivity.class);
                        startActivityForResult(intent, 998);
                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }


    private void delayOpenSendActivity(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("file", mFileArrayList);
        bundle.putSerializable("banner", mFileArrayList.get(0));
        if ("heji".equals(type)) {
            ActivityUtils.init().start(mActivity, ForumHejiSendActivity.class, "", bundle);
        } else {
            ActivityUtils.init().start(mActivity, ForumXindeSendActivity.class, "", bundle);
        }
        finish();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(requestCode);
        LogUtil.e(resultCode);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                if (mLoading == null) {
                    mLoading = new DialogLoading(this);
                }
                mLoading.show();
                isZiped = false;
                List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                    Observable.fromIterable(strings).subscribe(s -> {
                        File file = BitmapUtil.compressImage(new File(s), type + UUID.randomUUID());
                        mFileArrayList.add(file);
                    });
                    return mFileArrayList;
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                    if (mLoading != null && mLoading.isShowing()) {
                        mLoading.dismiss();
                    }
                    isZiped = true;
                    Observable.timer(500, TimeUnit.MILLISECONDS)
                            .compose(new IoMainSchedule<>())
                            .subscribe(aLong -> {
                                delayOpenSendActivity(type);
                            });
                });
            } else if (requestCode == 998) {
                String videoPath = data.getStringExtra(RecordedActivity.INTENT_PATH);
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", videoPath);
                LogUtil.e(videoPath+"------www-------------------");
                ActivityUtils.init().start(mActivity, ForumVideoSendActivity.class, "发布视频", bundle);
                finish();
            }

        }
    }

    @Subscribe
    public void onEvent(FinishSendStartEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
