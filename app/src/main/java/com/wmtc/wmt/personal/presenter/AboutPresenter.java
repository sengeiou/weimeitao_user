package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.activity.AboutActivity;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.pojo.VersionPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.utils.SharePreUtil;

/**
 * Created by Obl on 2019/7/16.
 * com.wmtc.wmt.personal.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AboutPresenter extends BasePresenter<AboutActivity> {

    private final CommonModel mModel;

    public AboutPresenter(AboutActivity aboutActivity) {
        super(aboutActivity);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getNewVersion() {
        VersionPojo pojo = new VersionPojo();
        pojo.app = "1";
        mModel.getNewVersion(pojo).subscribe(new DefaultCallBackObserver<VersionBean>() {

            @Override
            public void responseSuccess(VersionBean bean) {
                mIView.version(bean);
            }

            @Override
            public void responseFail(VersionBean bean) {
            }

        });
    }

}
