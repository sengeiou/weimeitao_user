package com.wmtc.wmt.personal.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.activity.AllOrderListActivity;
import com.wmtc.wmt.appoint.activity.CheckPayActivity;
import com.wmtc.wmt.appoint.activity.OrderCommentActivity;
import com.wmtc.wmt.appoint.activity.OrderInfoActivity;
import com.wmtc.wmt.appoint.activity.RedBagWillActivity;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.appoint.event.JudgeEvent;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtUtil;
import com.wmtc.wmt.forum.activity.ForumMeActivity;
import com.wmtc.wmt.forum.activity.ForumSearchActivity;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.wmtc.wmt.personal.activity.FeedBackActivity;
import com.wmtc.wmt.personal.activity.SetActivity;
import com.wmtc.wmt.personal.activity.SetPersonalMessageActivity;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.personal.dialog.PersonalCodeDialog;
import com.wmtc.wmt.personal.dialog.ShareDialog;
import com.wmtc.wmt.personal.event.AvatarEvent;
import com.wmtc.wmt.personal.event.LoginSuccessEvent;
import com.wmtc.wmt.personal.event.RefreshUserInfoEvent;
import com.wmtc.wmt.personal.event.ShareCodeEvent;
import com.wmtc.wmt.personal.presenter.PersonalPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/5/15.
 * com.wmtc.wmt.personal.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PersonalFragment extends SuperBaseFragment {
    @BindView(R.id.ivSetting)
    ImageView mIvSetting;
    @BindView(R.id.ivAddOne)
    ImageView ivAddOne;
    @BindView(R.id.ivShare)
    ImageView mIvShare;
    @BindView(R.id.tvMeName)
    TextView mTvMeName;
    @BindView(R.id.ivMeAvatar)
    PolygonImageView mIvMeAvatar;
    @BindView(R.id.ivSex)
    ImageView mIvSex;
    @BindView(R.id.ivUserAvatarBg)
    ImageView mIvUserAvatarBg;
    @BindView(R.id.ivBirth)
    TextView mIvBirth;
    @BindView(R.id.ivSkin)
    TextView mIvSkin;
    @BindView(R.id.ivLocal)
    TextView mIvLocal;
    @BindView(R.id.tvSex)
    TextView mTvSex;
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
    @BindView(R.id.tvOrderMore)
    TextView mTvOrderMore;
    @BindView(R.id.llWaitPay)
    LinearLayout mLlWaitPay;
    @BindView(R.id.llWaitShop)
    LinearLayout mLlWaitShop;
    @BindView(R.id.llWaitComment)
    LinearLayout mLlWaitComment;
    @BindView(R.id.llSetting)
    LinearLayout mLlSetting;
    @BindView(R.id.llFeed)
    LinearLayout mLlFeed;
    @BindView(R.id.llWaitPayed)
    LinearLayout mLlWaitPayed;
    @BindView(R.id.clOneOrder)
    ConstraintLayout mClOneOrder;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvSubTitle)
    TextView mTvSubTitle;
    @BindView(R.id.btnLeft)
    Button mBtnLeft;
    @BindView(R.id.btnRight)
    Button mBtnRight;
    @BindView(R.id.llBtn)
    LinearLayout mLlBtn;
    @BindView(R.id.llServer)
    LinearLayout mLlServer;
    @BindView(R.id.llRed)
    LinearLayout mLlRed;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    Unbinder unbinder;
    private PersonalPresenter mPresenter;
    UserInfoBean.DataBean mDataBean;
    private OneOrderBean.DataBean mOrderOne;

    @Override
    public int initLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.init();
    }


    @Override
    protected void initData(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        initImmersionBar();
        EventBus.getDefault().register(this);
        ivAddOne.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, ForumSearchActivity.class, "");
        });
        mPresenter = new PersonalPresenter(this);
        mPresenter.getOrderByOne();
        mPresenter.getUserInfo();
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getOrderByOne();
            mPresenter.getUserInfo();
            mEdDetail.clearFocus();
        });
        initClick();
        mEdDetail.setEnabled(WmtApplication.loginOrInfoToStart(mActivity));

    }

    @Override
    protected void onShowFragment() {
        super.onShowFragment();
        mPresenter.getOrderByOne();
        mPresenter.getUserInfo();
        mEdDetail.setEnabled(WmtApplication.loginOrInfoToStart(mActivity));
    }

    private void initClick() {

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

        mLlServer.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "admin");
                ActivityUtils.init().start(mActivity, CustomChatActivity.class, "唯美淘在线客服", bundle);
            }
        });
        mLlRed.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                ActivityUtils.init().start(mActivity, RedBagWillActivity.class, "红包卡券");
            }
        });
        mLlSetting.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                ActivityUtils.init().start(mActivity, SetActivity.class, "账号设置");
            }
        });
        mTvEdit.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                ActivityUtils.init().start(mActivity, SetPersonalMessageActivity.class, "编辑个人资料");
            }
        });
        mIvMeAvatar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", WmtApplication.id);
            ActivityUtils.init().start(mActivity, ForumMeActivity.class, "", bundle);
        });
        mTvOrderMore.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                toAllOrderActivity("", "全部订单");
            }
        });
        mLlWaitPay.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                toAllOrderActivity("1", "待支付");
            }
        });
        mLlWaitShop.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                toAllOrderActivity("5", "待到店");
            }
        });
        mLlWaitComment.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                toAllOrderActivity("comment", "待点评");
            }
        });

        mLlWaitPayed.setOnClickListener(v -> {
            if (WmtApplication.loginOrInfoToStart(mActivity)) {
                toAllOrderActivity("refund", "退款");
            }
        });
        mLlFeed.setOnClickListener(v -> ActivityUtils.init().start(mActivity, FeedBackActivity.class, "反馈内容"));
        mIvShare.setOnClickListener(v -> {
            new ShareDialog(mActivity)
                    .setCode()
                    .setUrl(BuildConfig.HOST + "h5/index.html#/pages/index/register?type=1&uid=" + WmtApplication.id)
                    .show();
        });
    }

    private void toAllOrderActivity(String status, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        ActivityUtils.init().start(mActivity, AllOrderListActivity.class, type, bundle);
    }


    public void getUserInfo(UserInfoBean bean) {
        mSmartRefreshLayout.finishRefresh();
        mDataBean = bean.data;
        if (mDataBean != null) {
            Glide.with(mActivity)
                    .load(WmtApplication.url_host + mDataBean.avatar)
                    .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                    .into(mIvMeAvatar);
            Glide.with(mActivity)
                    .load(WmtApplication.url_host + mDataBean.avatar)
                    .apply(GlideUtils.init().options(R.drawable.wm_avatar))
                    .into(mIvUserAvatarBg);
            mEdDetail.setText(mDataBean.introduce);
            mIvSex.setImageDrawable(getResources().getDrawable("1".equals(mDataBean.sex) ?
                    R.drawable.boy : R.drawable.girl));
            mTvSex.setText("1".equals(mDataBean.sex) ? "精致男" : "轻奢女");

            if (StringUtils.isNotBlank(mDataBean.birthday) && mDataBean.birthday.length() > 3) {
                String birth = mDataBean.birthday.substring(2, 3) + "0后";
                mIvBirth.setText(birth);
            }

            mIvSkin.setText(StringUtils.init().fixNullStr(mDataBean.userFuzhiName));
            mIvLocal.setText(StringUtils.init().fixNullStr(mDataBean.loginCityName));
            mTvMeName.setText(StringUtils.init().fixNullStr(mDataBean.name));

            if (StringUtils.isNotBlank(mDataBean.birthday)) {
                WmtApplication.isInfo = "1";
            } else {
                WmtApplication.isInfo = "";
            }

        }
    }


    public void getOrderByOne(OneOrderBean bean) {
        mSmartRefreshLayout.finishRefresh();
        mOrderOne = bean.data;
        if (mOrderOne != null && StringUtils.isNotBlank(mOrderOne.id)) {
            mClOneOrder.setVisibility(View.VISIBLE);
            mTvTitle.setText(StringUtils.init().fixNullStr(mOrderOne.projectName));
            mTvSubTitle.setText(StringUtils.init().fixNullStr(mOrderOne.shopName));
            mBtnLeft.setText(WmtUtil.getStatus(mOrderOne.orderStatus));
            mBtnLeft.setOnClickListener(v -> {

                if ("1".equals(mOrderOne.orderStatus)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mOrderOne.id);
                    ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
                } else if ("5".equals(mOrderOne.orderStatus)) {
                    //todo 带到店
                } else if ("6".equals(mOrderOne.orderStatus)) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("order", mOrderOne);
                    ActivityUtils.init().start(mActivity, OrderCommentActivity.class, "", bundle);
                } else {
                    //todo 退款
                }

            });

            mClOneOrder.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", mOrderOne.id);
                ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
            });
        } else {
            mClOneOrder.setVisibility(View.GONE);
        }
    }

    public void getRefundDate(RefundBean bean) {
        mPresenter.getOrderByOne();
    }

    public void refreshByMain() {
        mPresenter.getUserInfo();
        mPresenter.getOrderByOne();
    }

    @Subscribe
    public void onEvent(LoginSuccessEvent event) {
        mPresenter.getUserInfo();
    }

    @Subscribe
    public void onEvent(ShareCodeEvent event) {
        new PersonalCodeDialog(mActivity)
                .setName(mTvMeName.getText().toString())
                .setUrl(event.url)
                .show();
    }

    @Subscribe
    public void onEvent(RefreshUserInfoEvent event) {
        mPresenter.getUserInfo();
    }

    @Subscribe
    public void onEvent(AvatarEvent event) {
        mPresenter.getUserInfo();
    }

    @Subscribe
    public void onEvent(JudgeEvent event) {
        mPresenter.getOrderByOne();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();

    }

    public void updateUserInfo(BaseBean bean) {

    }

}
