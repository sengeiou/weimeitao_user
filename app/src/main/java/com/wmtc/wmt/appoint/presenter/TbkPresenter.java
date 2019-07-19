package com.wmtc.wmt.appoint.presenter;

import com.wmtc.wmt.appoint.bean.MarketBean;
import com.wmtc.wmt.appoint.fragment.TbkMarketFragment;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.appoint.pojo.BasePojo;

import java.util.Objects;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.utils.IpGetUtil;

/**
 * Created by Obl on 2019/4/23.
 * com.wmtc.wmt.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TbkPresenter extends BasePresenter<TbkMarketFragment> {

    private final CommonModel mModel;

    public TbkPresenter(TbkMarketFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getGoodsList() {
        BasePojo pojo = new BasePojo();
        pojo.ip = IpGetUtil.getIPAddress(Objects.requireNonNull(mIView.getActivity()));
        mModel.getGoodList(pojo).subscribe(new DefaultCallBackObserver<MarketBean>() {
            @Override
            public void responseSuccess(MarketBean marketBean) {
                mIView.getGoodsList(marketBean);
            }

            @Override
            public void responseFail(MarketBean marketBean) {

            }
        });
    }
}
