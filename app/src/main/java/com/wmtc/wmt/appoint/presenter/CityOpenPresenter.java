package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.activity.CityOpenActivity;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.pojo.CityOpenPojo;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/7/10.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CityOpenPresenter extends BasePresenter<CityOpenActivity> {

    private final CommonModel mModel;

    public CityOpenPresenter(CityOpenActivity activity) {
        super(activity);
        mModel = new CommonModel(WmtServer.class);
    }

    public void toOpenCity(String city) {
        CityOpenPojo pojo = new CityOpenPojo();
        pojo.city = city;
        mModel.toOpenCity(pojo).subscribe(new DefaultCallBackObserver<CityOpenBean>(this) {
            @Override
            public void responseSuccess(CityOpenBean bean) {
                mIView.toOpenCity(bean);
            }

            @Override
            public void responseFail(CityOpenBean bean) {

            }
        });
    }
}
