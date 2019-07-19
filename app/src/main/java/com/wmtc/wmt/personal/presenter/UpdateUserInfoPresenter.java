package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.personal.activity.SetPersonalMessageActivity;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class UpdateUserInfoPresenter extends BasePresenter<SetPersonalMessageActivity> {

    private CommonModel commonModel;

    public UpdateUserInfoPresenter(SetPersonalMessageActivity iView) {
        super(iView);
        commonModel = new CommonModel(WmtServer.class);
    }

    public void getUserInfo() {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserId(WmtApplication.id);
        commonModel.getUserInfo(userPojo).subscribe(new DefaultCallBackObserver<UserInfoBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(UserInfoBean bean) {
                mIView.getUserInfo(bean);
            }

            @Override
            public void responseFail(UserInfoBean bean) {

            }
        });
    }

    //获取肤质标签
    public void getSkinDict(DictCodePojo Pojo) {
        commonModel.getSkinDict(Pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getSkinDictDate(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }

    //获兴趣标签
    public void getxingqu(DictCodePojo Pojo) {
        commonModel.getxingqu(Pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getxingquDate(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }

    //上传头像
    public void avatar(List<File> fileList, Map<String, String> stringMap) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();

        for (String key : stringMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    stringMap.get(key) == null ? "" : stringMap.get(key));
            mBodyHashMap.put(key, requestBody);
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            mBodyHashMap.put("avatar\"; filename=\"" + file.getName(), fileBody);
        }
        commonModel.avatar(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.avatar(baseBean);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    //提交修改信息无头像
    public void updateUserInfo(UpdateUserInfoPojo Pojo) {
        commonModel.updateUserInfo(Pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updateUserInfoDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
