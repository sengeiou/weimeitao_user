package com.wmtc.wmt.personal.activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.personal.bean.CodeLoginBean;
import com.wmtc.wmt.personal.pojo.CodeLoginPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.forum.pojo.SetPwdPojo;
import com.wmtc.wmt.personal.pojo.PhonePojo;
import com.wmtc.wmt.personal.presenter.SetPwdByAuthPresenter;

import top.jplayer.baseprolibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.networklibrary.NetworkApplication;

public class SetPwdByAuthActivity extends SuperBaseActivity {


    private SetPwdByAuthPresenter mPresenter;
    private String phone, token, id;
    private TextView tv_phone;
    private TextView login_tv_code;
    private EditText login_et_code, login_et_pwd;
    private ImageView login_show_pwd;
    private boolean showpwd;
    private TextView tv_forget, btn_ok;
    private String mAuth_id;
    private String mChannel;
    private String mUnionId;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_set_pwd_auth;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new SetPwdByAuthPresenter(this);
        EventBus.getDefault().register(this);
        phone = mBundle.getString("phone");
        token = mBundle.getString("token");
        id = mBundle.getString("id");
        mAuth_id = mBundle.getString("auth_id");
        mChannel = mBundle.getString("mChannel");
        mUnionId = mBundle.getString("unionId");
        initView();
    }

    private void initView() {
        tv_phone = findViewById(R.id.tv_phone);
        tv_phone.setText(phone);
        login_tv_code = findViewById(R.id.login_tv_code);
        login_tv_code.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(phone.replace(" ", ""))) {
                //检查手机号
                StringUtils.init().checkPhone(tv_phone);
                if (!StringUtils.init().checkPhone(tv_phone)) {
                    ToastUtils.init().showInfoToast(mActivity, "手机号码格式不正确");
                } else {
                    //发送验证码
                    sendCode(phone.replace(" ", ""));
                }
            } else {
                ToastUtils.init().showInfoToast(mActivity, "请输入手机号");
            }
        });
        StringUtils.init().getSmCode(login_tv_code);
        login_et_code = findViewById(R.id.login_et_code);
        login_et_code.setOnClickListener(v -> {
            login_et_code.setCursorVisible(true);
        });
        login_et_pwd = findViewById(R.id.login_et_pwd);
        login_et_pwd.setOnClickListener(v -> {
            login_et_pwd.setCursorVisible(true);
        });
        login_show_pwd = findViewById(R.id.login_show_pwd);
        login_show_pwd.setOnClickListener(v -> {
            if (!showpwd) {
                login_show_pwd.setImageResource(R.mipmap.in_look);
                login_et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                login_show_pwd.setImageResource(R.mipmap.un_look);
                login_et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            showpwd = !showpwd;
            login_et_pwd.setSelection(login_et_pwd.length());
        });
        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, ForgetPwdActivity.class);
        });
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(login_et_code)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入验证码");
                return;
            }
            if (StringUtils.init().isEmpty(login_et_pwd)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入登录密码");
                return;
            }
            PhonePojo phonePojo = new PhonePojo();
            phonePojo.setChannel(mChannel);
            phonePojo.setUnionId(mUnionId);
            phonePojo.setAuth_id(mAuth_id);
            phonePojo.setAccount(phone.replace(" ", ""));
            phonePojo.setPassword(login_et_pwd.getText().toString());
            phonePojo.setVerificationCode(login_et_code.getText().toString());
            mPresenter.authLoginBindTel(phonePojo);
        });
    }


    @Subscribe
    public void onEvent(LoginSuccessEvent event) {
        finish();
    }

    private void sendCode(String tel) {
        SendCodePojo pojo = new SendCodePojo();
        pojo.setTel(tel);
        pojo.setTemplateCode("SMS_157689371");
        mPresenter.sendCode(pojo);
    }

    public void sendCodeDate(BaseBean bean) {
        if (bean.result.equals("ok")) {
            ToastUtils.init().showInfoToast(mActivity, "发送成功");
//            //显示倒计时效果
//            requestCode();
            StringUtils.init().getSmCode(login_tv_code);
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
    }

    public void authLoginBindTel(CodeLoginBean bean) {
        CodeLoginBean.DataBean dataBean = bean.data;
        if (dataBean != null) {
            SharePreUtil.saveData(mActivity, "token", dataBean.token);
            SharePreUtil.saveData(mActivity, "id", dataBean.userId);
            SharePreUtil.saveData(mActivity, "islogin", "1");
            WmtApplication.id = dataBean.userId;
            WmtApplication.token = dataBean.token;
            WmtApplication.islogin = "1";
            NetworkApplication.mHeaderMap.put("token", dataBean.token);
            NetworkApplication.mHeaderMap.put("id", dataBean.userId);
            NetworkApplication.mHeaderMap.put("type", "1");
            //检测是否完善信息
            if (!TextUtils.isEmpty(dataBean.avatar) && !TextUtils.isEmpty(dataBean.name)
                    && !TextUtils.isEmpty(dataBean.sex) && !TextUtils.isEmpty(dataBean.birthday)) {
                EventBus.getDefault().post(new LoginSuccessEvent());
                WmtApplication.isInfo = "1";
            } else {
                KLog.e("TAG", "验证码成功-完善个人信息");
                WmtApplication.isInfo = "";
                ActivityUtils.init().start(mActivity, SetPreMessageActivity.class);
            }
        }
    }
}
