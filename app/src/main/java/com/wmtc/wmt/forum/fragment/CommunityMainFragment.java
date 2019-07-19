package com.wmtc.wmt.forum.fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.AnimationBuilder;
import com.github.florent37.viewanimator.ViewAnimator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.activity.ForumMeActivity;
import com.wmtc.wmt.forum.activity.ForumSendActivity;
import com.wmtc.wmt.forum.activity.VideoPlayActivity;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.forum.bean.FollowListBean;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.presenter.CommunityPresenter;
import com.wmtc.wmt.appoint.activity.ActivityCustomCapture;
import com.wmtc.wmt.forum.activity.ForumDetailActivity;
import com.wmtc.wmt.forum.activity.ForumSearchActivity;
import com.wmtc.wmt.forum.adapter.CommunityListAdapter;
import com.wmtc.wmt.forum.adapter.FollowListAdapter;
import com.wmtc.wmt.forum.dialog.EditCommentDialog;
import com.wmtc.wmt.forum.dialog.NotFollowDialog;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.personal.event.AvatarEvent;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.Observable;
import me.jessyan.autosize.utils.LogUtils;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.listener.ToolBarChangeScrollListener;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.ui.decoration.ItemDecorationFiltter;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Administrator on 2019/2/20.
 */

//首页五页面 - 第一个
public class CommunityMainFragment extends SuperBaseFragment {

    @BindView(R.id.tvScans)
    TextView mTvScans;
    @BindView(R.id.tvFollows)
    TextView mTvFollows;
    @BindView(R.id.ivAvatar)
    ImageView mTvAvatar;
    @BindView(R.id.ivScan)
    ImageView mIvScan;
    @BindView(R.id.ivSend)
    ImageView mIvSend;
    @BindView(R.id.editSearch)
    TextView mEditSearch;
    @BindView(R.id.flFollows)
    FrameLayout flFollows;
    @BindView(R.id.flScan)
    FrameLayout flScan;
    @BindView(R.id.llSearchAndScan)
    LinearLayout mLlSearchAndScan;
    @BindView(R.id.recyclerViewFollow)
    RecyclerView mRecyclerViewFollow;
    @BindView(R.id.multipleStatusViewFollow)
    MultipleStatusView mMultipleStatusViewFollow;
    @BindView(R.id.smartRefreshLayoutFollow)
    SmartRefreshLayout mSmartRefreshLayoutFollow;
    Unbinder unbinder;
    private CommunityPresenter mPresenter;
    private StaggeredGridLayoutManager mLayoutManager;
    private ArrayList<ForumListBean.DataBean.RecordsBean> mData;
    private CommunityListAdapter mAdapter;
    private int page = 1;
    private int followPage = 1;
    private View mHeader;
    private BGABanner mBanner;
    private int scrollState = 0;
    private boolean isAnim = false;
    private int mScreenWidth;
    private boolean isScans = true;
    private boolean isAnimTop = false;
    private int duration = 125;
    private FollowListAdapter mFollowListAdapter;
    private int mHalfHeight;
    private EditCommentDialog mEditCommentDialog;
    private NotFollowDialog mNotFollowDialog;
    private String posType = "1";

