package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.personal.fragment.PersonalFragment;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class PersonalPresenter extends BasePresenter<PersonalFragment> {

    private CommonModel commonModel;

    public PersonalPresenter(PersonalFragment iView) {
        super(iView);
        commonModel = new CommonModel(WmtServer.class);
    }

    public void getUserInfo() {
        UserPojo pojo = new UserPojo();
        pojo.setUserId(WmtApplication.id);
        commonModel.getUserInfo(pojo).subscribe(new DefaultCallBackObserver<UserInfoBean>(this) {
            @Override
            public void responseSuccess(UserInfoBean bean) {
                mIView.getUserInfo(bean);
            }

            @Override
            public void responseFail(UserInfoBean bean) {

            }
        });
    }

    public void getOrderByOne() {
        UserPojo pojo = new UserPojo();
        pojo.setUserId(WmtApplication.id);
        commonModel.getOrderByOne(pojo).subscribe(new DefaultCallBackObserver<OneOrderBean>(this) {
            @Override
            public void responseSuccess(OneOrderBean bean) {
                mIView.getOrderByOne(bean);
            }

            @Override
            public void responseFail(OneOrderBean bean) {

            }
        });
    }


    public void getRefund(RefundPojo pojo) {
        commonModel.getRefund(pojo).subscribe(new DefaultCallBackObserver<RefundBean>(new LoaddingErrorImplTip(mIView.getActivity()), this) {
            @Override
            public void responseSuccess(RefundBean bean) {
                mIView.getRefundDate(bean);
            }

            @Override
            public void responseFail(RefundBean bean) {

            }
        });
    }

    public void updateUserInfo(String content) {
        UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
        pojo.setIntroduce(content);
        pojo.setUserId(WmtApplication.id);
        commonModel.updateUserInfo(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updateUserInfo(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
