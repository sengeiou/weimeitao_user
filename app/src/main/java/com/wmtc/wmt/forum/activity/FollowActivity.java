package com.wmtc.wmt.forum.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.ShopInfoActivity;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.forum.adapter.FollowsListAdapter;
import com.wmtc.wmt.forum.adapter.ShopFollowAdapter;
import com.wmtc.wmt.forum.bean.FansListBean;
import com.wmtc.wmt.forum.bean.ShopFollowBean;
import com.wmtc.wmt.forum.bean.TabLayoutBean;
import com.wmtc.wmt.forum.presenter.ForumFollowListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FollowActivity extends CommonWmtActivity {
    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.recyclerViewShop)
    RecyclerView mRecyclerViewShop;
    @BindView(R.id.multipleStatusViewShop)
    MultipleStatusView mMultipleStatusViewShop;
    @BindView(R.id.smartRefreshLayoutShop)
    SmartRefreshLayout mSmartRefreshLayoutShop;
    private Unbinder mBind;
    private FollowsListAdapter mUserAdapter;
    private ForumFollowListPresenter mPresenter;
    private String mId;
    private ShopFollowAdapter mShopAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_follows;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mPresenter = new ForumFollowListPresenter(this);
        ArrayList<CustomTabEntity> tabEntitys = new ArrayList<>();
        tabEntitys.add(new TabLayoutBean("用户"));
        tabEntitys.add(new TabLayoutBean("商家"));
        mTabLayout.setTabData(tabEntitys);
        mUserAdapter = new FollowsListAdapter(null);
        mRecyclerView.setAdapter(mUserAdapter);
        mRecyclerViewShop.setLayoutManager(new LinearLayoutManager(this));
        showLoading();
        mId = mBundle.getString("id");
        mPresenter.attentionUserList(mId);
        mPresenter.attentionShopList(mId);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    mSmartRefreshLayoutShop.setVisibility(View.GONE);
                    mSmartRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    mSmartRefreshLayoutShop.setVisibility(View.VISIBLE);
                    mSmartRefreshLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mUserAdapter.setOnItemChildClickListener((adapter, view1, position) -> {

            if (view1.getId() == R.id.tvUserFollow) {
                String status;
                if (view1.isSelected()) {
                    status = "-1";
                } else {
                    status = "1";
                }
                FansListBean.DataBean dataBean = mUserAdapter.getData().get(position);
                dataBean.attntionStatus = status;
                view1.setSelected(!view1.isSelected());
                mUserAdapter.notifyItemChanged(position);
                mPresenter.followShopOrUser(status, dataBean.userId);
            }
            return false;
        });
        mUserAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            FansListBean.DataBean dataBean = mUserAdapter.getData().get(position);
            bundle.putString("id", dataBean.userId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mSmartRefreshLayoutShop.setOnRefreshListener(refreshLayout -> {
            mPresenter.attentionShopList(mId);
        });

        mShopAdapter = new ShopFollowAdapter(null);
        mShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            ShopFollowBean.DataBean dataBean = mShopAdapter.getData().get(position);
            bundle.putString("id", dataBean.shopId);
            bundle.putParcelable("pojo", new Bundle());
            ActivityUtils.init().start(mActivity, ShopInfoActivity.class, "", bundle);
        });
        mRecyclerViewShop.setAdapter(mShopAdapter);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.attentionUserList(mId);
    }

    public void attentionShopList(ShopFollowBean bean) {
        mSmartRefreshLayoutShop.finishRefresh();
        mMultipleStatusViewShop.showContent();
        List<ShopFollowBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mShopAdapter.setNewData(data);
        } else {
            mMultipleStatusViewShop.showEmpty();
        }
    }

    public void attentionUserList(FansListBean fansListBean) {
        responseSuccess();
        List<FansListBean.DataBean> data = fansListBean.data;
        if (data != null && data.size() > 0) {
            mUserAdapter.setNewData(data);
        } else {
            showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

}
