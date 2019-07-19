package com.wmtc.wmt.personal.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.personal.adapter.LocalListAdapter;
import com.wmtc.wmt.personal.bean.LocalListBean;
import com.wmtc.wmt.personal.event.AddressCorUEvent;
import com.wmtc.wmt.personal.pojo.LocalPojo;
import com.wmtc.wmt.personal.presenter.LocalListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalListActivity extends CommonWmtActivity {

    private LocalListAdapter mAdapter;
    private LocalListPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_local_list;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        EventBus.getDefault().register(this);
        mAdapter = new LocalListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new LocalListPresenter(this);
        showLoading();
        mPresenter.getLocalList();

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            LocalListBean.DataBean dataBean = mAdapter.getData().get(position);
            if (view.getId() == R.id.localDel) {
                String id = dataBean.id + "";
                mPresenter.delAddress(id);

            } else if (view.getId() == R.id.localEdit) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isEdit", true);
                bundle.putParcelable("bean", dataBean);
                ActivityUtils.init().start(mActivity, LocalCreateActivity.class, "修改收货地址", bundle);
            } else if (view.getId() == R.id.checkbox) {
                if (dataBean.isDefault != 1) {
                    Gson gson = new Gson();
                    String toJson = gson.toJson(dataBean);
                    LocalPojo localPojo = gson.fromJson(toJson, LocalPojo.class);
                    localPojo.isDefault = "1";
                    localPojo.addressId = dataBean.id + "";
                    LogUtil.e(localPojo);
                    mPresenter.updateAddress(localPojo);
                } else {
                    mAdapter.notifyItemChanged(position);
                }
            }
            return false;
        });
        rootView.findViewById(R.id.btnSave).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isEdit", false);
            ActivityUtils.init().start(mActivity, LocalCreateActivity.class, "新增收货地址", bundle);
        });
    }

    @Subscribe
    public void onEvent(AddressCorUEvent event) {
        mPresenter.getLocalList();
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.getLocalList();
    }

    public void localList(LocalListBean baseBean) {
        responseSuccess();
        List<LocalListBean.DataBean> data = baseBean.data;
        if (data != null && data.size() > 0) {
            mAdapter.setNewData(data);
        } else {
            showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
    }

    public void updateAddress() {
        mPresenter.getLocalList();
    }
}
