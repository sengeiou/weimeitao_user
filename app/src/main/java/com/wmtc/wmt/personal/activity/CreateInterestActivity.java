package com.wmtc.wmt.personal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.DictAdapter;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.pojo.SetUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.personal.presenter.CreateInterestPresenter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/6/18.
 * com.wmtc.wmt.personal.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateInterestActivity extends SuperBaseActivity {

    @BindView(R.id.tagFlowUnSel)
    TagFlowLayout mTagFlowUnSel;
    @BindView(R.id.btnMain)
    TextView mBtnMain;
    private CreateInterestPresenter mPresenter;
    private Unbinder mUnbinder;
    private DictAdapter mAdapter;
    private StringBuilder mBuilder;
    private List<DictListBean.DataBean> mNetData;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_create_interest;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.statusBar).keyboardEnable(true).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mUnbinder = ButterKnife.bind(this);
        mBuilder = new StringBuilder();
        mPresenter = new CreateInterestPresenter(this);
        mPresenter.getInterest();
        mTagFlowUnSel.setOnSelectListener(selectPosSet -> {
            mBuilder.delete(0, mBuilder.length());
            Observable.fromIterable(selectPosSet).subscribe(integer -> {
                DictListBean.DataBean dataBean = mNetData.get(integer);
                mBuilder.append(dataBean.name);
                mBuilder.append(",");
            }, throwable -> {
            });
            mBtnMain.setText(String.format(Locale.CHINA, "进入主页 %d/20", selectPosSet.size()));
        });
        mBtnMain.setOnClickListener(v -> {
            Set<Integer> selectedList = mTagFlowUnSel.getSelectedList();
            if (selectedList.size() > 2) {
                UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
                pojo.setInterestName(mBuilder.toString().substring(0, mBuilder.length() - 1));
                pojo.setUserId(WmtApplication.id);
                mPresenter.updateUserInfo(pojo);
            } else {
                ToastUtils.init().showInfoToast(mActivity, "至少选择三个兴趣标签");
            }
        });
        findViewById(R.id.tvSkip).setOnClickListener(v -> {
            finish();
        });
    }

    public void getInterest(DictListBean bean) {
        mNetData = bean.data;
        mAdapter = new DictAdapter(mNetData);
        mTagFlowUnSel.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mUnbinder.unbind();
    }


    public void updateUserInfoDate(BaseBean bean) {
        finish();
    }
}
