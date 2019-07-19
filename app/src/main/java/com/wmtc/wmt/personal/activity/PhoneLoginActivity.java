package com.wmtc.wmt.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.QLoginBean;
import com.wmtc.wmt.personal.pojo.AuthLoginPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.pojo.LoginPojo;
import com.wmtc.wmt.personal.presenter.PhoneLoginPresenter;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.bean.QQbean;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.event.WxLoginEvent;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.UUID;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.networklibrary.NetworkApplication;

//手机号码登录页面
public class PhoneLoginActivity extends SuperBaseActivity {

    //密码登录,手机号，验证码，获取验证码，登录，qq,微信，微博
    private TextView login_tv_pwd;
    private EditText login_et_phone;
    private EditText login_et_code;
    private TextView login_tv_code;
    private LinearLayout login_ll_qq;
    private LinearLayout login_ll_wechat;
    private LinearLayout login_ll_weibo;
    private TextView login_agreement;
    private RelativeLayout rl_back;

    private Tencent mTencent;
    private static boolean isServerSideLogin = false;
    private String authToken;
    private String channel;
    private String openId;
    private IWXAPI mWxApi;
    private PhoneLoginPresenter mPresenter;


    private void registerToWX() {
        mWxApi = WXAPIFactory.createWXAPI(this, WmtApplication.WXAPP_ID, false);
        mWxApi.registerApp(WmtApplication.WXAPP_ID);
    }

    @Override
    protected int initRootLayout() {
        return R.layout.activity_login;
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
        mPresenter = new PhoneLoginPresenter(this);
        registerToWX();
        //初始化控件
        login_tv_pwd = findViewById(R.id.login_tv_pwd);
        login_et_phone = findViewById(R.id.login_et_phone);
        login_et_phone.setCursorVisible(false);
        login_et_code = findViewById(R.id.login_et_code);
        login_et_code.setCursorVisible(false);
        login_tv_code = findViewById(R.id.login_tv_code);
        login_ll_qq = findViewById(R.id.login_ll_qq);
        login_ll_wechat = findViewById(R.id.login_ll_wechat);
        login_ll_weibo = findViewById(R.id.login_ll_weibo);
        login_agreement = findViewById(R.id.login_agreement);
        rl_back = findViewById(R.id.rl_back);
        mTencent = Tencent.createInstance("101550603", this.getApplicationContext());
        initClick();
    }

