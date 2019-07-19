package com.wmtc.wmt.personal.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.LoginDateBean;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.pojo.PwdLoginPojo;
import com.wmtc.wmt.personal.presenter.PwdLoginPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.NetworkApplication;

//密码登录界面
public class PwdLoginActivity extends SuperBaseActivity {


    private RelativeLayout rl_back;
    private EditText login_et_phone;
    private EditText login_et_pwd;
    private ImageView login_show_pwd;
    private TextView login_tv_login;
    private TextView login_forget_pwd;
    private TextView tv_yzm;
    private boolean showpwd;
    private PwdLoginPresenter mPresenter;

    private String phone;
    private String pwd;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_pwd_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new PwdLoginPresenter(this);
        EventBus.getDefault().register(this);
        rl_back = findViewById(R.id.rl_back);
        login_et_phone = findViewById(R.id.login_et_phone);
        login_et_phone.setCursorVisible(false);
        login_et_pwd = findViewById(R.id.login_et_pwd);
        login_et_pwd.setCursorVisible(false);
        login_forget_pwd = findViewById(R.id.login_forget_pwd);
        tv_yzm = findViewById(R.id.tv_yzm);
        login_show_pwd = findViewById(R.id.login_show_pwd);
        login_tv_login = findViewById(R.id.login_tv_login);
        initClick();
    }

    private void initClick() {
        login_show_pwd.setOnClickListener(v -> {
            if (!showpwd) {
                login_show_pwd.setImageResource(R.mipmap.un_look);
                login_et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                login_show_pwd.setImageResource(R.mipmap.in_look);
                login_et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            showpwd = !showpwd;
            login_et_pwd.setSelection(login_et_pwd.length());
        });
        rl_back.setOnClickListener(v -> finish());

        login_tv_login.setOnClickListener(v -> {
            phone = login_et_phone.getText().toString().replace(" ", "");
            pwd = login_et_pwd.getText().toString().replace(" ", "");
            KLog.e("TAG", "phone=" + phone + "=pwd" + pwd);
            if (!TextUtils.isEmpty(phone)) {
                if (!TextUtils.isEmpty(pwd)) {
                    PwdLoginPojo pojo = new PwdLoginPojo();
                    pojo.setTel(phone);
                    pojo.setPassword(pwd);
                    mPresenter.pwdLogin(pojo);
                } else {
                    ToastUtils.init().showInfoToast(getApplication(), "请输入密码");
                }
            } else {
                ToastUtils.init().showInfoToast(getApplication(), "请输入手机号");
            }
        });

        login_forget_pwd.setOnClickListener(v -> ActivityUtils.init().start(mActivity, ForgetPwdActivity.class));
        tv_yzm.setOnClickListener(v -> ActivityUtils.init().start(mActivity, PhoneLoginActivity.class));
        login_et_phone.setOnClickListener(v -> login_et_phone.setCursorVisible(true));
        login_et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        stringBuilder.append(s.charAt(i));
                        if ((stringBuilder.length() == 4 || stringBuilder.length() == 9)
                                && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
                        }
                    }
                }
                if (!stringBuilder.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (stringBuilder.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    login_et_phone.setText(stringBuilder.toString());
                    login_et_phone.setSelection(index);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_et_pwd.setOnClickListener(v -> login_et_pwd.setCursorVisible(true));
    }

    public void pwdLoginDate(LoginDateBean bean) {
        LoginDateBean.DataBean data = bean.data;
        if (data != null) {
            SharePreUtil.saveData(mActivity, "token", data.token);
            SharePreUtil.saveData(mActivity, "id", data.userId);
            SharePreUtil.saveData(mActivity, "islogin", "1");
            NetworkApplication.mHeaderMap.put("token", data.token);
            NetworkApplication.mHeaderMap.put("id", data.userId);
            NetworkApplication.mHeaderMap.put("type", "1");
            WmtApplication.id = data.userId;
            WmtApplication.token = data.token;
            WmtApplication.islogin = "1";
            if (StringUtils.isBlank(data.avatar) && StringUtils.isBlank(data.name)) {
                KLog.e("TAG", "验证码成功-完善个人信息");
                WmtApplication.isInfo = "";
                ActivityUtils.init().start(mActivity, SetPreMessageActivity.class);
            } else {
                WmtApplication.isInfo = "1";
                EventBus.getDefault().post(new LoginSuccessEvent());
            }
        }

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
}
