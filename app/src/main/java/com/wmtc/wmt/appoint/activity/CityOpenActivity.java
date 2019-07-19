package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.presenter.CityOpenPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Obl on 2019/7/10.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CityOpenActivity extends CommonWmtActivity {
    @BindView(R.id.tvWantOpenNum)
    TickerView tvWantOpenNum;
    @BindView(R.id.tvWantOpen)
    TextView mTvWantOpen;
    private Unbinder mBind;
    private CityOpenPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_city_open;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mPresenter = new CityOpenPresenter(this);
        mPresenter.toOpenCity(mBundle.getString("city"));
        tvWantOpenNum.setText("0");
        mTvWantOpen.setOnClickListener(v -> mPresenter.toOpenCity(mBundle.getString("city")));
    }

    public void toOpenCity(CityOpenBean bean) {
        tvWantOpenNum.setText(bean.data.like + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

}
