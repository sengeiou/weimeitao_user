package com.wmtc.wmt.personal.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.personal.activity.LocalListActivity;
import com.wmtc.wmt.personal.bean.LocalListBean;
import com.wmtc.wmt.personal.pojo.LocalPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalListPresenter extends BasePresenter<LocalListActivity> {

    private final CommonModel mModel;

    public LocalListPresenter(LocalListActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getLocalList() {
        LocalPojo pojo = new LocalPojo();
        pojo.userId = WmtApplication.id;
        mModel.getAddressList(pojo).subscribe(new DefaultCallBackObserver<LocalListBean>(this) {
            @Override
            public void responseSuccess(LocalListBean baseBean) {
                mIView.localList(baseBean);
            }

            @Override
            public void responseFail(LocalListBean baseBean) {

            }
        });
    }

    public void delAddress(String id) {
        LocalPojo pojo = new LocalPojo();
        pojo.userId = WmtApplication.id;
        pojo.addressId = id;
        mModel.delAddress(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                getLocalList();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void updateAddress(LocalPojo pojo) {
        mModel.updateAddress(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.updateAddress();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }
}
