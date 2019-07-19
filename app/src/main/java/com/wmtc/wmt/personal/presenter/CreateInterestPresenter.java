package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.pojo.SetUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.personal.activity.CreateInterestActivity;
import com.wmtc.wmt.personal.activity.InterestActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/6/18.
 * com.wmtc.wmt.personal.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateInterestPresenter extends BasePresenter<CreateInterestActivity> {
    private final CommonModel mModel;

    public CreateInterestPresenter(CreateInterestActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getInterest() {
        DictCodePojo pojo = new DictCodePojo();
        pojo.setCode("interest_name");
        mModel.getDict(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getInterest(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }


    //提交修改信息无头像
    public void updateUserInfo(UpdateUserInfoPojo Pojo) {
        mModel.updateUserInfo(Pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
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
