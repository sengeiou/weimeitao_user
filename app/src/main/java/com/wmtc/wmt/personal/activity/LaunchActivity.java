package com.wmtc.wmt.personal.activity;

import android.annotation.SuppressLint;
import android.view.View;

import com.wmtc.wmt.MainActivity;
import com.wmtc.wmt.R;
import com.wmtc.wmt.personal.event.CloseLauthEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

@SuppressLint("CheckResult")
public class LaunchActivity extends SuperBaseActivity {


    @Override
    protected int initRootLayout() {
        return R.layout.activity_launch;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        Observable.timer(1, TimeUnit.SECONDS).subscribe(aLong -> {
            ActivityUtils.init().start(LaunchActivity.this, MainActivity.class);
        });
    }

    @Subscribe
    public void onEvent(CloseLauthEvent event) {
        Observable.timer(1, TimeUnit.SECONDS).subscribe(
                aLong -> finish()
        );
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
