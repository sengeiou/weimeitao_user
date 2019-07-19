package com.wmtc.wmt.appoint.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.MarketBean;
import com.wmtc.wmt.appoint.presenter.TbkPresenter;
import com.wmtc.wmt.forum.adapter.TabAdapter;

import java.util.HashMap;
import java.util.Map;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;

/**
 * Created by Administrator on 2019/2/20.
 */

//首页五页面 - 第三个
public class TbkMarketFragment extends SuperBaseFragment {


    //适配器
    TabAdapter mAdapter;
    private TbkPresenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayout() {
        return R.layout.activity_third_fragment;
    }

    @Override
    protected void initData(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        initRefreshStatusView(rootView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new TabAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new TbkPresenter(this);
        showLoading();
        mPresenter.getGoodsList();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tbk_item) {
                tbkActivity(mAdapter.getData().get(position).getItem_url());
            }

            return false;
        });
    }

    public void tbkActivity(String taokeUrl) {
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        //实例化URL打开page
        AlibcBasePage page = new AlibcPage(taokeUrl);

        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(mActivity, page, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });

    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.getGoodsList();
    }

    public void getGoodsList(MarketBean bean) {
        responseSuccess();
        MarketBean.DataBean data = bean.getData();
        MarketBean.DataBean.TbkItemInfoGetResponseBean itemInfo = data.getTbk_item_info_get_response();
        MarketBean.DataBean.TbkItemInfoGetResponseBean.ResultsBean results = itemInfo.getResults();
        mAdapter.setNewData(results.getN_tbk_item());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
    }
}
