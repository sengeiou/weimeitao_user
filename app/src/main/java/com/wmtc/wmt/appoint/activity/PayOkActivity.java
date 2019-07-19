package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.event.PayOkEvent;
import com.wmtc.wmt.forum.activity.ForumHejiSendActivity;
import com.wmtc.wmt.forum.activity.ForumXindeSendActivity;
import com.wmtc.wmt.forum.event.SendRijiEvent;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

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
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PayOkActivity extends SuperBaseActivity {
    @BindView(R.id.ivDel)
    ImageView mIvDel;
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvOrder)
    TextView mTvOrder;
    @BindView(R.id.tvBack)
    TextView mTvBack;
    @BindView(R.id.tvServer)
    TextView tvServer;
    @BindView(R.id.tvRiji)
    TextView tvRiji;
    private Unbinder mBind;
    private String mType;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.llToolBar)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected int initRootLayout() {
        return R.layout.activity_pay_ok;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        mBind = ButterKnife.bind(this);
        mTvOrder.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", mBundle.getString("id"));
            ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
            EventBus.getDefault().post(new PayOkEvent(mBundle.getString("id")));
            finish();
        });
        mTvPrice.setText(mBundle.getString("price"));
        mTvBack.setOnClickListener(v -> {
            EventBus.getDefault().post(new PayOkEvent(mBundle.getString("id")));
            finish();
        });
        mIvDel.setOnClickListener(v -> {
            EventBus.getDefault().post(new PayOkEvent(mBundle.getString("id")));
            finish();
        });
        tvServer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", "admin");
            ActivityUtils.init().start(mActivity, CustomChatActivity.class, "唯美淘在线客服", bundle);
        });
        if (mBundle.getBoolean("payEnd")) {
            tvRiji.setVisibility(View.VISIBLE);
        }else {
            tvRiji.setVisibility(View.GONE);
        }
        LogUtil.e(mBundle.getBoolean("payEnd"));
        tvRiji.setOnClickListener(v -> {
            mType = "riji";
            orderId = mBundle.getString("id");
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (mFileArrayList == null) {
                mFileArrayList = new ArrayList<>();
            }
            mFileArrayList.clear();
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {

                Observable.fromIterable(strings).subscribe(s -> {
                    File file = BitmapUtil.compressImage(new File(s), mType + UUID.randomUUID());
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
                            delayOpenSendActivity(mType);
                        });
            });
        }
    }

    public ArrayList<File> mFileArrayList;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    public String orderId;

    private void delayOpenSendActivity(String type) {
        if (orderId != null) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("orderId", orderId);
            bundle.putSerializable("file", mFileArrayList);
            bundle.putSerializable("banner", mFileArrayList.get(0));
            if ("heji".equals(type) || "riji".equals(type)) {
                ActivityUtils.init().start(mActivity, ForumHejiSendActivity.class, "", bundle);
            } else {
                ActivityUtils.init().start(mActivity, ForumXindeSendActivity.class, "", bundle);
            }

        }
    }

    @Subscribe
    public void onEvent(SendRijiEvent event) {
      finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
