package com.wmtc.wmt.personal.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;

import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.DictOnlySelAdapter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.personal.event.AvatarEvent;
import com.wmtc.wmt.personal.event.InterestEvent;
import com.wmtc.wmt.personal.event.RefreshUserInfoEvent;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.personal.presenter.UpdateUserInfoPresenter;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

public class SetPersonalMessageActivity extends CommonWmtActivity {

    private UpdateUserInfoPresenter mPresenter;
    private PolygonImageView im_photo;
    private EditText et_name;
    private EditText et_qianming;
    private TextView tv_fuzhi;
    private TextView tv_sex;
    private TextView tv_birth;
    private TextView tv_diqu;
    private TextView tv_address;
    private EditText et_phone;
    private TextView tv_biaoqian;
    private PickerUtils pickerUtils;
    private ArrayList<String> skinList = new ArrayList<>();
    private String[] mSplit = new String[]{};
    private List<PickerUtils.SelectLocalBean> localList = new ArrayList<>();
    Map<String, String> mStringMap = new HashMap<>();
    private ArrayList<String> biaoqianList = new ArrayList<>();
    private String msex = "";
    private RelativeLayout mRlAddress;
    private TagFlowLayout mTagFlow;
    private TextView mTvAddInterest;
    private DictOnlySelAdapter mOnlySelAdapter;


    @Override
    public int initAddLayout() {
        return R.layout.activity_personal_set;

    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        EventBus.getDefault().register(this);
        mPresenter = new UpdateUserInfoPresenter(this);
        mPresenter.getUserInfo();
        mTvToolRight.setVisibility(View.VISIBLE);
        pickerUtils = new PickerUtils();
        mTagFlow = findViewById(R.id.tagFlow);
        initView();
        onClick();
        getSkinDict();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(InterestEvent event) {
        tv_biaoqian.setText(event.interest);
        ArrayList<String> list = new ArrayList<>();
        if (event.interest.contains(",")) {
            Observable.fromArray(event.interest.split(",")).subscribe(list::add, throwable -> {
            });
        } else {
            list.add(event.interest);
        }
        list.add("添加");
        mTagFlow.setAdapter(new DictOnlySelAdapter(list));
    }

    //获取肤质标签
    private void getSkinDict() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("user_fuzhi");
        mPresenter.getSkinDict(pojo);
    }

