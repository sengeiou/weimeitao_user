package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.SelectTeachActivity;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.appoint.pojo.ShopTeachPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class TeachSelPresenter extends BasePresenter<SelectTeachActivity> {

    private final CommonModel mModel;

    public TeachSelPresenter(SelectTeachActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void selTeach(String shopId, String arrivalTime) {
        ShopTeachPojo shopTeachPojo = new ShopTeachPojo();
        if (StringUtils.isNotBlank(arrivalTime)) {
            shopTeachPojo.arrivalTime = arrivalTime;
        }
        shopTeachPojo.shopId = shopId;
        mModel.shopTeach(shopTeachPojo).subscribe(new DefaultCallBackObserver<TeachSelBean>(this) {
            @Override
            public void responseSuccess(TeachSelBean teachSelBean) {
                mIView.selTeach(teachSelBean);
            }

            @Override
            public void responseFail(TeachSelBean teachSelBean) {

            }
        });
    }
}
