package com.wmtc.wmt.base;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.umeng.commonsdk.UMConfigure;
import com.wmtc.chatmodule.adapter.holder.MessageMapHolder;
import com.wmtc.chatmodule.base.OblMessageManager;
import com.wmtc.chatmodule.enity.CustomHolder;
import com.wmtc.chatmodule.enity.LocalMapMessage;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.personal.activity.PhoneLoginActivity;
import com.wmtc.wmt.personal.activity.SetPreMessageActivity;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.listener.SampleApplicationLifecycleCallbacks;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.klog.KLog;
import top.jplayer.networklibrary.NetworkApplication;


/**
 * Created by Administrator on 2018/8/7.
 */

public class WmtApplication extends MultiDexApplication {

    public static String islogin = "";
    public static String isInfo = "1";
    public static String id = "";
    public static String token = "";
    public static String type = "1";
    public static final String B_JMKEY = "823eaafdf72eebfc9e81e382";
    public static final String C_JMKEY = "af81909ace48e59e14d9ca0a";
    public static String url_host = "";
    public static String mJPushUdid;
    public static final String ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com/";
    public static String avatar = "";
    public static final String WXAPP_ID = "wxb3ced52d70985d54";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Observable.just(1).subscribeOn(Schedulers.io())
                .map(o -> {
                    MultiDex.install(this);
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            url_host = "http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/";
        } else {
            url_host = "http://prod-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/";
        }

        id = (String) SharePreUtil.getData(WmtApplication.this, "id", "");
        token = (String) SharePreUtil.getData(WmtApplication.this, "token", "");
        islogin = (String) SharePreUtil.getData(WmtApplication.this, "islogin", "");
        BaseInitApplication.with(this)
                .retrofit()//网络请求
                .swipeBack()//侧滑返回
                .fixFileProvide()
                .lifecycle(new SampleApplicationLifecycleCallbacks())
                .zxing();//二维码
        NetworkApplication.mHeaderMap.put("id", id);
        NetworkApplication.mHeaderMap.put("token", token);
        NetworkApplication.mHeaderMap.put("type", "1");
        NetworkApplication.mHeaderMap.put("version", BuildConfig.VERSION_NAME);
        KLog.init(true);
        Observable.just(1).subscribeOn(Schedulers.io())
                .map(o -> {
                    LogUtil.e(WmtApplication.mJPushUdid + "----------极光ID--------");
                    UMConfigure.init(this, "5ceb61820cafb204a8000ebf", "wmtc", UMConfigure.DEVICE_TYPE_PHONE, null);
                    JMessageClient.setDebugMode(true);
                    JMessageClient.init(this);
                    JPushInterface.setDebugMode(true);
                    JPushInterface.init(this);
                    OblMessageManager.init(this)
                            .addCustomMsg(new CustomHolder<>(MessageMapHolder.class, R.layout.obl_message_map,
                                    LocalMapMessage.class))
                            .build();
                    mJPushUdid = JPushInterface.getRegistrationID(this);
                    BaseInitApplication.with(this).crash(true);
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }


    public static boolean loginOrInfoToStart(Activity activity) {
        if (!"".equals(WmtApplication.islogin) && !"".equals(WmtApplication.isInfo)) {
            return true;
        } else {
            if ("".equals(WmtApplication.islogin)) {
                ActivityUtils.init().start(activity, PhoneLoginActivity.class);
            } else if ("".equals(WmtApplication.isInfo)) {
                ActivityUtils.init().start(activity, SetPreMessageActivity.class);
            }
            return false;
        }
    }
}
