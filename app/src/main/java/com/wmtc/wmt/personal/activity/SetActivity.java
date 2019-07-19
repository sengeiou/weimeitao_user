package com.wmtc.wmt.personal.activity;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.dialog.LogoutDialog;
import com.wmtc.wmt.personal.event.VersionCheckEvent;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.im.android.api.JMessageClient;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.NetworkApplication;

public class SetActivity extends CommonWmtActivity {

    RelativeLayout rl_grzl;
    RelativeLayout rl_tuichu;
    RelativeLayout mRlFeed;
    private LogoutDialog mLogout;

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        rl_grzl = findViewById(R.id.rl_grzl);
        mRlFeed = findViewById(R.id.rlFeed);
        rl_grzl.setOnClickListener(v -> ActivityUtils.init().start(mActivity, SetPersonalMessageActivity.class));
        rl_tuichu = findViewById(R.id.rl_tuichu);
        rl_tuichu.setOnClickListener(v -> {
            mLogout = new LogoutDialog(mActivity);
            mLogout.show(R.id.btnSure, view -> {
                //设置状态 退出
                SharePreUtil.saveData(mActivity, "token", "");
                SharePreUtil.saveData(mActivity, "id", "");
                SharePreUtil.saveData(mActivity, "islogin", "");
                NetworkApplication.mHeaderMap.put("id", "");
                NetworkApplication.mHeaderMap.put("token", "");
                WmtApplication.id = "";
                WmtApplication.token = "";
                WmtApplication.islogin = "";
                ActivityUtils.init().start(mActivity, PhoneLoginActivity.class);
                JMessageClient.logout();
                finish();
                mLogout.dismiss();
            });
        });
        TextView tvCurVersion = findViewById(R.id.tvCurVersion);
        tvCurVersion.setOnClickListener(v -> {
            String curVersion = (String) SharePreUtil.getData(mActivity, "curVersion", "");
            if (StringUtils.isBlank(curVersion)) {
                ActivityUtils.init().start(mActivity, AboutActivity.class, "关于唯美淘");
            } else {
                EventBus.getDefault().post(new VersionCheckEvent());
                finish();
            }
        });
        mRlFeed.setOnClickListener(v -> ActivityUtils.init().start(mActivity, FeedBackActivity.class, "反馈内容"));
    }

    @Override
    public int initAddLayout() {
        return R.layout.activity_set;
    }

}