    public void getSkinDictDate(DictListBean bean) {
        if (bean.result.equals("ok")) {
            skinList.clear();
            for (int i = 0; i < bean.data.size(); i++) {
                skinList.add(bean.data.get(i).name);
            }
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    private void getxingqu() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("topic");
        mPresenter.getxingqu(pojo);
    }

    public void getxingquDate(DictListBean bean) {
        if (bean.result.equals("ok")) {
            biaoqianList.clear();
            for (int i = 0; i < bean.data.size(); i++) {
                biaoqianList.add(bean.data.get(i).name);
            }
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    //初始化
    private void initView() {
        im_photo = findViewById(R.id.im_photo);
        et_qianming = findViewById(R.id.et_qianming);
        et_name = findViewById(R.id.et_name);
        tv_fuzhi = findViewById(R.id.tv_fuzhi);
        tv_sex = findViewById(R.id.tv_sex);
        tv_birth = findViewById(R.id.tv_birth);
        tv_diqu = findViewById(R.id.tv_diqu);
        tv_address = findViewById(R.id.tv_address);
        et_phone = findViewById(R.id.et_phone);
        tv_biaoqian = findViewById(R.id.tv_biaoqian);
        mRlAddress = findViewById(R.id.rlAddress);
        mTvAddInterest = findViewById(R.id.tvAddInterest);
        mTagFlow.setOnTagClickListener((view, position, parent) -> {
            if (position + 1 >= mTagFlow.getAdapter().getCount()) {
                BGAKeyboardUtil.closeKeyboard(this);
                Bundle bundle = new Bundle();
                bundle.putString("selInterest", tv_biaoqian.getText().toString());
                ActivityUtils.init().start(mActivity, InterestActivity.class, "添加标签", bundle);
            }
            return false;
        });
    }


    @SuppressLint("CheckResult")
    public void getUserInfo(UserInfoBean bean) {
        UserInfoBean.DataBean data = bean.data;
        if (data != null) {
            WmtApplication.avatar = WmtApplication.url_host + data.avatar;
            Glide.with(mActivity).load(WmtApplication.avatar)
                    .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                    .into(im_photo);
            et_name.setText(StringUtils.init().fixNullStr(data.name));
            et_qianming.setText(StringUtils.init().fixNullStr(data.introduce));
            tv_fuzhi.setText(StringUtils.init().fixNullStr(data.userFuzhiName));
            if (data.sex != null) {
                if ("1".equals(data.sex)) {
                    tv_sex.setText("男");
                } else {
                    tv_sex.setText("女");
                }
            }
            tv_birth.setText(StringUtils.init().fixNullStr(data.birthday));
            tv_diqu.setText(StringUtils.init().fixNullStr(data.loginCityName));
//            tv_address.setText(StringUtils.init().fixNullStr(data.deliveryAddress));
            et_phone.setText(StringUtils.init().fixNullStr(data.contactTel));
            String strSel = StringUtils.init().fixNullStr(data.interestName);
            tv_biaoqian.setText(strSel);
            ArrayList<String> list = new ArrayList<>();
            if (strSel.contains(",")) {
                String[] split = strSel.split(",");
                Observable.fromArray(split).subscribe(list::add);
            } else {
                list.add(strSel);
            }
            list.add("添加");
            mOnlySelAdapter = new DictOnlySelAdapter(list);
            mTagFlow.setAdapter(mOnlySelAdapter);
            if (StringUtils.isNotBlank(data.birthday)) {
                WmtApplication.isInfo = "1";
            } else {
                WmtApplication.isInfo = "";
            }

        }

    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        KLog.e("TAG", "保存信息");
        if (et_name.getText().length() > 8) {
            ToastUtils.init().showErrorToast(mActivity, "昵称长度不可超过8字");
            return;
        }


        UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
        pojo.setUserId(WmtApplication.id);
        WmtApplication.mJPushUdid = JPushInterface.getRegistrationID(this);
        pojo.setJiguangId(WmtApplication.mJPushUdid);
        pojo.setName(et_name.getText().toString());
        pojo.setIntroduce(et_qianming.getText().toString());
        pojo.setUserFuzhiName(tv_fuzhi.getText().toString());
        if (tv_sex.getText().toString().equals("男")) {
            msex = "1";
        } else {
            msex = "2";
        }
        pojo.setSex(msex);
        pojo.setBirthday(tv_birth.getText().toString());
        pojo.setLoginCityName(tv_diqu.getText().toString());
        pojo.setContactTel(et_phone.getText().toString());
        pojo.setInterestName(tv_biaoqian.getText().toString());
        mPresenter.updateUserInfo(pojo);
    }

    private void onClick() {


        im_photo.setOnClickListener(v -> {
            openCamera();
        });
        et_name.setOnClickListener(v -> {
            et_name.setCursorVisible(true);
        });
        et_qianming.setOnClickListener(v -> {
            et_qianming.setCursorVisible(true);
        });
        tv_fuzhi.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            pickerUtils.initStringPicker(skinList, 0, this, (position, string) -> {
                tv_fuzhi.setText(string);
            });
            pickerUtils.mPickerView.show();
        });
        tv_sex.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            ArrayList<String> optionsItems = new ArrayList<>();
            optionsItems.add("女");
            optionsItems.add("男");
            pickerUtils.initStringPicker(optionsItems, 0, this, (position, string) -> {
                tv_sex.setText(string);
            });
            pickerUtils.mPickerView.show();
        });
        tv_birth.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            pickerUtils.initTimePicker(mActivity, mSplit, (date, patternDate) -> {
                tv_birth.setText(patternDate);
            });
            pickerUtils.timeShow();
        });
        tv_diqu.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            pickerUtils.initLocalPicker(this, list -> {
                localList = list;
                mStringMap.put("province", localList.get(0).name);
                mStringMap.put("city", localList.get(1).name);
                mStringMap.put("area", localList.get(2).name);
                mStringMap.put("area_code", localList.get(2).code);
                tv_diqu.setText(localList.get(1).name);
            });
            pickerUtils.localShow(this);
        });
        mRlAddress.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, LocalListActivity.class, "我的收货地址");
        });
        et_phone.setOnClickListener(v -> {
            et_phone.setCursorVisible(true);
        });
        mTvAddInterest.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);
            Bundle bundle = new Bundle();
            bundle.putString("selInterest", tv_biaoqian.getText().toString());
            ActivityUtils.init().start(mActivity, InterestActivity.class, "添加标签", bundle);
        });
    }

    private void openCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> CameraUtil.getInstance().openCameraWithSize(this.mActivity, 1))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
    }

    private DialogLoading mLoading;
    public boolean isZiped = true;
    private File mFile;

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(mActivity);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                mFile = BitmapUtil.compressImage(new File(strings.get(0)));
                ArrayList<File> files = new ArrayList<>();
                files.add(mFile);
                return files;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                HashMap<String, String> stringMap = new HashMap<>();
                stringMap.put("userId", WmtApplication.id);
                mPresenter.avatar(files, stringMap);
            });
        }
    }

    public void avatar(BaseBean bean) {
        if (bean.result.equals("ok")) {
            Glide.with(mActivity).load(mFile).into(im_photo);
            EventBus.getDefault().post(new AvatarEvent());
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    public void updateUserInfoDate(BaseBean bean) {
        if (bean.result.equals("ok")) {
            EventBus.getDefault().post(new RefreshUserInfoEvent());
            BGAKeyboardUtil.closeKeyboard(this);
            finish();
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }
}
