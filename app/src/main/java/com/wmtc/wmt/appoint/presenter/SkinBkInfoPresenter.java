package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.SkinBkInfoActivity;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;
import com.wmtc.wmt.appoint.pojo.EfficacyPojo;
import com.wmtc.wmt.appoint.pojo.WentiPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinBkInfoPresenter extends BasePresenter<SkinBkInfoActivity> {

    private final CommonModel mModel;

    public SkinBkInfoPresenter(SkinBkInfoActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getSkinBaike(String fid, String qid) {
        EfficacyPojo pojo = new EfficacyPojo();
        pojo.setFunctionId(fid);
        pojo.setQuestionId(qid);
        mModel.getSkinBaike(pojo).subscribe(new DefaultCallBackObserver<EfficacyDetailsBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(EfficacyDetailsBean bean) {
                mIView.getSkinBaike(bean);
            }

            @Override
            public void responseFail(EfficacyDetailsBean bean) {

            }
        });
    }

    public void getListByParentId(String pid) {
        WentiPojo pojo = new WentiPojo();
        pojo.setPid(pid);
        mModel.getListByParentId(pojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getListByParentId(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }
}
