package com.wmtc.wmt.personal.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.presenter.PasswordResetPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.utils.klog.KLog;

public class SetPasswordActivity extends SuperBaseActivity {

    private String phone;
    private String userId;
    private boolean showpwd;

    private TextView set_tv_phone;
    private EditText set_et_pwd;
    private ImageView set_show_pwd;
    private TextView set_tv_login;
    private TextView login_tv_pwd;
    private RelativeLayout rl_back;

    private PasswordResetPresenter mPresenter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_set_password;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        mPresenter = new PasswordResetPresenter(this);
        phone = mBundle.getString("phone");
        userId = mBundle.getString("userId");
        set_tv_phone = findViewById(R.id.set_tv_phone);
        set_tv_phone.setText(" " + phone);
        set_et_pwd = findViewById(R.id.set_et_pwd);
        set_et_pwd.setCursorVisible(false);
        set_show_pwd = findViewById(R.id.set_show_pwd);
        set_tv_login = findViewById(R.id.set_tv_login);
        login_tv_pwd = findViewById(R.id.login_tv_pwd);
        rl_back = findViewById(R.id.rl_back);
        initClick();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).init();
    }

    private void initClick() {
        set_et_pwd.setOnClickListener(v -> set_et_pwd.setCursorVisible(true));
        set_show_pwd.setOnClickListener(v -> {
            if (!showpwd) {
                set_show_pwd.setImageResource(R.mipmap.in_look);
                set_et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                set_show_pwd.setImageResource(R.mipmap.un_look);
                set_et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            showpwd = !showpwd;
            set_et_pwd.setSelection(set_et_pwd.length());
        });
        set_tv_login.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(set_et_pwd)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入需要修改的账号密码");
                return;
            }
            String replace = set_et_pwd.getText().toString().replace(" ", "");
            mPresenter.resetPassword(userId, replace);
        });
        rl_back.setOnClickListener(v -> finish());
        login_tv_pwd.setOnClickListener(v -> ActivityUtils.init().start(mActivity, PhoneLoginActivity.class));
    }

    @Subscribe
    public void onEvent(LoginSuccessEvent event) {
        finish();
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void resetPassword() {
        KLog.e("TAG", "重置密码成功跳转登录");
        EventBus.getDefault().post(new LoginSuccessEvent());
        finish();
    }
}