    @Override
    public int initLayout() {
        return R.layout.activity_community_fragment;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.communityBar).init();
    }

    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        initRefreshStatusView(rootView);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = new CommunityPresenter(this);
        mPresenter.getBanner();
        showLoading();
        mPresenter.findForum(page, posType);
        mPresenter.getUserInfo();
        mIvScan.setOnClickListener(v -> {
            startCamera(ActivityCustomCapture.class);
        });
        mData = new ArrayList<>();
        mAdapter = new CommunityListAdapter(mData);
        mHeader = View.inflate(mActivity, R.layout.adapter_header_banner, null);
        initHeader();
        mAdapter.addHeaderView(mHeader);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.findForum(++page, posType);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ForumListBean.DataBean.RecordsBean bean = mAdapter.getData().get(position);
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
                mAdapter.notifyItemChanged(position + mAdapter.getHeaderLayoutCount());
            }
            return false;
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            ForumListBean.DataBean.RecordsBean bean = mAdapter.getData().get(position);
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

        mScreenWidth = ScreenUtils.getScreenWidth();
        flFollows.setTranslationX(-mScreenWidth);

        mTvScans.setOnClickListener(v -> {
            if (!isScans && !isAnimTop) {
                ViewAnimator.animate(flFollows).duration(duration).translationX(0, -mScreenWidth)
                        .andAnimate(flScan).duration(duration).translationX(mScreenWidth, 0)
                        .onStart(() -> {
                            isAnimTop = true;
                        })
                        .onStop(() -> {
                            isAnimTop = false;
                        })
                        .start();


                setBold(mTvScans, 30, R.color.black, Typeface.BOLD);
                setBold(mTvFollows, 14, R.color.color999, Typeface.NORMAL);
                isScans = true;
            }
        });

        mTvFollows.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                if (isScans && !isAnimTop) {
                    ViewAnimator.animate(flScan).duration(duration).translationX(0, mScreenWidth)
                            .andAnimate(flFollows).duration(duration).translationX(-mScreenWidth, 0)
                            .onStart(() -> {
                                isAnimTop = true;
                            })
                            .onStop(() -> {
                                isAnimTop = false;
                                mPresenter.findFollow(followPage, null);
                            })
                            .start();
                    setBold(mTvFollows, 30, R.color.black, Typeface.BOLD);
                    setBold(mTvScans, 14, R.color.color999, Typeface.NORMAL);
                    isScans = false;
                }
            }
        });

        mEditSearch.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, ForumSearchActivity.class, "");
        });

        mMultipleStatusViewFollow.showLoading();
        mPresenter.findFollow(followPage, null);
        mRecyclerViewFollow.setLayoutManager(new LinearLayoutManager(mActivity));
        mFollowListAdapter = new FollowListAdapter(null);
        mRecyclerViewFollow.setAdapter(mFollowListAdapter);
        mSmartRefreshLayoutFollow.setOnRefreshListener(refreshLayout -> {
            followPage = 1;
            mPresenter.findFollow(followPage, new ErrorResponseImplTip(mActivity));
        });
        mSmartRefreshLayoutFollow.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.findFollow(++followPage, new ErrorResponseImplTip(mActivity));
        });
        mFollowListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            FollowListBean.DataBean.RecordsBean bean = mFollowListAdapter.getData().get(position);
            bundle.putString("id", bean.forumId);
            if ("4".equals(bean.forumType)) {
                bundle.putString("url", WmtApplication.url_host + bean.picture);
                bundle.putString("title", bean.content);
                bundle.putString("avatar", WmtApplication.url_host + bean.createUserAvatar);
                if (StringUtils.isNotBlank(bean.birthday) && bean.birthday.length() > 3) {
                    bundle.putString("birth", bean.birthday.substring(2, 3) + "0后");
                }
                bundle.putString("skin", bean.fuzhi);
                bundle.putString("name", bean.createUser);
                ActivityUtils.init().start(mActivity, VideoPlayActivity.class, "浏览", bundle);
            } else {
                ActivityUtils.init().start(mActivity, ForumDetailActivity.class, "浏览", bundle);
            }
        });
        mFollowListAdapter.setFocusListener((position, view) -> {
            scrollEditWhenInput(view, position);
            commentAdd(position, view);
        });

        mFollowListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FollowListBean.DataBean.RecordsBean bean = mFollowListAdapter.getData().get(position);
            if (view.getId() == R.id.edInput) {
                scrollEditWhenInput(view, position);
            } else if (view.getId() == R.id.llInputZan) {
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
                mFollowListAdapter.notifyItemChanged(position);
            } else if (view.getId() == R.id.llInputSave) {
                String status;
                String saveStatus = bean.shoucangStatus;
                int num;
                try {
                    num = Integer.parseInt(bean.shoucangNum);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    num = 1;
                }
                if (saveStatus.equals("-1")) {
                    status = "1";
                    ++num;
                } else {
                    --num;
                    status = "-1";
                }
                bean.shoucangStatus = status;
                bean.shoucangNum = num + "";
                mPresenter.forumCollection(status, bean.forumId);
                mFollowListAdapter.notifyItemChanged(position);
            } else if (view.getId() == R.id.tvUserFollow) {
                mNotFollowDialog = new NotFollowDialog(mActivity);
                mNotFollowDialog.show(R.id.tvSure, view1 -> {
                    String status;
                    if (bean.isFollow) {
                        status = "-1";
                    } else {
                        status = "1";
                    }
                    bean.isFollow = !bean.isFollow;
                    mPresenter.followShopOrUser(status, bean.userId);
                    if (mNotFollowDialog != null && mNotFollowDialog.isShowing()) {
                        mNotFollowDialog.dismiss();
                    }
                });
            }

            return false;
        });
        mTvAvatar.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                Bundle bundle = new Bundle();
                bundle.putString("id", WmtApplication.id);
                ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
            }
        });
        mIvSend.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                ActivityUtils.init().start(mActivity, ForumSendActivity.class, "");
            }
        });
    }

    private void commentAdd(int position, View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setOnEditorActionListener((v, actionId, event) -> {
                boolean isOK = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    sendComment(editText, mFollowListAdapter.getData().get(position));
                    BGAKeyboardUtil.closeKeyboard(mActivity);
                    editText.setText("");
                    editText.clearFocus();
                    if (mEditCommentDialog != null && mEditCommentDialog.isShowing()) {
                        mEditCommentDialog.dismiss();
                    }
                    isOK = true;
                }
                return isOK;
            });

        }
    }

    private void sendComment(EditText editText, FollowListBean.DataBean.RecordsBean recordsBean) {
        if (StringUtils.init().isEmpty(editText)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入要发送的内容");
            return;
        }
        String forumId = recordsBean.forumId;
        mPresenter.commentarySave(forumId, recordsBean.forumType, StringUtils.init().fixNullStr(editText.getText()), "", "1");
    }


    @SuppressLint("CheckResult")
    private void scrollEditWhenInput(View view, int position) {
        int[] outLocation = new int[2];
        view.getLocationOnScreen(outLocation);
        LogUtil.e(outLocation);
        mHalfHeight = ScreenUtils.getScreenHeight() / 3;
        int i = outLocation[1];
        int keyboardHeight =
                (int) SharePreUtil.getData(mActivity, "keyboardHeight", mHalfHeight) - ScreenUtils.dp2px(40);
        if (i > keyboardHeight) {
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .compose(new IoMainSchedule<>())
                    .subscribe(aLong -> {
                        mRecyclerViewFollow.smoothScrollBy(0, i - keyboardHeight);
                    });
        }
        if (mFollowListAdapter.getData().size() - 1 <= position) {
            LogUtil.e("--------------");
            view.clearFocus();
            if (i > keyboardHeight) {
                mEditCommentDialog = new EditCommentDialog(mActivity)
                        .setViewData("", position)
                        .setFocusListener(this::commentAdd);
                mEditCommentDialog.show();
            }
        }
    }

    private void setBold(TextView tvScans, int i, int p, int bold) {
        tvScans.setTextSize(i);
        tvScans.setTextColor(getResources().getColor(p));
        tvScans.setTypeface(Typeface.defaultFromStyle(bold));
    }

    private void initHeader() {
        mBanner = mHeader.findViewById(R.id.banner);
        mBanner.setAdapter((BGABanner.Adapter<ImageView, CommentBannerBean.DataBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model.path)
                        .apply(GlideUtils.init().options(R.mipmap.wmt_default))
                        .into(itemView);
                itemView.setOnClickListener(v -> {
                    LogUtil.e(model.linkType);
                    WmtUtil.init().bannerType(mActivity, model.linkType, model.linkUrl);
                });
            }
        });
    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        page = 1;
        posType = "1";
        mPresenter.getBanner();
        mPresenter.findForum(page, posType);
    }

    public void followList(FollowListBean baseBean) {
        mMultipleStatusViewFollow.showContent();
        if (baseBean.data != null && baseBean.data.records != null) {
            if (followPage > 1) {
                mFollowListAdapter.addData(baseBean.data.records);
            } else {
                if (baseBean.data.records.size() > 0) {
                    mFollowListAdapter.setNewData(baseBean.data.records);
                } else {
                    mMultipleStatusViewFollow.showEmpty();
                }
            }
        }
    }

    public void forumList(ForumListBean bean) {

        if (bean.data != null && bean.data.records != null && bean.data.records.size() > 0) {
            if (page > 1) {
                mAdapter.addData(bean.data.records);
            } else {
                mAdapter.setNewData(bean.data.records);
            }
        } else {
            posType = "2";
        }
    }


    @Override
    public void initRootRecyclerView() {
        if (mRecyclerView != null) {
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            ArrayList<Integer> filtters = new ArrayList<>();
            filtters.add(0);
            mRecyclerView.addItemDecoration(new ItemDecorationFiltter(ScreenUtils.dp2px(10), filtters));
            mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.addOnScrollListener(new ToolBarChangeScrollListener() {
                @Override
                public void changeMethod(int alpha, float percent, boolean b) {
                    super.changeMethod(alpha, percent, b);
                }
            });
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    int[] first = new int[2];
                    mLayoutManager.findFirstCompletelyVisibleItemPositions(first);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                        mLayoutManager.invalidateSpanAssignments();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
//                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
//                    int columnCount = layoutManager.getSpanCount();
//                    int[] itemPositions = layoutManager.findFirstVisibleItemPositions(new int[columnCount]);
//                    LogUtil.e(dy);
//                    LogUtils.e(Arrays.toString(itemPositions));   `   111```
                    if (!isAnim && dy < 0) {
                        if (scrollState != -1) {
                            LogUtils.e("下滑" + dy);
                            startAnim(150, 0, 0, 1);
                        }
                        scrollState = -1;
                    }
                    if (!isAnim && dy > 0) {
                        if (scrollState != 1) {
                            LogUtil.e("上拉" + dy);
                            startAnim(0, 150, 1, 0);
                        }
                        scrollState = 1;
                    }

                }
            });

        }

    }

    private void startAnim(int upS, int upE, float start, float end) {
        AnimationBuilder builder = ViewAnimator.animate(mIvSend).duration(500).dp()
                .translationX(upS, upE).alpha(start, end)
                .onStart(() -> isAnim = true)
                .onStop(() -> isAnim = false);
        builder.start();
    }

    public void getBannerDate(CommentBannerBean bean) {
        mBanner.setData(bean.data, null);
    }

    private void startCamera(Class clazz) {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> ActivityUtils.init().startForResult(mActivity, clazz,
                        "扫一扫"))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();

    }

    @Subscribe
    public void onEvent(AvatarEvent avatarEvent) {
        mPresenter.getUserInfo();
    }

    @Subscribe
    public void onEvent(LoginSuccessEvent event) {
        mPresenter.getUserInfo();
        JMessageClient.login("C_" + WmtApplication.id, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.e("-------", "登录: " + i + s + "----------");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
    }

    public void zan(int position) {
//        mAdapter.notifyItemRangeChanged(position,1,UUID.randomUUID());
    }

    public void complete() {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
    }


    public void completeFollow() {
        mSmartRefreshLayoutFollow.finishRefresh();
        mSmartRefreshLayoutFollow.finishLoadMore();
    }

    public void commentSave(CommentSaveBean bean) {
        followPage = 1;
        mPresenter.findFollow(followPage, new ErrorResponseImplTip(mActivity));
    }

    public void setFollow() {
        followPage = 1;
        mPresenter.findFollow(followPage, new ErrorResponseImplTip(mActivity));
    }

    public void getUserInfo(UserInfoBean bean) {
        UserInfoBean.DataBean data = bean.data;
        if (data != null) {
            String model = WmtApplication.url_host + data.avatar;
            WmtApplication.avatar = model;
            Glide.with(mActivity)
                    .load(model)
                    .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                    .into(mTvAvatar);
            if (StringUtils.isNotBlank(data.birthday)) {
                WmtApplication.isInfo = "1";
            } else {
                WmtApplication.isInfo = "";
            }
        }
    }
}