package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.dialog.ShopCouponDialog;
import com.wmtc.wmt.appoint.event.QCodeEvent;
import com.wmtc.wmt.appoint.presenter.QCodePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.QRCodeDecoderUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2018/3/27.
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ActivityCustomCapture extends CommonToolBarActivity {

    private QCodePresenter mPresenter;
    private boolean isShortcuts = false;

    @Override
    public int initAddLayout() {
        return R.layout.activity_custom_camera;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        toolRightVisible(mTvToolRight, "相册");
        Observable.timer(100, TimeUnit.MILLISECONDS).subscribe(aLong -> refresh());
        mPresenter = new QCodePresenter(this);

        if (getIntent().getData() != null && TextUtils.equals(getIntent().getAction(), Intent.ACTION_VIEW)) {
            Uri uri = getIntent().getData();
            List<String> pathSegments = uri.getPathSegments();
            LogUtil.e(pathSegments);
            if (pathSegments.contains("qcode")) {
                isShortcuts = true;
            }
        }

    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public int toolMarginTop() {
        return 0;
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @SuppressLint("CheckResult")
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            StringUtils.init().copyString(mActivity, result);
            refresh();
            getResult(result);
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtils.init().showErrorToast(mActivity, "解析二维码失败");
            refresh();
        }
    };

    private void getResult(String result) {
        LogUtil.e(result);
        //Wmt专属码
        if (result != null && result.contains(top.jplayer.baseprolibrary.BuildConfig.HOST)) {
            if (isShortcuts) {
                getCodeResult(result);
            } else {
                EventBus.getDefault().post(new QCodeEvent(result));
                finish();
            }
        } else {
            ToastUtils.init().showQuickToast("请扫描唯美淘专属码");
        }
    }

    private void getCodeResult(String result) {
        if (result.contains("index/shopw")) {//收款码
            LogUtil.e("------收款码------");
            Bundle bundle = new Bundle();
            getUid(result);
            bundle.putString("id", uid);
            ActivityUtils.init().start(mActivity, OffLineActivity.class, "付款", bundle);
            finish();
        } else {//红包码
            getUid(result);
            mPresenter.getShopCoupon(uid);
        }
    }


    private void refresh() {
        try {
            CaptureFragment captureFragment = new CaptureFragment();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        } catch (Exception e) {
            finish();
        }
    }


    private DialogLoading mDialogLoading;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    String path = StringUtils.getRealFilePath(mActivity, uri);
                    Bitmap bitmap = BitmapUtil.getDecodeAbleBitmap(path);
                    if (bitmap != null) {
                        Observable.just(bitmap)
                                .subscribeOn(Schedulers.io())
                                .map(QRCodeDecoderUtils::syncDecodeQRCode)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        if (!d.isDisposed()) {
                                            mDialogLoading = new DialogLoading(mActivity);
                                            mDialogLoading.show();
                                        }
                                    }

                                    @Override
                                    public void onNext(String result) {
                                        if (mDialogLoading != null) {
                                            mDialogLoading.dismiss();
                                        }
                                        if (TextUtils.isEmpty(result)) {
                                            ToastUtils.init().showInfoToast(mActivity, "未发现二维码");
                                        } else {
                                            StringUtils.init().copyString(mActivity, result);
                                            ToastUtils.init().showQuickToast(result);
                                            getResult(result);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (mDialogLoading != null) {
                                            mDialogLoading.dismiss();
                                        }
                                        ToastUtils.init().showInfoToast(mActivity, "未发现二维码");
                                    }

                                    @Override
                                    public void onComplete() {
                                        refresh();
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    String uid = "";

    @SuppressLint("CheckResult")
    public void getUid(String result) {
        if (result.contains("?")) {
            String param = result.substring(result.indexOf("?"));
            if (param.contains("&")) {
                List<String> strings = Arrays.asList(param.split("&"));
                Observable.fromIterable(strings).subscribe(s -> {
                    if (s.contains("uid") && s.contains("=")) {
                        String[] split = s.split("=");
                        uid = split[1];
                    }
                });

            }
        }
    }

    public void getShopCoupon(ShopCouponBean bean) {
        ShopCouponDialog dialog = new ShopCouponDialog(this);
        dialog.bindBean(bean.data);
        dialog.show();
    }

}
