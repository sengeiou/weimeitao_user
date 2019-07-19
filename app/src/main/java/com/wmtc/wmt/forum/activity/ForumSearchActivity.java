package com.wmtc.wmt.forum.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.HotFireSearchAdapter;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.HotSearchBean;
import com.wmtc.wmt.forum.bean.SearchUserBean;
import com.wmtc.wmt.forum.presenter.ForumSearchPresenter;
import com.wmtc.wmt.forum.adapter.CommunityListAdapter;
import com.wmtc.wmt.forum.adapter.HotSearchAdapter;
import com.wmtc.wmt.forum.adapter.SearchUserAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.decoration.ItemDecoration;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/5.
 * com.wmtc.wmt.mvui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumSearchActivity extends SuperBaseActivity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.editSearch)
    TextView mEditSearch;
    @BindView(R.id.tvForum)
    TextView mTvForum;
    @BindView(R.id.tvUser)
    TextView mTvUser;
    @BindView(R.id.llHot)
    LinearLayout llHot;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.llHistory)
    LinearLayout llHistory;
    @BindView(R.id.hotFlow)
    TagFlowLayout mHotFlow;
    @BindView(R.id.ivDelHistory)
    ImageView mIvDelHistory;
    @BindView(R.id.historyFlow)
    TagFlowLayout mHistoryFlow;
    @BindView(R.id.smartRefreshLayoutForum)
    SmartRefreshLayout mSmartRefreshLayoutForum;
    @BindView(R.id.recyclerViewForum)
    RecyclerView mRecyclerViewForum;
    private Unbinder mBind;
    private HotFireSearchAdapter mHotForumAdapter;
    private HotSearchAdapter mHistoryAdapter;
    private ForumSearchPresenter mPresenter;
    private int page = 1;
    private SearchUserAdapter mUserAdapter;
    private String key = "";
    private CommunityListAdapter mForumAdapter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_search;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this);
        mPresenter = new ForumSearchPresenter(this);
        mPresenter.hotList();
        mPresenter.history();
        mIvBack.setOnClickListener(v -> finish());
        mEditSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean isOK = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!StringUtils.init().isEmpty(mEditSearch)) {
                    String string = mEditSearch.getText().toString();
                    mPresenter.addHistory(string, historyList);
                }
                key = StringUtils.init().fixNullStr(mEditSearch.getText());
                searchKey(key);
                llHot.setVisibility(View.GONE);
                llSearch.setVisibility(View.VISIBLE);
                BGAKeyboardUtil.closeKeyboard(mActivity);
                mEditSearch.clearFocus();
                isOK = true;
            }
            return isOK;
        });
        mIvDelHistory.setOnClickListener(v -> {
            mPresenter.delHistory();
        });
        mHistoryFlow.setOnTagClickListener((view1, position, parent) -> {
            String item = mHistoryAdapter.getItem(position);
            mEditSearch.setText(item);
            key = item;
            llHot.setVisibility(View.GONE);
            llSearch.setVisibility(View.VISIBLE);
            searchKey(key);
            return false;
        });
        mHotFlow.setOnTagClickListener((view1, position, parent) -> {
            String item = mHotForumAdapter.getItem(position);
            mEditSearch.setText(item);
            key = item;
            mPresenter.addHistory(item, historyList);
            llHot.setVisibility(View.GONE);
            llSearch.setVisibility(View.VISIBLE);
            searchKey(key);
            return false;
        });
        mUserAdapter = new SearchUserAdapter(null);
        mRecyclerView.setAdapter(mUserAdapter);

        mUserAdapter.setOnItemChildClickListener((adapter, view1, position) -> {

            if (view1.getId() == R.id.tvUserFollow) {
                String status;
                if (view1.isSelected()) {
                    status = "-1";
                } else {
                    status = "1";
                }
                SearchUserBean.DataBean dataBean = mUserAdapter.getData().get(position);
                dataBean.isFollow = !dataBean.isFollow;
                view1.setSelected(!dataBean.isFollow);
                mUserAdapter.notifyItemChanged(position);
                mPresenter.followShopOrUser(status, dataBean.uid);
            }
            return false;
        });
        mUserAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Bundle bundle = new Bundle();
            SearchUserBean.DataBean dataBean = mUserAdapter.getData().get(position);
            bundle.putString("id", dataBean.uid);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mTvForum.setOnClickListener(v -> {
            mTvForum.setEnabled(false);
            mTvUser.setEnabled(true);
            mPresenter.findForum(page, key,posType);
            mSmartRefreshLayout.setVisibility(View.GONE);
            mSmartRefreshLayoutForum.setVisibility(View.VISIBLE);
        });
        mTvUser.setOnClickListener(v -> {
            mTvForum.setEnabled(true);
            mTvUser.setEnabled(false);
            page = 1;
            mPresenter.searchUser(key);
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
            mSmartRefreshLayoutForum.setVisibility(View.GONE);
        });
        recyclerForum();
    }

    public void recyclerForum() {
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewForum.setLayoutManager(mLayoutManager);
        mRecyclerViewForum.addItemDecoration(new ItemDecoration(ScreenUtils.dp2px(10)));
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerViewForum.setItemAnimator(null);
        mForumAdapter = new CommunityListAdapter(null);
        mRecyclerViewForum.setAdapter(mForumAdapter);
        mForumAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ForumListBean.DataBean.RecordsBean bean = mForumAdapter.getData().get(position);
            if (view.getId() == R.id.llZan) {
                String status;
                String zanStatus = bean.zanStatus;
                int num;
                try {
                    num = Integer.parseInt(bean.dianzanNum);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    num = 1;
                }
                if (zanStatus.equals("-1")) {
                    status = "1";
                    ++num;
                } else {
                    --num;
                    status = "-1";
                }
                bean.zanStatus = status;
                bean.dianzanNum = num + "";
                mPresenter.zan(status, bean.forumId, position);
                mForumAdapter.notifyItemChanged(position + mForumAdapter.getHeaderLayoutCount());
            }
            return false;
        });
        mForumAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            ForumListBean.DataBean.RecordsBean bean = mForumAdapter.getData().get(position);
            bundle.putString("id", bean.forumId);
            LogUtil.e(bean);
            if ("4".equals(bean.forumType)) {
                bundle.putString("url", WmtApplication.url_host + bean.picture);
                bundle.putString("title", bean.content);
                bundle.putString("avatar", WmtApplication.url_host + bean.createUserAvatar);
                if (StringUtils.isNotBlank(bean.birthday) && bean.birthday.length() > 3) {
                    bundle.putString("birth", bean.birthday.substring(2, 3) + "0后");
                }
                bundle.putString("skin", bean.fuzhiName);
                bundle.putString("name", bean.createUser);
                ActivityUtils.init().start(mActivity, VideoPlayActivity.class, "浏览", bundle);
            } else {
                ActivityUtils.init().start(mActivity, ForumDetailActivity.class, "浏览", bundle);
            }
        });
        mSmartRefreshLayoutForum.setOnRefreshListener(refreshLayout -> {
            super.refreshStart();
//            page = 1;
//            key = "";
//            mEditSearch.setText("");
//            mEditSearch.clearFocus();
//            mPresenter.findForum(page, key);
        });
        mSmartRefreshLayoutForum.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.findForum(++page, key,posType);
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
//        mEditSearch.setText("");
//        mEditSearch.clearFocus();
//        page = 1;
//        key = "";
//        searchKey(key);
    }

    public void searchKey(String key) {
        if (mTvUser.isEnabled()) {
            mPresenter.findForum(page, key,posType);
        } else {
            mPresenter.searchUser(key);
        }
    }

    public void hotList(HotSearchBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            mHotForumAdapter = new HotFireSearchAdapter(bean.data);
            mHotFlow.setAdapter(mHotForumAdapter);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolBar).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    private List<String> historyList;

    public void history(List<String> beans) {
        if (beans != null && beans.size() > 0) {
            mHistoryAdapter = new HotSearchAdapter(beans);
            historyList = beans;
            mHistoryFlow.setAdapter(mHistoryAdapter);
        } else {
            llHistory.setVisibility(View.GONE);
            mHistoryFlow.setVisibility(View.GONE);
        }
    }

    public void delOk() {
        historyList.clear();
        mPresenter.history();
    }

    public void searchUserList(SearchUserBean bean) {
        if (bean.data != null) {
            mUserAdapter.setNewData(bean.data);
        }
    }

    private String posType = "1";

    public void forumList(ForumListBean bean) {

        if (bean.data != null && bean.data.records != null) {
            if (bean.data.records.size() < 1) {
                posType = "2";
            }
            if (page > 1) {
                if (bean.data.records.size() > 0) {
                    mForumAdapter.addData(bean.data.records);
                }
            } else {
                mForumAdapter.setNewData(bean.data.records);
            }
        }
    }

    public void complete() {
        mSmartRefreshLayoutForum.finishLoadMore();
        mSmartRefreshLayoutForum.finishRefresh();
    }

    public void userComplete() {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
    }

    public void zan(int position) {

    }
}