    private void initClick() {
        rl_back.setOnClickListener(v -> finish());
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
        login_et_code.setOnClickListener(v -> login_et_code.setCursorVisible(true));
        login_et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4) {
                    String et_phone = login_et_phone.getText().toString().replace(" ", "");
                    String et_code = login_et_code.getText().toString();
                    if (!TextUtils.isEmpty(et_phone)) {
                        if (!TextUtils.isEmpty(et_code)) {
                            LoginPojo pojo = new LoginPojo();
                            pojo.tel = et_phone;
                            pojo.verificationCode = et_code;
                            mPresenter.registByVerificationCode(pojo);
                            BGAKeyboardUtil.closeKeyboard(mActivity);
                        } else {
                            ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "请填写验证码");
                        }
                    } else {
                        ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "请输入手机号");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_tv_code.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(login_et_phone)) {
                ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "请输入手机号");
                return;
            }
            if (!StringUtils.init().checkPhone(login_et_phone)) {
                ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "手机号码格式不正确");
            } else {
                sendCode(login_et_phone.getText().toString().replace(" ", ""));
            }
        });
        login_agreement.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", "https://app.weimeitao.com/h5/index.html#/pages/index/seconde");
            ActivityUtils.init().start(PhoneLoginActivity.this, WebViewActivity.class, "", bundle);
        });

        login_tv_pwd.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, PwdLoginActivity.class);
        });
        login_ll_qq.setOnClickListener(v -> {
            if (!mTencent.isSessionValid()) {
                mTencent.login(this, "all", loginListener);
            }
        });
        login_ll_wechat.setOnClickListener(v -> {
            if (mWxApi == null) {
                mWxApi = WXAPIFactory.createWXAPI(this, WmtApplication.WXAPP_ID, true);
            }
            if (!mWxApi.isWXAppInstalled()) {
                ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "您还未安装微信客户端");
                return;
            } else {
                mWxApi.registerApp(WmtApplication.WXAPP_ID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = UUID.randomUUID().toString();
                //像微信发送请求
                mWxApi.sendReq(req);
            }
        });
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            KLog.e("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            //更新用户信息
        }
    };

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    public void registerAndLogin(LoginByCodeBean bean) {
        LoginByCodeBean.DataBean data = bean.data;
        if (data != null) {
            SharePreUtil.saveData(PhoneLoginActivity.this, "token", data.token);
            SharePreUtil.saveData(PhoneLoginActivity.this, "id", data.userId);
            SharePreUtil.saveData(PhoneLoginActivity.this, "islogin", "1");
            WmtApplication.id = data.userId;
            WmtApplication.token = data.token;
            WmtApplication.islogin = "1";
            NetworkApplication.mHeaderMap.put("token", WmtApplication.token);
            NetworkApplication.mHeaderMap.put("id", WmtApplication.id);
            NetworkApplication.mHeaderMap.put("type", "1");
            if (!TextUtils.isEmpty(data.getAvatar()) && !TextUtils.isEmpty(data.getName())
                    && !TextUtils.isEmpty(data.getSex()) && !TextUtils.isEmpty(data.getBirthday())) {
                KLog.e("TAG", "验证码成功-登陆");
                WmtApplication.isInfo = "1";
                EventBus.getDefault().post(new LoginSuccessEvent());
            } else {
                KLog.e("TAG", "验证码成功-完善个人信息");
                WmtApplication.isInfo = "";
                ActivityUtils.init().start(mActivity, SetPreMessageActivity.class);
            }
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.init().showInfoToast(mActivity, "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtils.init().showInfoToast(mActivity, "登录失败");
                return;
            }
            KLog.e("TAG", "显示登录信息" + response);
            QQbean bean = new Gson().fromJson(response.toString(), QQbean.class);
            authToken = bean.getAccess_token();
            channel = "QQ";
            openId = bean.getOpenid();
            //第三方授权登录
            AuthLoginPojo pojo = new AuthLoginPojo();
            pojo.setOpenId(openId);
            pojo.setChannel(channel);
            pojo.setAuthToken(authToken);
            mPresenter.authLogin(pojo);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            KLog.e("TAG", "onError" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            KLog.e("TAG", "取消登录");
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.e("TAG", "QQ登录成功" + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            if (resultCode == -1) {
//                ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "qq授权登录成功");
            } else if (resultCode == 0) {
                ToastUtils.init().showInfoToast(PhoneLoginActivity.this, "qq授权登录取消");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void authLoginDate(QLoginBean bean) {
        QLoginBean.DataBean dataBean = bean.data;
        if (dataBean != null) {
            if (StringUtils.isEmpty(dataBean.account)) {
                Bundle bundle = new Bundle();
                bundle.putString("token", dataBean.token);
                bundle.putString("id", dataBean.userId);
                bundle.putString("auth_id", dataBean.auth_id);
                bundle.putString("channel", dataBean.channel);
                bundle.putString("unionId", dataBean.unionId);
                ActivityUtils.init().start(mActivity, SetPhoneActivity.class, bundle);
            } else {
                SharePreUtil.saveData(PhoneLoginActivity.this, "token", dataBean.token);
                SharePreUtil.saveData(PhoneLoginActivity.this, "id", dataBean.userId);
                SharePreUtil.saveData(PhoneLoginActivity.this, "islogin", "1");
                WmtApplication.id = dataBean.userId;
                WmtApplication.token = dataBean.token;
                WmtApplication.islogin = "1";
                NetworkApplication.mHeaderMap.put("token", WmtApplication.token);
                NetworkApplication.mHeaderMap.put("id", WmtApplication.id);
                NetworkApplication.mHeaderMap.put("type", "1");
                if (StringUtils.isBlank(dataBean.avatar) && StringUtils.isBlank(dataBean.name)) {
                    KLog.e("TAG", "验证码成功-完善个人信息");
                    WmtApplication.isInfo = "";
                    ActivityUtils.init().start(mActivity, SetPreMessageActivity.class);
                } else {
                    WmtApplication.isInfo = "1";
                    EventBus.getDefault().post(new LoginSuccessEvent());
                }
            }
        }

    }

    private void sendCode(String tel) {
        SendCodePojo pojo = new SendCodePojo();
        pojo.setTel(tel);
        pojo.setTemplateCode("SMS_157689371");
        mPresenter.sendCode(pojo);
    }

    public void sendCodeDate(BaseBean bean) {
        StringUtils.init().getSmCode(login_tv_code);

    }

    @Subscribe
    public void onEvent(LoginSuccessEvent event) {
        finish();
    }

    @Subscribe
    public void onEvent(WxLoginEvent event) {
        String wxcode = event.backCode();
        String wxmsg = event.backMsg();
        if (wxmsg.equals("取消了微信登录")) {
            ToastUtils.init().showInfoToast(mActivity, wxmsg);
        } else {
            AuthLoginPojo pojo = new AuthLoginPojo();
            pojo.setOpenId(wxcode);
            channel = "WX";
            pojo.setChannel(channel);
            pojo.setAuthToken(wxcode);
            mPresenter.authLogin(pojo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }
}
