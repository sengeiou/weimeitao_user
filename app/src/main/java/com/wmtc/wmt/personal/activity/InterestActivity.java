package com.wmtc.wmt.personal.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.DictAdapter;
import com.wmtc.wmt.appoint.adapter.DictOnlySelAdapter;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.personal.event.InterestEvent;
import com.wmtc.wmt.personal.presenter.InterestPresenter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/6/17.
 * com.wmtc.wmt.personal.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class InterestActivity extends CommonWmtActivity {
    @BindView(R.id.tvSel)
    TextView mTvSel;
    @BindView(R.id.tagFlowSel)
    TagFlowLayout mTagFlowSel;
    @BindView(R.id.tagFlowUnSel)
    TagFlowLayout mTagFlowUnSel;
    private Unbinder mBind;
    private InterestPresenter mPresenter;
    private DictAdapter mAdapter;
    private DictOnlySelAdapter mDictSelAdapter;
    private List<DictListBean.DataBean> mNetData;
    private StringBuilder mBuilder;
    private ArrayList<String> mStrings;

    @Override
    public int initAddLayout() {
        return R.layout.activity_interest;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mTvToolRight.setVisibility(View.VISIBLE);
        mPresenter = new InterestPresenter(this);
        mBind = ButterKnife.bind(this);
        mPresenter.getInterest();
        mStrings = new ArrayList<>();
        mBuilder = new StringBuilder();
        if (mBundle != null) {
            String selInterest = mBundle.getString("selInterest");
            if (selInterest != null) {
                if (selInterest.contains(",")) {
                    String[] split = selInterest.split(",");
                    Observable.fromArray(split).subscribe(s -> {
                        mStrings.add(s);
                    });
                } else {
                    mStrings.add(selInterest);
                }
            }
            mTvSel.setText(String.format(Locale.CHINA, "已选择 %d/20", mStrings.size()));
        }
        mDictSelAdapter = new DictOnlySelAdapter(mStrings);
        mTagFlowSel.setAdapter(mDictSelAdapter);
        mTagFlowUnSel.setOnSelectListener(selectPosSet -> {
            mStrings.clear();
            mBuilder.delete(0, mBuilder.length());
            Observable.fromIterable(selectPosSet).subscribe(integer -> {
                DictListBean.DataBean dataBean = mNetData.get(integer);
                mStrings.add(dataBean.name);
                mBuilder.append(dataBean.name);
                mBuilder.append(",");
            }, throwable -> {
            });
            mDictSelAdapter.notifyDataChanged();
            mTvSel.setText(String.format(Locale.CHINA, "已选择 %d/20", mStrings.size()));
        });
        mTagFlowUnSel.setOnTagClickListener((view, position, parent) -> {
            if (mStrings.size() >= 20) {
                ToastUtils.init().showInfoToast(mActivity, "最多可选20个");
                return true;
            }
            return false;
        });
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        if (mBuilder.length() < 0) {
            ToastUtils.init().showInfoToast(mActivity, "请选择兴趣标签");
            return;
        }
        String s = mBuilder.toString();
        EventBus.getDefault().post(new InterestEvent(s.substring(0, mBuilder.length() - 1)));
        finish();
    }

    @Override
    public void toolLeftBtn(View v) {
        super.toolLeftBtn(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    @SuppressLint("CheckResult")
    public void getInterest(DictListBean bean) {
        mNetData = bean.data;
        mAdapter = new DictAdapter(mNetData);
        mTagFlowUnSel.setAdapter(mAdapter);
        Set<Integer> integers = new HashSet<>();
        Observable.fromIterable(mNetData).subscribe(s -> {
            if (mStrings.contains(s.name)) {
                integers.add(mNetData.indexOf(s));
            }
        });
        mAdapter.setSelectedList(integers);
    }
}
