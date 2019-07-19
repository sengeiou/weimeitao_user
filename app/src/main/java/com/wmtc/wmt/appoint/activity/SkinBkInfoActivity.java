package com.wmtc.wmt.appoint.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.adapter.CheckTextAdapter;
import com.wmtc.wmt.appoint.adapter.SkinBkInfoAdapter;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;
import com.wmtc.wmt.appoint.presenter.SkinBkInfoPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;

import java.util.List;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SkinBkInfoActivity extends CommonWmtActivity {

    private SkinBkInfoPresenter mPresenter;
    private String mFid;
    private RecyclerView mRecyclerViewCheck;
    private CheckTextAdapter mAdapter;
    private SkinBkInfoAdapter mSkinBkInfoAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_skinbk_info;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mPresenter = new SkinBkInfoPresenter(this);
        mFid = mBundle.getString("fid");
        mPresenter.getListByParentId(mFid);
        mSkinBkInfoAdapter = new SkinBkInfoAdapter(null);
        mRecyclerView.setAdapter(mSkinBkInfoAdapter);
        View header = View.inflate(mActivity, R.layout.header_check_item, null);
        mSkinBkInfoAdapter.addHeaderView(header);
        mRecyclerViewCheck = header.findViewById(R.id.recyclerViewCheck);
        mRecyclerViewCheck.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mAdapter = new CheckTextAdapter(null);
        mRecyclerViewCheck.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                DictListBean.DataBean dataBean = mAdapter.getData().get(i);
                if (i == position) {
                    dataBean.isSel = true;
                } else {
                    dataBean.isSel = false;
                }
            }
            mAdapter.notifyDataSetChanged();
            DictListBean.DataBean dataBean = mAdapter.getData().get(position);
            mPresenter.getSkinBaike(mFid, dataBean.id);
        });

    }

    public void getSkinBaike(EfficacyDetailsBean bean) {
        List<EfficacyDetailsBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mSkinBkInfoAdapter.setNewData(data);
        }

    }

    public void getListByParentId(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            data.get(0).isSel = true;
            mAdapter.setNewData(data);
            mPresenter.getSkinBaike(mFid, data.get(0).id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
