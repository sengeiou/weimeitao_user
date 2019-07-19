package com.wmtc.wmt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.wmtc.wmt.appoint.activity.OffLineActivity;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.dialog.CouponDialog;
import com.wmtc.wmt.appoint.dialog.ShopCouponDialog;
import com.wmtc.wmt.appoint.event.QCodeEvent;
import com.wmtc.wmt.base.FragmentFactory;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.event.ShareAllEvent;
import com.wmtc.wmt.forum.event.ShareOneEvent;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.event.CloseLauthEvent;
import com.wmtc.wmt.personal.event.VersionCheckEvent;
import com.wmtc.wmt.personal.presenter.MainPresenter;
import com.wmtc.wmt.wxapi.WXShare;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.klog.KLog;
import top.jplayer.networklibrary.net.download.DownloadByManager;

public class MainActivity extends SuperBaseActivity {

    private MainPresenter mPresenter;
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;
    public FrameLayout mFlFragment;
    private WXShare mWxShare;


    @Override
    protected int initRootLayout() {
        return R.layout.activity_main;
    }


    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        addOnSoftKeyBoardVisibleListener();
        EventBus.getDefault().register(this);
        mStringName = getResources().getString(R.string.app_name);
        mWxShare = new WXShare(this);
        mWxShare.register();
        mFlFragment = view.findViewById(R.id.flFragment);
        isOpenDoubleBack = true;
        mPresenter = new MainPresenter(this);
        mDownloadByManager = new DownloadByManager(this);
        bottomBar();
        UserPojo pojo = new UserPojo();
        if (!"".equals(WmtApplication.id)) {
            pojo.setUserId(WmtApplication.id);
            mPresenter.getNewUseCoupon(pojo);
        }
        mPresenter.getNewVersion();
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map(o -> {
                    JMessageClient.setDebugMode(true);
                    JMessageClient.init(this);
                    JPushInterface.setDebugMode(true);
                    JPushInterface.init(this);
                    JMessageClient.login("C_" + WmtApplication.id, "123456", new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            Log.e("-------", "登录: " + i + s + "----------");
                        }
                    });
                    WmtApplication.mJPushUdid = JPushInterface.getRegistrationID(this);
                    LogUtil.e(WmtApplication.mJPushUdid + "----------极光ID--------");
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (StringUtils.isNotBlank(WmtApplication.mJPushUdid)) {
                        mPresenter.updateUserInfo();
                    }
                });
        JMessageClient.registerEventReceiver(this);
        EventBus.getDefault().post(new CloseLauthEvent());


        AndPermission.with(this)
                .permission(Permission.CAMERA,
                        Permission.RECORD_AUDIO,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.ACCESS_COARSE_LOCATION)
                .onGranted(permissions -> {
                })
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();

    }

    private void bottomBar() {
        mViewList = new ArrayList<>();

        LinearLayout llHome = superRootView.findViewById(R.id.llHome);
        mViewList.add(llHome);
        llHome.setOnClickListener(v -> onTabClick((LinearLayout) v, 0));

//        LinearLayout llScheme = superRootView.findViewById(R.id.ll);
//        mViewList.add(llScheme);
//        llScheme.setOnClickListener(v -> {
//            onTabClick((LinearLayout) v, 1);
//        });

        LinearLayout llServer = superRootView.findViewById(R.id.llServer);
        mViewList.add(llServer);
        llServer.setOnClickListener(v -> onTabClick((LinearLayout) v, 2));

        LinearLayout llMsg = superRootView.findViewById(R.id.llMsg);
        mViewList.add(llMsg);
        llMsg.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                onTabClick((LinearLayout) v, 3);
            }
        });

        LinearLayout llMe = superRootView.findViewById(R.id.llMe);
        mViewList.add(llMe);
        llMe.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                onTabClick((LinearLayout) v, 4);
            }
        });

        onTabClick(mViewList.get(0), 0);
    }

    public int curIndex = -1;

    public void onTabClick(LinearLayout view, int index) {

        if (curIndex == index) {
            return;
        }
        for (LinearLayout linearLayout : mViewList) {
            linearLayout.getChildAt(0).setSelected(false);
            linearLayout.getChildAt(1).setSelected(false);
        }
        view.getChildAt(0).setSelected(true);
        view.getChildAt(1).setSelected(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(index + "");
        if (fragment == null) {
            fragment = FragmentFactory.instance().getSingleFragment(index);
            transaction.add(R.id.flFragment, fragment, index + "");
        }
        for (Fragment fragmentItem : getSupportFragmentManager().getFragments()) {
            if (!fragmentItem.isHidden()) {
                transaction.hide(fragmentItem);
            }
        }
        if (fragment.isHidden()) {
            transaction.show(fragment);
        }
        curIndex = index;
        transaction.commitAllowingStateLoss();
    }


    public void getNewUseCouponDate(CouponDialogbean bean) {
        KLog.e("TAG", "dialog返回" + new Gson().toJson(bean));
        if (bean.data != null && bean.data.size() > 0) {
            CouponDialog dialog = new CouponDialog(this);
            dialog.setBean(bean.data);
            dialog.show();
        }
    }


    public void onEvent(NotificationClickEvent event) {
        Bundle bundle = new Bundle();
        Message message = event.getMessage();
        bundle.putString("name", message.getFromID());
        ActivityUtils.init().startWithTask(mActivity, CustomChatActivity.class, message.getFromUser().getNickname(), bundle);
        LogUtil.e(event);
        LogUtil.e("---------NotificationClickEvent--------");
    }

    private Bitmap mBitmap;
    private String mStringName;

    @Subscribe
    public void shopCoupon(QCodeEvent event) {
        String result = event.result;
        if (result.contains("index/shopw")) {//收款码
            LogUtil.e("------收款码------");
            Bundle bundle = new Bundle();
            getUid(result);
            bundle.putString("id", uid);
            ActivityUtils.init().start(mActivity, OffLineActivity.class, "付款", bundle);
        } else {//红包码
            getUid(result);
            mPresenter.getShopCoupon(uid);
        }
    }

    @Subscribe
    public void versionEvent(VersionCheckEvent event) {
        if (mData != null) {
            downloadByManager(mData);
        }
    }

    @Subscribe
    public void shareEvent(ShareOneEvent event) {
        LogUtil.e(event);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        mWxShare.shareUrl(event.url,
                mStringName,
                mBitmap,
                "邀请注册有好礼",
                SendMessageToWX.Req.WXSceneSession);
    }

    @Subscribe
    public void shareEvent(ShareAllEvent event) {
        LogUtil.e(event);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        mWxShare.shareUrl(event.url,
                mStringName,
                mBitmap,
                "邀请注册有好礼",
                SendMessageToWX.Req.WXSceneTimeline);
    }

    public List<LinearLayout> mViewList;

    public static int keyboardHeight = 0;
    boolean isVisiableForLast = false;

    public void addOnSoftKeyBoardVisibleListener() {
        if (keyboardHeight > 0) {
            return;
        }
        final View decorView = getWindow().getDecorView();
        onGlobalLayoutListener = () -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int height = decorView.getHeight();
            boolean vb = (double) displayHight / height < 0.8;
            int statusBarHeight = 0;
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = getApplicationContext().getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (vb && vb != isVisiableForLast) {
                //获得键盘高度
                keyboardHeight = (top.jplayer.baseprolibrary.utils.ScreenUtils.getScreenHeight() - (height - displayHight - statusBarHeight));
                LogUtil.e("keyboardHeight=============" + keyboardHeight);
                SharePreUtil.saveData(mActivity, "keyboardHeight", keyboardHeight);
            }
            isVisiableForLast = vb;
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
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


    /**
     * 添加 下载
     */
    private VersionBean.DataBean mData;
    private DownloadByManager mDownloadByManager;

    public void version(VersionBean response) {
        mData = response.data;
        if (mData != null && mData.url != null && Integer.parseInt
                (mData.versionCode) > BuildConfig.VERSION_CODE) {
            SharePreUtil.saveData(mActivity, "curVersion", "存在新版本");
            DialogLogout dialog =
                    new DialogLogout(mActivity).setForce("1".equals(mData.isForce)).setTitle("更新提示").setSubTitle(mData.description);
            dialog.show(R.id.btnSure, view -> {
                dialog.dismiss();
                AndPermission.with(this)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(permissions -> {
                            downloadByManager(mData);
                        })
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            });
        } else {
            SharePreUtil.saveData(mActivity, "curVersion", "");
        }
    }

    public void downloadByManager(VersionBean.DataBean data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !mActivity.getPackageManager()
                .canRequestPackageInstalls()) {// 8.0  安装问题 是否允许外部安装
            Uri packageURI = Uri.parse("package:" + mActivity.getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    packageURI);
            startActivityForResult(intent, 10000);
        } else {
            updateVersion(data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK) {
            if (mData != null) {
                updateVersion(mData);
            }
        }
    }

    public void updateVersion(VersionBean.DataBean data) {
        int newCode = Integer.parseInt(data.versionCode);
        mDownloadByManager.bind(newCode, data.description, data.url)
                .download().listener((currentByte, totalByte) -> {
            LogUtil.e(currentByte + "-------------" + totalByte);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mDownloadByManager != null)
            mDownloadByManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDownloadByManager != null)
            mDownloadByManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWxShare.unregister();
        EventBus.getDefault().unregister(this);
        JMessageClient.unRegisterEventReceiver(this);
    }


}
