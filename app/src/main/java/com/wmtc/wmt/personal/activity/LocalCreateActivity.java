package com.wmtc.wmt.personal.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.LocalListBean;
import com.wmtc.wmt.personal.event.AddressCorUEvent;
import com.wmtc.wmt.personal.pojo.LocalPojo;
import com.wmtc.wmt.personal.presenter.LocalCreatePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.ShSwitchView;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalCreateActivity extends CommonWmtActivity {
    @BindView(R.id.editName)
    EditText mEditName;
    @BindView(R.id.editPhone)
    EditText mEditPhone;
    @BindView(R.id.tvLocalSecOk)
    TextView mTvLocalSecOk;
    @BindView(R.id.llSecLocal)
    LinearLayout mLlSecLocal;
    @BindView(R.id.editDetailLocal)
    EditText mEditDetailLocal;
    @BindView(R.id.editEmail)
    EditText mEditEmail;
    @BindView(R.id.switchView)
    ShSwitchView mSwitchView;
    @BindView(R.id.btnSave)
    Button mBtnSave;
    private Unbinder mBind;
    private LocalCreatePresenter mPresenter;
    private LocalPojo mPojo;
    private PickerUtils mPickerUtils;
    private boolean mIsEdit;
    private LocalListBean.DataBean mBean;

    @Override
    public int initAddLayout() {
        return R.layout.activity_local_create;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mPojo = new LocalPojo();
        mPickerUtils = new PickerUtils();
        mPojo.userId = WmtApplication.id;
        mIsEdit = mBundle.getBoolean("isEdit");
        mBean = mBundle.getParcelable("bean");
        if (mIsEdit && mBean != null) {

            mPojo.province = mBean.province;
            mPojo.city = mBean.city;
            mPojo.area = mBean.area;
            mPojo.addressId = mBean.id + "";
            mPojo.address = mBean.address;
            mPojo.isDefault = mBean.isDefault + "";
            mPojo.deliveryName = mBean.deliveryName;
            mPojo.deliveryTel = mBean.deliveryTel;

            mSwitchView.setOn(1 == (mBean.isDefault));
            String local = mPojo.province + mPojo.city + mPojo.area;
            mTvLocalSecOk.setText(local);
            mEditPhone.setText(StringUtils.init().fixNullStr(mPojo.deliveryTel));
            mEditName.setText(StringUtils.init().fixNullStr(mPojo.deliveryName));
            mEditDetailLocal.setText(StringUtils.init().fixNullStr(mPojo.address));
        } else {
            mSwitchView.setOn(true);
            mPojo.isDefault = "1";
        }

        mPickerUtils.initLocalPicker(mActivity, list -> {
            String province = list.get(0).name;
            String city = list.get(1).name;
            String area = list.get(2).name;
            mPojo.city = city;
            mPojo.area = area;
            mPojo.province = province;

            String local = province + city + area;
            mTvLocalSecOk.setText(local);
        });

        mPresenter = new LocalCreatePresenter(this);


        mBtnSave.setText(mIsEdit ? "修改地址" : "新增地址");
        initClick();

    }

    private void initClick() {
        mBtnSave.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditName)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入收货人姓名");
                return;
            }
            if (StringUtils.init().isEmpty(mEditPhone)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入收货电话");
                return;
            }
            if (StringUtils.init().isEmpty(mEditDetailLocal)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入收货地址");
                return;
            }
            mBtnSave.setEnabled(false);
            mPojo.deliveryName = StringUtils.init().fixNullStr(mEditName.getText());
            mPojo.deliveryTel = StringUtils.init().fixNullStr(mEditPhone.getText());
            mPojo.address = StringUtils.init().fixNullStr(mEditDetailLocal.getText());
            mPojo.isDefault = mSwitchView.isOn() ? "1" : "0";
            if (mIsEdit) {
                mPresenter.updateAddress(mPojo);
            } else {
                mPresenter.addAddress(mPojo);
            }
        });


        mTvLocalSecOk.setOnClickListener(v -> {
            mPickerUtils.localShow(mActivity);
        });
    }

    public void updateAddress() {
        ToastUtils.init().showInfoToast(mActivity, "收货地址修改成功");
        EventBus.getDefault().post(new AddressCorUEvent());
        finish();
    }

    public void addAddress() {
        ToastUtils.init().showInfoToast(mActivity, "收货地址添加成功");
        EventBus.getDefault().post(new AddressCorUEvent());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    public void complete() {
        if (mBtnSave != null) {
            mBtnSave.setEnabled(true);
        }
    }
}
