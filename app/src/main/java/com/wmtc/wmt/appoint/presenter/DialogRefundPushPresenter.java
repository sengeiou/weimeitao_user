package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.dialog.DialogCancelPush;
import com.wmtc.wmt.appoint.dialog.DialogRefundPush;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DialogRefundPushPresenter extends BasePresenter<DialogRefundPush> {

    private final CommonModel mModel;

    public DialogRefundPushPresenter(DialogRefundPush iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getDict(String dict) {
        DictCodePojo dictCodePojo = new DictCodePojo();
        dictCodePojo.setCode(dict);
        mModel.getxingqu(dictCodePojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getList(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }

}
