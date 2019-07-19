package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.PreAppointOrderActivity;
import com.wmtc.wmt.appoint.bean.PreOrderBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.bean.TimeSelBean;
import com.wmtc.wmt.appoint.pojo.PreOrderPojo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.appoint.pojo.ShopTeachPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProAppointOrderPresenter extends BasePresenter<PreAppointOrderActivity> {

    private final CommonModel mModel;

    public ProAppointOrderPresenter(PreAppointOrderActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void proInfo(String proId) {
        ShopInfoPojo infoPojo = new ShopInfoPojo();
        infoPojo.proId = proId;
        mModel.proInfo(infoPojo).subscribe(new DefaultCallBackObserver<ProjectInfoBean>(new LoaddingErrorImplTip(mIView),
                this) {
            @Override
            public void responseSuccess(ProjectInfoBean proInfoBean) {
                mIView.proInfo(proInfoBean);
            }

            @Override
            public void responseFail(ProjectInfoBean proInfoBean) {

            }
        });
    }

    public void preOrder(PreOrderPojo pojo) {
        mModel.preOrder(pojo).subscribe(new DefaultCallBackObserver<PreOrderBean>(new LoaddingErrorImplTip(mIView),
                this) {
            @Override
            public void responseSuccess(PreOrderBean preOrderBean) {
                mIView.preOrder(preOrderBean);
            }

            @Override
            public void responseFail(PreOrderBean preOrderBean) {

            }
        });
    }

    public void timeSel(ShopTeachPojo pojo) {
        mModel.timeSel(pojo).subscribe(new DefaultCallBackObserver<TimeSelBean>(new LoaddingErrorImplTip(mIView), this) {
            @Override
            public void responseSuccess(TimeSelBean timeSelBean) {
                mIView.timeSel(timeSelBean);
            }

            @Override
            public void responseFail(TimeSelBean timeSelBean) {

            }
        });
    }
}
