package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.event.EditEvent;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.wmtc.wmt.personal.event.AvatarEvent;
import com.wmtc.wmt.personal.event.RefreshUserInfoEvent;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.forum.adapter.ForumMeListAdapter;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.MeForumCountBean;
import com.wmtc.wmt.forum.dialog.ForumMoreDialog;
import com.wmtc.wmt.forum.dialog.ZanFansDialog;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.forum.bean.ForumMeBean;
import com.wmtc.wmt.forum.bean.TabLayoutBean;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.forum.presenter.ForumMePresenter;
import com.wmtc.wmt.personal.activity.SetPersonalMessageActivity;
import com.wmtc.wmt.personal.dialog.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.ui.decoration.ItemDecoration;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/5/9.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumMeActivity extends CommonWmtActivity {
    @BindView(R.id.tvMeName)
    TextView mTvMeName;
    @BindView(R.id.ivMeAvatar)
    ImageView mIvMeAvatar;
    @BindView(R.id.ivSex)
    ImageView mIvSex;
    @BindView(R.id.ivBirth)
    TextView mIvBirth;
    @BindView(R.id.ivSkin)
    TextView mIvSkin;
    @BindView(R.id.ivLocal)
    TextView mIvLocal;
    @BindView(R.id.llMeDetail)
    LinearLayout mLlMeDetail;
    @BindView(R.id.tvEdit)
    TextView mTvEdit;
    @BindView(R.id.edDetail)
    EditText mEdDetail;
    @BindView(R.id.tvFollow)
    TextView mTvFollow;
    @BindView(R.id.tvFans)
    TextView mTvFans;
    @BindView(R.id.tvZanSave)
    TextView mTvZanSave;
    @BindView(R.id.llZanFans)
    LinearLayout mLlZanFans;
    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.tabLayoutChild)
    CommonTabLayout mTabLayoutChild;
    private Unbinder mBind;
    private ArrayList<CustomTabEntity> mTabEntitysChild;
    private ForumMePresenter mPresenter;

    private UserInfoBean.DataBean mDataBean;
    private int page = 1;
    private ForumMeListAdapter mAdapter;
    private boolean isSeling = false;
    public String forumType = "1";
    public String operationType = "content";
    private StaggeredGridLayoutManager mLayoutManager;
    private View mHeader;
    private String mUid;
    private boolean isMe = true;

    @Override
    public int initAddLayout() {
        return R.layout.forum_me;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mHeader = View.inflate(mActivity, R.layout.header_forum_me, null);
        mBind = ButterKnife.bind(this, mHeader);
        mHeader.findViewById(R.id.ivAddOne).setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, ForumSearchActivity.class, "");
        });
        EventBus.getDefault().register(this);
        mIvShare.setVisibility(View.VISIBLE);

        mPresenter = new ForumMePresenter(this);
        initRootRecyclerView();
        mUid = mBundle.getString("id");
        if (!WmtApplication.id.equals(mUid)) {
            isMe = false;
        }

        mEdDetail.setEnabled(isMe);
        mTvEdit.setText(isMe ? "编辑资料" : "私信");
        mPresenter.zanfansNum(mUid);
        mPresenter.getUserInfo(mUid);
        mPresenter.socialHomeForumList(forumType, operationType, page, mUid);
        mIvShare.setOnClickListener(v -> {
            new ShareDialog(this)
                    .setUrl(BuildConfig.HOST + "h5/index.html#/pages/index/register?type=1&uid=" + WmtApplication.id)
                    .show();
        });
        tabParent();

        mTvEdit.setOnClickListener(v -> {
            if (isMe) {
                Bundle bundle = new Bundle();
                ActivityUtils.init().start(mActivity, SetPersonalMessageActivity.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("name", "C_" + mUid);
                ActivityUtils.init().start(mActivity, CustomChatActivity.class, mTvMeName.getText().toString(), bundle);
            }
        });

        mIvMeAvatar.setOnClickListener(v -> {
            if (isMe) {
                Bundle bundle = new Bundle();
                ActivityUtils.init().start(mActivity, SetPersonalMessageActivity.class, bundle);
            }
        });
        mTabEntitysChild = new ArrayList<>();
        tabChild();
        mPresenter.findFourmCount("content", 0, mUid);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onTabSelect(int position) {
                isSeling = true;
                String type = "content";
                if (position == 1) {
                    type = "collection";
                } else if (position == 2) {
                    type = "zan";
                } else if (position == 3) {
                    type = "draft";
                } else if (position == 4) {
                    type = "pending";
                }
                operationType = type;
                page = 1;
                mTabLayoutChild.setCurrentTab(0);
                forumType = "1";
                mPresenter.findFourmCount(type, position, mUid);
                mPresenter.socialHomeForumList("1", operationType, page, mUid);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mTvZanSave.setOnClickListener(v -> {
            new ZanFansDialog(mActivity)
                    .setContent(StringUtils.init().fixNullStr("'" + mTvMeName.getText() + "'"),
                            "共" + mTvZanSave.getText()).show();
        });

        mEdDetail.setOnEditorActionListener((v, actionId, event) -> {
            boolean isOK = false;
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                BGAKeyboardUtil.closeKeyboard(mActivity);
                mEdDetail.clearFocus();
                String string = mEdDetail.getText().toString();
                if (StringUtils.isNotBlank(string)) {
                    mPresenter.updateUserInfo(string);
                }
                isOK = true;
            }
            return isOK;
        });

        mTvFans.setOnClickListener(v -> {
            if (mDataBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mDataBean.id);
                ActivityUtils.init().start(mActivity, FansActivity.class, "我的粉丝", bundle);
            }
        });
        mTvFollow.setOnClickListener(v -> {
            if (mDataBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mDataBean.id);
                ActivityUtils.init().start(mActivity, FollowActivity.class, "关注列表", bundle);
            }
        });
        mAdapter = new ForumMeListAdapter(null, this);
        mAdapter.addHeaderView(mHeader);
        mRecyclerView.setAdapter(mAdapter);

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
            } else if (view.getId() == R.id.ivDelForum) {
                ForumMoreDialog forumMoreDialog = new ForumMoreDialog(this);
                forumMoreDialog.show(R.id.tvDel, dialogView -> {
                    TabLayoutBean customTabEntity = (TabLayoutBean) mTabEntitysChild.get(mTabLayoutChild.getCurrentTab());
                    String tabTitle = customTabEntity.getTabTitle();
                    if (tabTitle.contains("·")) {
                        String[] split = tabTitle.split("·");
                        String num = split[1];
                        int i = StringUtils.init().fixNullInt(num);
                        customTabEntity.title = split[0] + "·" + (--i);
                        mTabLayoutChild.setTabData(mTabEntitysChild);
                        mTabLayoutChild.notifyDataSetChanged();
                    }
                    mPresenter.del(bean.forumId);
                    mAdapter.remove(position);
                    forumMoreDialog.dismiss();
                });
            }
            return false;
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            ForumListBean.DataBean.RecordsBean bean = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.forumId);
            if ("4".equals(bean.forumType)) {
                bundle.putString("url", WmtApplication.url_host + bean.picture);
                bundle.putString("title", bean.content);
                bundle.putString("avatar", WmtApplication.url_host + bean.createUserAvatar);
                bundle.putString("birth", mIvBirth.getText().toString());
                bundle.putString("skin", mIvSkin.getText().toString());
                bundle.putString("name", mIvToolLeft.getText().toString());
                ActivityUtils.init().start(mActivity, VideoPlayActivity.class, "浏览", bundle);
            } else {
                ActivityUtils.init().start(mActivity, ForumDetailActivity.class, "浏览", bundle);
            }
        });
        mTabLayoutChild.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                forumType = "1";
                if (position == 1) {
                    forumType = 2 + "";
                } else if (position == 2) {
                    forumType = 3 + "";
                } else if (position == 3) {
                    forumType = 4 + "";
                }
                page = 1;
                mPresenter.socialHomeForumList(forumType, operationType, page, mUid);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.socialHomeForumList(forumType, operationType, ++page, mUid);
        });
    }

    protected void initRootRecyclerView() {
        if (mRecyclerView != null) {
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new ItemDecoration(ScreenUtils.dp2px(10)));
            mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            mRecyclerView.setItemAnimator(null);
        }
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        page = 1;
        mPresenter.getUserInfo(mUid);
        mPresenter.socialHomeForumList(forumType, operationType, page, mUid);
    }

    @Subscribe
    public void onEvent(EditEvent editEvent) {
        mPresenter.socialHomeForumList(forumType, operationType, page, mUid);
    }

    public void socialHomeForumList(ForumListBean listBean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        List<ForumListBean.DataBean.RecordsBean> records = listBean.data.records;
        if (records != null) {
            if (page == 1) {
                mAdapter.setNewData(records);
            } else {
                if (records.size() > 0) {
                    mAdapter.addData(records);
                }
            }
        }
    }


    public void getUserInfo(UserInfoBean bean) {
        mDataBean = bean.data;
        if (mDataBean != null) {
            Glide.with(mActivity)
                    .load(WmtApplication.url_host + mDataBean.avatar)
                    .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                    .into(mIvMeAvatar);
            mEdDetail.setText(mDataBean.introduce);
            mIvSex.setImageDrawable(getResources().getDrawable("1".equals(mDataBean.sex) ?
                    R.drawable.boy : R.drawable.girl));
            if (StringUtils.isNotBlank(mDataBean.birthday) && mDataBean.birthday.length() > 3) {
                String birth = mDataBean.birthday.substring(2, 3) + "0后";
                mIvBirth.setText(birth);
            }
            mIvSkin.setText(StringUtils.init().fixNullStr(mDataBean.userFuzhiName));
            mIvLocal.setText(StringUtils.init().fixNullStr(mDataBean.loginCityName));
            mTvMeName.setText(StringUtils.init().fixNullStr(mDataBean.name));
            mIvToolLeft.setText(StringUtils.init().fixNullStr(mDataBean.name));
            if (StringUtils.isNotBlank(mDataBean.birthday)) {
                WmtApplication.isInfo = "1";
            } else {
                WmtApplication.isInfo = "";
            }
        }
    }

    public void zanFansNum(ForumMeBean forumMeBean) {
        ForumMeBean.DataBean data = forumMeBean.data;
        if (data != null) {
            mTvFollow.setText(String.format(Locale.CHINA, "%s", StringUtils.init().fixNullStr(data.attentionCount)));
            mTvFans.setText(String.format(Locale.CHINA, "%s", StringUtils.init().fixNullStr(data.fensCount)));
            int zan = StringUtils.init().fixNullInt(data.zanCount);
            int save = StringUtils.init().fixNullInt(data.collectionCount);
            mTvZanSave.setText(String.format(Locale.CHINA, "%d", zan + save));
        }
    }

    private void tabChild() {
        mTabEntitysChild.add(new TabLayoutBean("心得"));
        mTabEntitysChild.add(new TabLayoutBean("合辑"));
        mTabEntitysChild.add(new TabLayoutBean("视频"));
        mTabEntitysChild.add(new TabLayoutBean("日记"));
        mTabLayoutChild.setTabData(mTabEntitysChild);
    }

    private void tabParent() {
        ArrayList<CustomTabEntity> tabEntitys = new ArrayList<>();
        tabEntitys.add(new TabLayoutBean("内容"));
        if (isMe) {
            tabEntitys.add(new TabLayoutBean("收藏"));
        }
        tabEntitys.add(new TabLayoutBean("赞过"));
        if (isMe) {
            tabEntitys.add(new TabLayoutBean("草稿"));
            tabEntitys.add(new TabLayoutBean("审核 "));
        }
        mTabLayout.setTabData(tabEntitys);
    }

    public void forumCountBean(MeForumCountBean bean) {
        List<MeForumCountBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            mTabEntitysChild.clear();
            for (MeForumCountBean.DataBean datum : data) {
                String type = datum.type;
                String title = "心得·";
                if ("2".equals(type)) {
                    title = "合辑·";
                } else if ("3".equals(type)) {
                    title = "日记·";
                } else if ("4".equals(type)) {
                    title = "视频·";
                } else if ("99".equals(type)) {
                    title = "审核·";
                    continue;
                }
                mTabEntitysChild.add(new TabLayoutBean(title + datum.count));
            }
            mTabLayoutChild.setTabData(mTabEntitysChild);
        }
    }


    @Subscribe
    public void onEvent(RefreshUserInfoEvent event) {
        mPresenter.getUserInfo(mUid);
    }

    @Subscribe
    public void onEvent(AvatarEvent event) {
        mPresenter.getUserInfo(mUid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void updateUserInfo(BaseBean bean) {

    }

    public void setTabComplete() {
        isSeling = false;
    }

    public void zan(int position) {

    }

    public void del() {

    }
}
