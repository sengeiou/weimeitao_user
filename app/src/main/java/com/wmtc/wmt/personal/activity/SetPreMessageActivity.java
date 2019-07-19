package com.wmtc.wmt.personal.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.MainActivity;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.forum.pojo.SetUserInfoPojo;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.presenter.SetUserInfoPresenter;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

public class SetPreMessageActivity extends SuperBaseActivity {

    private SetUserInfoPresenter mPresenter;
    private PolygonImageView user_photo;
    private EditText et_name;
    private TextView tv_sex;
    private TextView tv_birth;
    private TextView tv_fuzhi;
    private TextView tv_diqu;
    private TextView date_submit;
    private ArrayList<String> skinList = new ArrayList<>();
    private ArrayList<String> skinIdList = new ArrayList<>();
    private PickerUtils pickerUtils;
    private String[] mSplit = new String[]{};
    private List<PickerUtils.SelectLocalBean> localList = new ArrayList<>();
    Map<String, String> mStringMap = new HashMap<>();
    private String msex = "";
    private String fid;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_set_message;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new SetUserInfoPresenter(this);
        pickerUtils = new PickerUtils();
        user_photo = findViewById(R.id.user_photo);
        et_name = findViewById(R.id.et_name);
        et_name.setCursorVisible(false);
        tv_sex = findViewById(R.id.tv_sex);
        tv_birth = findViewById(R.id.tv_birth);
        tv_fuzhi = findViewById(R.id.tv_fuzhi);
        tv_diqu = findViewById(R.id.tv_diqu);
        date_submit = findViewById(R.id.date_submit);
        initClick();
        getSkinDict();
    }

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
                skinIdList.add(bean.data.get(i).id);
            }
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    private void initClick() {
        findViewById(R.id.rl_back).setOnClickListener(v -> finish());

        user_photo.setOnClickListener(v -> {
            openCamera();
        });
        et_name.setOnClickListener(v -> {
            et_name.setCursorVisible(true);
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
        tv_fuzhi.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            pickerUtils.initStringPicker(skinList, 0, this, (position, string) -> {
                tv_fuzhi.setText(string);
                fid = skinIdList.get(position);
            });
            pickerUtils.mPickerView.show();
        });
        tv_diqu.setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(this);

            pickerUtils.initLocalPicker(this, list -> {
                localList = list;
                mStringMap.put("province", localList.get(0).name);
                mStringMap.put("city", localList.get(1).name);
                mStringMap.put("area", localList.get(2).name);
                mStringMap.put("area_code", localList.get(2).code);
                StringBuilder stringBuilder = new StringBuilder();
                for (PickerUtils.SelectLocalBean bean : list) {
                    stringBuilder.append(bean.name);
                }
                tv_diqu.setText(localList.get(2).name);
            });
            pickerUtils.localShow(this);
        });
        date_submit.setOnClickListener(v -> {
            if (tv_sex.getText().toString().equals("男")) {
                msex = "1";
            } else {
                msex = "2";
            }
            if (!"".equals(et_name.getText().toString()) &&
                    !"".equals(tv_fuzhi.getText().toString()) &&
                    !"".equals(msex) &&
                    !"".equals(tv_birth.getText().toString()) &&
                    !"".equals(tv_diqu.getText().toString())) {
                SetUserInfoPojo pojo = new SetUserInfoPojo();
                pojo.setUserId(WmtApplication.id);
                pojo.setJiguangId("171976fa8acf3f23edc");
                pojo.setNickName(et_name.getText().toString());
                pojo.setUserFuzhiName(tv_fuzhi.getText().toString());
                pojo.setUserFuzhiId(fid);
                pojo.setSex(msex);
                pojo.setBirthday(tv_birth.getText().toString());
                pojo.setLoginCityName(tv_diqu.getText().toString());
                mPresenter.setUserInfo(pojo);
            } else {
                ToastUtils.init().showInfoToast(mActivity, "请填写完整信息");
            }
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
            Glide.with(mActivity).load(mFile).into(user_photo);
        } else {
            ToastUtils.init().showInfoToast(mActivity, bean.message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public void setUserInfoDate(BaseBean bean) {
        ActivityUtils.init().start(mActivity, CreateInterestActivity.class);
        EventBus.getDefault().post(new LoginSuccessEvent());
        finish();
    }

}
