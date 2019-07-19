package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.ProjectInfoActivity;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.pojo.OrderCustomPojo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/22.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProInfoPresenter extends BasePresenter<ProjectInfoActivity> {

    private final CommonModel mModel;

    public ProInfoPresenter(ProjectInfoActivity iView) {
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
    public void getOrderJudge(String id) {
        ShopInfoPojo pojo = new ShopInfoPojo();
        pojo.userId = WmtApplication.id;
        pojo.currentPage = "1";
        pojo.projectId = id;
        mModel.getOrderJudge(pojo).subscribe(new DefaultCallBackObserver<OrderJudgeBean>(this) {
            @Override
            public void responseSuccess(OrderJudgeBean orderJudgeBean) {
                mIView.getOrderJudge(orderJudgeBean);
            }

            @Override
            public void responseFail(OrderJudgeBean shopInfoBean) {

            }
        });
    }

    public void getCustomerService(String id){
        OrderCustomPojo orderPojo = new OrderCustomPojo();
        orderPojo.shopId = id;
        orderPojo.userType = "1";
        mModel.getCustomerService(orderPojo).subscribe(new DefaultCallBackObserver<CustomChatBean>() {
            @Override
            public void responseSuccess(CustomChatBean customChatBean) {
                mIView.chatBean(customChatBean);
            }

            @Override
            public void responseFail(CustomChatBean customChatBean) {

            }
        });
    }

}
