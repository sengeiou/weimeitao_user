package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.AnimationBuilder;
import com.github.florent37.viewanimator.ViewAnimator;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.bean.ForumDetailBean;
import com.wmtc.wmt.forum.event.EditEvent;
import com.wmtc.wmt.forum.event.SharePicEvent;
import com.wmtc.wmt.forum.event.ShareReportEvent;
import com.wmtc.wmt.forum.presenter.ForumDetailPresenter;
import com.wmtc.wmt.forum.adapter.ForumDetailAdapter;
import com.wmtc.wmt.forum.adapter.HotForumAdapter;
import com.wmtc.wmt.personal.dialog.ShareHaveReportDialog;
import com.wmtc.wmt.personal.event.ShareDelEvent;
import com.wmtc.wmt.personal.event.ShareEditEvent;
import com.wmtc.wmt.wxapi.WXShare;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.richeditor.RichEditor;
import me.jessyan.autosize.utils.LogUtils;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.listener.RecyclerViewChangeScrollListener;
import top.jplayer.baseprolibrary.listener.ToolBarChangeScrollListener;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.net.tip.ErrorResponseImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SizeUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumDetailActivity extends SuperBaseActivity {

    @BindView(R.id.ivAvatar)
    PolygonImageView mIvAvatar;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    @BindView(R.id.tvSkin)
    TextView mTvSkin;
    @BindView(R.id.tvFollow)
    TextView mTvFollow;
    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.tvContent)
    TextView mTvContent;
    @BindView(R.id.tagFlow)
    TagFlowLayout mTagFlow;
    @BindView(R.id.tvSendLocal)
    TextView mTvSendLocal;
    @BindView(R.id.tvSendTime)
    TextView mTvSendTime;
    @BindView(R.id.tvHas)
    TextView mTvHas;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.clUserInfo)
    ConstraintLayout clUserInfo;
    @BindView(R.id.editor)
    RichEditor mEditor;
    FrameLayout flInput;
    private ForumDetailPresenter mPresenter;
    private Unbinder mUnbinder;
    private ForumDetailAdapter mAdapter;
    private View mHeader;
    private int scrollState = 0;
    private boolean isAnim = false;
    private EditText mEdInput;
    private LinearLayout mLlZan;
    private LinearLayout mLlSave;
    private ImageView mIvZan;
    private ImageView mIvUserAvatar;
    private ImageView mIvSave;
    private TextView mTvZan;
    private TextView mTvSave;
    private ForumDetailBean.DataBean mDetailData;
    private View mFooter;
    private String mId;
    private View mFlShare;
    private FrameLayout mFlScroll;
    private FrameLayout mFlUnScroll;
    private LinearLayout mLlBarShare;
    private TextView mTvBarName;
    private ImageView mIvBarAvatar;
    private TextView mTvBarFollow;
    private FrameLayout mUnScrollShare;
    private View mClShare;
    private ImageView mIvShareBgCard;
    private ImageView mIvSharePic;
    private ImageView mIvShareUserAvatar;
    private TextView mTvShareName;
    private TextView mTvShareTitle;
    private ImageView mIvQCode;
    private View mClShareDialog;
    private DialogLoading mPicLoading;
    private Bitmap mBitmap;
    private String mSaveFilePath;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_follow;

    }


    @Override
    public void initRootData(View rootView) {
        super.initRootData(rootView);
        mId = mBundle.getString("id");

        EventBus.getDefault().register(this);
        mHeader = View.inflate(mActivity, R.layout.header_forum_detail, null);
        inputComment(rootView);
        initShare(rootView);
        mFlScroll = rootView.findViewById(R.id.flScroll);
        mTvBarName = rootView.findViewById(R.id.tvBarName);

        mIvBarAvatar = rootView.findViewById(R.id.ivBarAvatar);
        mTvBarFollow = rootView.findViewById(R.id.tvBarFollow);
        rootView.findViewById(R.id.flBack).setOnClickListener(v -> finish());
        rootView.findViewById(R.id.flBackScroll).setOnClickListener(v -> finish());

        mFlUnScroll = rootView.findViewById(R.id.flUnScroll);
        mLlBarShare = rootView.findViewById(R.id.llBarShare);
        mFlShare = rootView.findViewById(R.id.flShare);
        mUnScrollShare = rootView.findViewById(R.id.flUnScrollShare);

        mFlShare.setOnClickListener(v -> {


            String cuid = mDetailData.createUserId;
            new ShareHaveReportDialog(this)
                    .setInterceptor(mDetailData == null || !"1".equals(mDetailData.status))
                    .setId(cuid)
                    .setUrl(WmtUtil.wmShare + mId)
                    .show();
        });

        mUnScrollShare.setOnClickListener(v -> {

            String cuid = mDetailData.createUserId;
            new ShareHaveReportDialog(this)
                    .setInterceptor(mDetailData == null || !"1".equals(mDetailData.status))
                    .setId(cuid)
                    .setUrl(WmtUtil.wmShare + mId)
                    .show();
        });

        mUnbinder = ButterKnife.bind(this, mHeader);
        mPresenter = new ForumDetailPresenter(this);

//        mFlShare.setOnClickListener(v -> {
//            new ForumMoreDialog(this).show(R.id.tvDel, view -> {
//                mPresenter.del(mId);
//            });
//        });
        mPresenter.forumDetail(mId, new ErrorResponseImplTip(this));
        mPresenter.forumComment(1, mId, new ErrorResponseImplTip(this));
        mAdapter = new ForumDetailAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeader);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            Bundle bundle = new Bundle();
            bundle.putString("id", mAdapter.getData().get(position).userId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mBanner.setAdapter((BGABanner.Adapter<ImageView, String>) (banner, itemView, model, position) -> {
            Glide.with(mActivity)
                    .load(WmtApplication.url_host + model)
                    .apply(GlideUtils.init().options(R.mipmap.wmt_default))
                    .into(itemView);
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isAnim && dy < 0) {
                    if (scrollState != -1) {
                        LogUtils.e("下滑" + dy);
                        startAnim(50, 0, 0, 1);
                    }
                    scrollState = -1;
                }
                if (!isAnim && dy > 0) {
                    if (scrollState != 1) {
                        LogUtil.e("上拉" + dy);
                        startAnim(0, 50, 1, 0);
                    }
                    scrollState = 1;
                }

            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerViewChangeScrollListener(500) {
            @Override
            public void changeMethod(int alpha, float percent, boolean b) {
                super.changeMethod(alpha, percent, b);
                if (b) {
                    mLlBarShare.setVisibility(View.INVISIBLE);
                } else {
                    mLlBarShare.setVisibility(View.VISIBLE);
                }
            }
        });
        mRecyclerView.addOnScrollListener(new ToolBarChangeScrollListener() {
            @Override
            public void changeMethod(int alpha, float percent, boolean b) {
                super.changeMethod(alpha, percent, b);
                if (b) {
                    mFlUnScroll.setVisibility(View.VISIBLE);
                } else {
                    mFlUnScroll.setVisibility(View.INVISIBLE);
                }
                mFlScroll.setAlpha(percent);
                mFlUnScroll.setAlpha(1 - percent);
            }
        });
        mIvUserAvatar = rootView.findViewById(R.id.ivUserAvatar);
        Glide.with(this)
                .load(WmtApplication.avatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(mIvUserAvatar);
        clUserInfo.setOnClickListener(v -> {
            showInfoActivity();
        });
        //初始化编辑高度
        mEditor.setEditorHeight(150);
        //初始化字体大小
        mEditor.setEditorFontSize(14);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置默认显示语句
        mEditor.setPlaceholder("");
        //设置编辑器是否可用
        mEditor.setInputEnabled(false);

    }

    @SuppressLint("CheckResult")
    private void initShare(View rootView) {
        mClShare = rootView.findViewById(R.id.clShare);
        mClShareDialog = rootView.findViewById(R.id.clShareDialog);
        mIvShareBgCard = rootView.findViewById(R.id.ivShareBgCard);
        mIvSharePic = rootView.findViewById(R.id.ivSharePic);
        mIvShareUserAvatar = rootView.findViewById(R.id.ivShareUserAvatar);
        mTvShareName = rootView.findViewById(R.id.tvShareName);
        mTvShareTitle = rootView.findViewById(R.id.tvShareTitle);
        mIvQCode = rootView.findViewById(R.id.ivQCode);
        String textContent = WmtUtil.wmShare + mId;
        int dp2px = SizeUtils.dp2px(68);
        Observable.just(textContent).subscribeOn(Schedulers.io())
                .map(s -> CodeUtils.createImage(s, dp2px, dp2px, BitmapUtil.drawableToBitmap(getResources().getDrawable(R.mipmap.logo))))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            mIvQCode.setImageBitmap(bitmap);
        });
        rootView.findViewById(R.id.tvShareCancel).setOnClickListener(v -> {
            mClShare.setVisibility(View.GONE);
        });
        rootView.findViewById(R.id.llShareWxAll).setOnClickListener(v -> {
            mClShareDialog.setVisibility(View.GONE);
            createPic(0);
        });
        rootView.findViewById(R.id.llShareWxOne).setOnClickListener(v -> {
            mClShareDialog.setVisibility(View.GONE);
            createPic(1);
        });
    }

    @SuppressLint("CheckResult")
    public void createPic(int i) {
        mPicLoading = new DialogLoading(this);
        mPicLoading.show();
        Observable.just(1).subscribeOn(Schedulers.io())
                .map(aLong -> {
                    mBitmap = BitmapUtil.screenShotView(mClShare);
                    mSaveFilePath = BitmapUtil.saveBitmap("share" + UUID.randomUUID() + ".png", mBitmap);
                    return aLong;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (mPicLoading != null) {
                        mPicLoading.dismiss();
                        LogUtil.e(mSaveFilePath);
                        if (mSaveFilePath != null && mBitmap != null) {
                            if (i == 1) {
                                new WXShare(this).shareImage(mSaveFilePath, mBitmap, SendMessageToWX.Req.WXSceneSession);
                            } else {
                                new WXShare(this).shareImage(mSaveFilePath, mBitmap, SendMessageToWX.Req.WXSceneTimeline);
                            }
                        }
                        Observable.timer(1, TimeUnit.SECONDS)
                                .compose(new IoMainSchedule<>())
                                .subscribe(aLong1 -> {
                                    mClShare.setVisibility(View.GONE);
                                });
                    }
                });
    }

    private void showInfoActivity() {
        if (mDetailData != null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", mDetailData.createUserId);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        }

    }

    private void inputComment(View rootView) {
        flInput = rootView.findViewById(R.id.flInputComment);
        mEdInput = rootView.findViewById(R.id.edInput);
        mLlZan = rootView.findViewById(R.id.llInputZan);
        mLlSave = rootView.findViewById(R.id.llInputSave);
        mIvZan = rootView.findViewById(R.id.ivInputZan);
        mIvSave = rootView.findViewById(R.id.ivInputSave);
        mTvZan = rootView.findViewById(R.id.tvInputZan);
        mTvSave = rootView.findViewById(R.id.tvInputSave);
        rootView.findViewById(R.id.llInputShare).setOnClickListener(v -> {

            String cuid = mDetailData.createUserId;
            new ShareHaveReportDialog(this)
                    .setInterceptor(mDetailData == null || !"1".equals(mDetailData.status))
                    .setId(cuid)
                    .setUrl(WmtUtil.wmShare + mId)
                    .show();
        });
        mEdInput.setOnEditorActionListener((v, actionId, event) -> {
            if (mDetailData == null || !"1".equals(mDetailData.status)) {
                ToastUtils.init().showInfoToast(mActivity, "当前帖子不可操作");
                BGAKeyboardUtil.closeKeyboard(mActivity);
                mEdInput.setText("");
                mEdInput.clearFocus();
                return false;
            }
            boolean isOK = false;
            //目前输入框需要支持多行输入，此时enter键的内容不会更改，且按下时actionId为0；
            // 注意不同的手机可能有兼容性问题，此时只监听enter键的按下
            //当actionId == XX_SEND
            //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
            //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                sendComment();
                BGAKeyboardUtil.closeKeyboard(mActivity);
                mEdInput.setText("");
                mEdInput.clearFocus();
                isOK = true;
            }
            return isOK;
        });
        mLlZan.setOnClickListener(v -> {
            if (mDetailData == null || !"1".equals(mDetailData.status)) {
                ToastUtils.init().showInfoToast(mActivity, "当前帖子不可操作");
                return;
            }
            String status;
            if (mIvZan.isSelected()) {
                status = "-1";
            } else {
                status = "1";
            }
            mPresenter.zan(status, mBundle.getString("id"));
            int anInt = Integer.parseInt(status);
            int num = Integer.parseInt(mTvZan.getText().toString());
            String zanNum = num + anInt + "";
            mTvZan.setText(zanNum);
            mIvZan.setSelected("1".equals(status));
        });
        mLlSave.setOnClickListener(v -> {
            if (mDetailData == null || !"1".equals(mDetailData.status)) {
                ToastUtils.init().showInfoToast(mActivity, "当前帖子不可操作");
                return;
            }
            String status;
            if (mIvSave.isSelected()) {
                status = "-1";
            } else {
                status = "1";
            }
            mPresenter.forumCollection(status, mBundle.getString("id"));
            int anInt = Integer.parseInt(status);
            int num = Integer.parseInt(mTvSave.getText().toString());
            String collectionNum = num + anInt + "";
            mTvSave.setText(collectionNum);
            mIvSave.setSelected("1".equals(status));
        });

    }

    private void sendComment() {
        if (mDetailData != null) {
            if (StringUtils.init().isEmpty(mEdInput)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入要发送的内容");
                return;
            }
            String forumId = mDetailData.forumId;
            mPresenter.commentarySave(forumId, mDetailData.forumType, StringUtils.init().fixNullStr(mEdInput.getText()), "", "1");
        }
    }

    private void startAnim(int upS, int upE, float start, float end) {
        AnimationBuilder builder = ViewAnimator.animate(flInput).duration(500).dp()
                .translationY(upS, upE).alpha(start, end)
                .onStart(() -> isAnim = true)
                .onStop(() -> isAnim = false);
        builder.start();
    }

    private String mType;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    public void forumDetail(ForumDetailBean bean) {
        mDetailData = bean.data;
        if (mDetailData.createUserId != null && mDetailData.createUserId.equals(WmtApplication.id)) {
            mFlShare.setVisibility(View.VISIBLE);
//            mFlShare.setImageDrawable(getResources().getDrawable(R.drawable.forum_more));
        }
        if ("1".equals(mDetailData.forumType)) {
            mType = "xinde";
            mTvContent.setVisibility(View.VISIBLE);
        } else if ("2".equals(mDetailData.forumType)) {
            mType = "heji";
            mEditor.setVisibility(View.VISIBLE);
        } else if ("3".equals(mDetailData.forumType)) {
            mType = "riji";
            mEditor.setVisibility(View.VISIBLE);
        } else if ("4".equals(mDetailData.forumType)) {
            mType = "video";
            mEditor.setVisibility(View.VISIBLE);
        }
        mEditor.setHtml(mDetailData.content);
        mTvName.setText(StringUtils.init().fixNullStr(mDetailData.createUser));
        mTvShareName.setText(StringUtils.init().fixNullStr(mDetailData.createUser));
        mTvBarName.setText(StringUtils.init().fixNullStr(mDetailData.createUser));

        mTvFollow.setSelected(!"-1".equals(mDetailData.attentionUserStatus));
        mTvBarFollow.setSelected(!"-1".equals(mDetailData.attentionUserStatus));

        if (mTvFollow.isSelected()) {
            mTvFollow.setText("已关注");
            mTvBarFollow.setText("已关注");
        } else {
            mTvFollow.setText("+ 关注");
            mTvBarFollow.setText("+ 关注");
        }
        mTvFollow.setOnClickListener(v -> {
            if (mDetailData == null || !"1".equals(mDetailData.status)) {
                ToastUtils.init().showInfoToast(mActivity, "当前帖子不可操作");
                return;
            }
            String status;
            if (mTvFollow.isSelected()) {
                status = "-1";
            } else {
                status = "1";
            }
            mTvFollow.setSelected(!mTvFollow.isSelected());

            if (mTvFollow.isSelected()) {
                mTvFollow.setText("已关注");
            } else {
                mTvFollow.setText("+ 关注");
            }
            mTvBarFollow.setSelected(!mTvBarFollow.isSelected());

            if (mTvBarFollow.isSelected()) {
                mTvBarFollow.setText("已关注");
            } else {
                mTvBarFollow.setText("+ 关注");
            }


            mPresenter.followShopOrUser(status, mDetailData.createUserId);
        });
        mTvBarFollow.setOnClickListener(v -> {
            if (mDetailData == null || !"1".equals(mDetailData.status)) {
                ToastUtils.init().showInfoToast(mActivity, "当前帖子不可操作");
                return;
            }
            String status;
            if (mTvBarFollow.isSelected()) {
                status = "-1";
            } else {
                status = "1";
            }

            mTvFollow.setSelected(!mTvFollow.isSelected());

            if (mTvFollow.isSelected()) {
                mTvFollow.setText("已关注");
            } else {
                mTvFollow.setText("+ 关注");
            }

            mTvBarFollow.setSelected(!mTvBarFollow.isSelected());

            if (mTvBarFollow.isSelected()) {
                mTvBarFollow.setText("已关注");
            } else {
                mTvBarFollow.setText("+ 关注");
            }

            mPresenter.followShopOrUser(status, mDetailData.createUserId);
        });

        if (StringUtils.isNotBlank(mDetailData.birthday) && mDetailData.birthday.length() > 3) {
            mTvTime.setText(String.format(Locale.CHINA, "%s0后", mDetailData.birthday.substring(2, 3)));
        }


        String skin = StringUtils.init().fixNullStr(mDetailData.fuzhiName);
        if (StringUtils.isNotBlank(skin)) {
            mTvSkin.setText(String.format(Locale.CHINA, "·%s", skin));
        }
        Glide.with(mActivity)
                .load(WmtApplication.url_host + mDetailData.createUserAvatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(mIvAvatar);
        Glide.with(mActivity)
                .load(WmtApplication.url_host + mDetailData.createUserAvatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(mIvShareUserAvatar);

        Glide.with(mActivity)
                .load(WmtApplication.url_host + mDetailData.createUserAvatar)
                .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                .into(mIvBarAvatar);

        List<String> pictures = mDetailData.pictures;
        mBanner.setData(pictures, null);
        String shareUrl = "";
        if (pictures != null && pictures.size() > 0) {
            shareUrl = pictures.get(0);
        }
        Glide.with(mActivity)
                .load(WmtApplication.url_host + shareUrl)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvShareBgCard);
        Glide.with(mActivity)
                .load(WmtApplication.url_host + shareUrl)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvSharePic);

        if (StringUtils.isNotBlank(mDetailData.positionName)) {
            mTvSendLocal.setText(StringUtils.init().fixNullStr(mDetailData.positionName));
        } else mTvSendLocal.setVisibility(View.INVISIBLE);
        mTvSendTime.setText(StringUtils.init().fixNullStr(mDetailData.createTime));
        mTvContent.setText(StringUtils.init().fixNullStr(mDetailData.content));
        if ("1".equals(mDetailData.forumType)) {
            mTvShareTitle.setText(StringUtils.init().fixNullStr(mDetailData.content));
        } else if ("2".equals(mDetailData.forumType)) {
            mTvShareTitle.setText(StringUtils.init().fixNullStr(mDetailData.title));
        } else if ("3".equals(mDetailData.forumType)) {
            mTvShareTitle.setText(StringUtils.init().fixNullStr(mDetailData.title));
        } else if ("4".equals(mDetailData.forumType)) {
            mTvShareTitle.setText(StringUtils.init().fixNullStr(mDetailData.title));
        }

        String topicListStr = mDetailData.topicListStr;
        if (StringUtils.isNotBlank(topicListStr)) {
            if (topicListStr.contains(",")) {
                mDetailData.topicList = Arrays.asList(topicListStr.split(","));
            } else {
                mDetailData.topicList.add(topicListStr);
            }
        }
        if (mDetailData.topicList != null && mDetailData.topicList.size() > 0) {
            mTagFlow.setVisibility(View.VISIBLE);
            HotForumAdapter adapter = new HotForumAdapter(mDetailData.topicList);
            mTagFlow.setAdapter(adapter);
        } else {
            mTagFlow.setVisibility(View.GONE);
        }

        mIvSave.setSelected("1".equals(mDetailData.shoucangStatus));
        mIvZan.setSelected("1".equals(mDetailData.dianzanStatus));
        mTvZan.setText(StringUtils.init().fixNullStr(mDetailData.dianzianNum));
        mTvSave.setText(StringUtils.init().fixNullStr(mDetailData.shoucangNum));

    }

    public void commentList(ForumCommentBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            if (bean.data.size() > 10) {
                mAdapter.setNewData(bean.data.subList(0, 10));
                mAdapter.removeAllFooterView();
                mFooter = View.inflate(mActivity, R.layout.footer_more_comment, null);
                mAdapter.addFooterView(mFooter);
                mFooter.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mId);
                    ActivityUtils.init().start(mActivity, MoreCommentActivity.class, "", bundle);
                });
            } else {
                mAdapter.setNewData(bean.data);
            }


            mTvHas.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            mTvHas.setText(String.format(Locale.CHINA, "共%s条评论", bean.data.size()));
        } else {
            viewLine.setVisibility(View.GONE);
            mTvHas.setVisibility(View.GONE);
        }
    }

    public void del() {
        finish();
    }

    public void complete() {
        responseSuccess();
    }

    @Subscribe
    public void shareEvent(ShareReportEvent event) {
        LogUtil.e(event);
        Bundle bundle = new Bundle();
        bundle.putString("id", mId);
        bundle.putString("type", "2");
        ActivityUtils.init().start(mActivity, ReportActivity.class, "举报", bundle);
    }

    @Subscribe
    public void onEvent(EditEvent editEvent) {
        mPresenter.forumDetail(mId, new ErrorResponseImplTip(this));
    }

    @Subscribe
    public void shareEvent(ShareDelEvent event) {
        mPresenter.del(mId);
    }

    @Subscribe
    public void shareEvent(ShareEditEvent event) {
        LogUtil.e("====");
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", mDetailData);
        bundle.putString("type", mType);
        if ("xinde".equals(mType)) {
            ActivityUtils.init().start(mActivity, ForumXindeEditActivity.class, bundle);
        }else {
            ActivityUtils.init().start(mActivity, ForumHejiEditActivity.class, bundle);
        }
    }

    @Subscribe
    public void shareEvent(SharePicEvent event) {
        ViewAnimator.animate(mClShare)
                .duration(500)
                .bounceIn()
                .onStart(() -> {
                    mClShare.setVisibility(View.VISIBLE);
                    mClShareDialog.setVisibility(View.VISIBLE);
                })
                .start();

    }

    @Override
    public void onBackPressed() {
        if (mClShare.getVisibility() == View.VISIBLE) {
            mClShare.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public void commentSave(CommentSaveBean bean) {
        mPresenter.forumComment(1, mBundle.getString("id"), new LoaddingErrorImplTip(this));
    }

    public void zan() {

    }

    public void follow() {

    }


}
