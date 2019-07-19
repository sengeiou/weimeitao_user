package com.wmtc.wmt.personal.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.pojo.PhonePojo;
import com.wmtc.wmt.personal.presenter.SetPhonePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

public class SetPhoneActivity extends SuperBaseActivity {

    private SetPhonePresenter mPresenter;
    private EditText et_phone;
    private TextView tv_next;
    private RelativeLayout rl_back;
    private String token, id;
    private String auth_id;
    private String mChannel;
    private String mUnionId;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_set_phone;
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
        mPresenter = new SetPhonePresenter(this);
        token = mBundle.getString("token");
        id = mBundle.getString("id");
        auth_id = mBundle.getString("auth_id");
        mChannel = mBundle.getString("channel");
        mUnionId = mBundle.getString("unionId");
        initView();
    }

    private void initView() {
        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(v -> finish());
        et_phone = findViewById(R.id.et_phone);
        //输入手机号是获取焦点
        et_phone.setOnClickListener(v -> et_phone.setCursorVisible(true));
        //输入手机号 344结构监听
        et_phone.addTextChangedListener(new TextWatcher() {
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
                    et_phone.setText(stringBuilder.toString());
                    et_phone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(v -> {
//            PhonePojo pojo = new PhonePojo();
//            pojo.setChannel(mChannel);
//            pojo.setAuth_id(auth_id);
//            pojo.setAccount(et_phone.getText().toString().replace(" ", ""));
//            mPresenter.setPhone(pojo);
            if (!StringUtils.init().checkPhone(et_phone)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入手机号");
                return;
            }  //发送验证码
            sendCode(et_phone.getText().toString().replace(" ", ""));

        });
    }

    private void sendCode(String tel) {
        SendCodePojo pojo = new SendCodePojo();
        pojo.setTel(tel);
        pojo.setTemplateCode("SMS_157689371");
        mPresenter.sendCode(pojo);
    }

    public void setPhoneDate(BaseBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", et_phone.getText().toString());
        bundle.putString("token", token);
        bundle.putString("id", id);
        ActivityUtils.init().start(mActivity, SetPwdByAuthActivity.class, bundle);
    }

    public void sendCodeDate(BaseBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", et_phone.getText().toString());
        bundle.putString("token", token);
        bundle.putString("id", id);
        bundle.putString("auth_id", auth_id);
        bundle.putString("mChannel", mChannel);
        bundle.putString("unionId", mUnionId);
        ActivityUtils.init().start(mActivity, SetPwdByAuthActivity.class, bundle);
    }


    public void setPhoneDateFail(BaseBean bean) {
        ToastUtils.init().showInfoToast(mActivity, bean.message);
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
