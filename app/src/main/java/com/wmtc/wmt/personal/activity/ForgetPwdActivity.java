package com.wmtc.wmt.personal.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.pojo.LoginPojo;
import com.wmtc.wmt.personal.presenter.ForgetPwdPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.NetworkApplication;

//忘记密码获取验证码页面
public class ForgetPwdActivity extends SuperBaseActivity {


    private RelativeLayout rl_back;
    private EditText forget_et_phone;
    private EditText forget_et_code;
    private TextView forget_tv_code;
    private TextView forget_tv_next;
    private ForgetPwdPresenter mPresenter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        mPresenter = new ForgetPwdPresenter(this);
        //初始化
        rl_back = findViewById(R.id.rl_back);
        forget_et_phone = findViewById(R.id.forget_et_phone);
        forget_et_phone.setCursorVisible(false);
        forget_et_code = findViewById(R.id.forget_et_code);
        forget_et_code.setCursorVisible(false);
        forget_tv_code = findViewById(R.id.forget_tv_code);
        forget_tv_next = findViewById(R.id.forget_tv_next);
        initClick();

    }


    private void initClick() {
        rl_back.setOnClickListener(v -> finish());

        //输入手机号是获取焦点
        forget_et_phone.setOnClickListener(v -> forget_et_phone.setCursorVisible(true));
        //输入手机号 344结构监听
        forget_et_phone.addTextChangedListener(new TextWatcher() {
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
                    forget_et_phone.setText(stringBuilder.toString());
                    forget_et_phone.setSelection(index);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //输入验证码是获取焦点
        forget_et_code.setOnClickListener(v -> forget_et_code.setCursorVisible(true));

        forget_tv_code.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(forget_et_phone.getText().toString().replace(" ", ""))) {
                if (!StringUtils.init().checkPhone(forget_et_phone)) {
                    ToastUtils.init().showInfoToast(ForgetPwdActivity.this, "手机号码格式不正确");
                } else {
                    sendCode(forget_et_phone.getText().toString().replace(" ", ""));
                }
            } else {
                ToastUtils.init().showInfoToast(ForgetPwdActivity.this, "请输入手机号");
            }
        });
        forget_tv_next.setOnClickListener(v -> {
            String et_phone = forget_et_phone.getText().toString().replace(" ", "");
            String et_code = forget_et_code.getText().toString();
            if (!TextUtils.isEmpty(et_phone)) {
                if (!TextUtils.isEmpty(et_code)) {
                    LoginPojo pojo = new LoginPojo();
                    pojo.tel = et_phone;
                    pojo.verificationCode = et_code;
                    mPresenter.registByVerificationCode(pojo);
                } else {
                    ToastUtils.init().showInfoToast(ForgetPwdActivity.this, "请填写验证码");
                }
            } else {
                ToastUtils.init().showInfoToast(ForgetPwdActivity.this, "请输入手机号");
            }
        });
    }


    private void sendCode(String tel) {
        SendCodePojo pojo = new SendCodePojo();
        pojo.setTel(tel);
        pojo.setTemplateCode("SMS_157684354");
        mPresenter.sendCode(pojo);
    }

    public void sendCodeDate(BaseBean bean) {
        StringUtils.init().getSmCode(forget_tv_code);
    }

    public void registerAndLogin(LoginByCodeBean bean) {
        LoginByCodeBean.DataBean data = bean.data;
        if (data != null) {
            SharePreUtil.saveData(mActivity, "token", data.token);
            SharePreUtil.saveData(mActivity, "id", data.userId);
            SharePreUtil.saveData(mActivity, "islogin", "1");
            WmtApplication.id = data.userId;
            WmtApplication.token = data.token;
            WmtApplication.islogin = "1";
            NetworkApplication.mHeaderMap.put("token", WmtApplication.token);
            NetworkApplication.mHeaderMap.put("id", WmtApplication.id);
            NetworkApplication.mHeaderMap.put("type", "1");
            Bundle bundle = new Bundle();
            bundle.putString("phone", forget_et_phone.getText().toString());
            bundle.putString("userId", WmtApplication.id);
            bundle.putString("token", WmtApplication.token);
            ActivityUtils.init().start(mActivity, SetPasswordActivity.class, bundle);
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

    public void smsVerDate() {


    }
}
