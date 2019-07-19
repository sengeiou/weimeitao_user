package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.appoint.dialog.CustomDialog;
import com.wmtc.wmt.appoint.dialog.DialogCancelPush;
import com.wmtc.wmt.appoint.event.CancelOrderEvent;
import com.wmtc.wmt.appoint.event.JudgeEvent;
import com.wmtc.wmt.appoint.event.PayOkEvent;
import com.wmtc.wmt.appoint.event.RefundSaleEvent;
import com.wmtc.wmt.appoint.fragment.OrderOkFragment;
import com.wmtc.wmt.appoint.presenter.OrderInfoPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.activity.ForumHejiSendActivity;
import com.wmtc.wmt.forum.activity.ForumXindeSendActivity;
import com.wmtc.wmt.forum.event.SendRijiEvent;
import com.wmtc.wmt.message.activity.CustomChatActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/25.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderInfoActivity extends CommonWmtActivity {

    @BindView(R.id.flOrderInclude)
    FrameLayout mFlOrderInclude;
    @BindView(R.id.tvChatServer)
    TextView mTvChatServer;
    @BindView(R.id.tvPhoneServer)
    TextView mTvPhoneServer;
    @BindView(R.id.tvNewOne)
    TextView mTvNewOne;
    @BindView(R.id.tvComment)
    TextView mTvComment;
    @BindView(R.id.llPayOneAndComment)
    LinearLayout mLlPayOneAndComment;
    @BindView(R.id.tvCancelOrder)
    TextView mTvCancelOrder;
    @BindView(R.id.tvPayEnd)
    TextView mTvPayEnd;
    @BindView(R.id.llPayEndAndCancel)
    LinearLayout mLlPayEndAndCancel;
    @BindView(R.id.tvCancel)
    TextView mTvCancel;
    @BindView(R.id.tvToAppoint)
    TextView mTvToAppoint;
    @BindView(R.id.tvReBuy)
    TextView tvReBuy;
    @BindView(R.id.clPayFirst)
    ConstraintLayout mClPayFirst;
    @BindView(R.id.flOrderBtn)
    FrameLayout mFlOrderBtn;
    private OrderInfoPresenter mPresenter;
    private String mId;
    private OrderOkFragment mOrderOkFragment;
    private Unbinder mBind;
    private OrderDetailsBean.DataBean mOrderBean;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private String type;

    @Override
    public int initAddLayout() {
        return R.layout.activity_order_info;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new OrderInfoPresenter(this);
        mId = mBundle.getString("id");
        mPresenter.getOrderDetails(mId);
        mPresenter.getServerPhone();
        mOrderOkFragment = new OrderOkFragment();
        replaceFragment(mOrderOkFragment);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flOrderInclude, fragment)
                .commitAllowingStateLoss();
    }

    public void getOrderDetails(OrderDetailsBean bean) {
        mOrderBean = bean.data;
        if (mOrderBean != null && mOrderOkFragment != null) {
            mOrderOkFragment.orderInfo(mOrderBean);
            mPresenter.getCustomerService(mOrderBean.shopId);
            //待支付
            mClPayFirst.setVisibility("1".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvCancel.setOnClickListener(v -> {
                String cancelType = "unpay_cancel_order_reason";
                new DialogCancelPush(mActivity)
                        .setDict(cancelType, mOrderBean.id)
                        .show();
            });
            mTvToAppoint.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", mId);
                ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
            });
            //待接单——待到店
            mLlPayEndAndCancel.setVisibility("3".equals(mOrderBean.orderStatus) || "5".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvCancelOrder.setVisibility("3".equals(mOrderBean.orderStatus) || "5".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvCancelOrder.setOnClickListener(v -> {
                String cancelType = "paid_cancel_order_reason";
                new DialogCancelPush(mActivity)
                        .setDict(cancelType, mOrderBean.id)
                        .show();
            });
//            mTvCancelOrder.setVisibility("5".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvPayEnd.setOnClickListener(v -> {
                //待到店 支付
                long preTime = DateUtils.dateToStamp(mOrderBean.arrivalTime);
                long nowTime = new Date().getTime();
                LogUtil.e(preTime + "-----" + nowTime);
                if (preTime < nowTime) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mId);
                    ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
                } else {
                    ToastUtils.init().showInfoToast(mActivity, "到店后方可支付尾款");
                }
            });
            //已完成
            mLlPayOneAndComment.setVisibility("6".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvNewOne.setOnClickListener(v -> {//已修改为写日记*
//                Bundle bundle = new Bundle();
//                bundle.putString("id", mOrderBean.projectId);
//                ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, "", bundle);
                if ("1".equals(mOrderBean.writeRiji)) {
                    ToastUtils.init().showInfoToast(mActivity, "该订单已写过日记");
                } else {
                    type = "riji";
                    AndPermission.with(this)
                            .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                            .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                            .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                            .start();
                }

            });
            mTvComment.setText("1".equals(mOrderBean.isComment) ? "已评价" : "评价");
            mTvComment.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                mOrderBean.picUrl = WmtApplication.url_host + mOrderBean.picUrl;
                if ("1".equals(mOrderBean.isComment)) {
                    return;
                }
                Gson gson = new Gson();
                String json = gson.toJson(mOrderBean);
                OneOrderBean.DataBean jsonBean = gson.fromJson(json, OneOrderBean.DataBean.class);
                bundle.putParcelable("order", jsonBean);
                ActivityUtils.init().start(mActivity, OrderCommentActivity.class, "", bundle);
            });
            mTvChatServer.setVisibility("4".equals(mOrderBean.orderStatus) || "9".equals(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            mTvChatServer.setOnClickListener(v -> callPhone(phone));

            ArrayList<String> list = new ArrayList<>();
            list.add("2");
            list.add("7");
            list.add("8");
            list.add("10");
            list.add("11");
            list.add("12");
            list.add("13");
            list.add("14");
            list.add("15");
            tvReBuy.setVisibility(list.contains(mOrderBean.orderStatus) ? View.VISIBLE : View.GONE);
            tvReBuy.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", mOrderBean.projectId);
                ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, "", bundle);
            });
        }
    }

    @Subscribe
    public void onEvent(PayOkEvent event) {
        mPresenter.getOrderDetails(mId);
    }

    @Subscribe
    public void onEvent(RefundSaleEvent event) {
        mPresenter.getOrderDetails(mId);
    }

    @Subscribe
    public void onEvent(JudgeEvent event) {
        mPresenter.getOrderDetails(mId);
    }

    @Subscribe
    public void onEvent(SendRijiEvent event) {
        mPresenter.getOrderDetails(mId);
    }

    @Subscribe
    public void onEvent(CancelOrderEvent event) {
        if ("unpay_cancel_order_reason".equals(event.type)) {
            mPresenter.cancelOrder(event.mRefundPojo);
        } else {
            mPresenter.getRefund(event.mRefundPojo);
        }
    }

    public String phone = "";

    public void callPhone(DictListBean bean) {
        List<DictListBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            phone = data.get(0).name;
        }
    }

    public void callPhone(String phone) {
        CustomDialog customDialog = new CustomDialog(mActivity);
        customDialog.setDismissButton(CustomDialog.Type.TEXTVIEW);
        customDialog.show();
        customDialog.init(phone, "", "取消", "呼叫", (dialog, type) -> {
            switch (type) {
                case LEFT:
                    customDialog.dismiss();
                    break;
                case RIGHT:
                    Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);
                    break;
            }
        });

    }

    public void chatBean(CustomChatBean bean) {
        if (bean.data != null && mOrderOkFragment != null) {
            mOrderOkFragment.chat(bean.data);
        }
    }

    public void getRefundDate(RefundBean bean) {
        mPresenter.getOrderDetails(mId);
    }

    public void cancelOk() {
        mPresenter.getOrderDetails(mId);
    }

    public void getRefund(RefundBean bean) {
        mPresenter.getOrderDetails(mId);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (mFileArrayList == null) {
                mFileArrayList = new ArrayList<>();
            }
            mFileArrayList.clear();
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {

                Observable.fromIterable(strings).subscribe(s -> {
                    File file = BitmapUtil.compressImage(new File(s), type + UUID.randomUUID());
                    mFileArrayList.add(file);
                });
                return mFileArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .compose(new IoMainSchedule<>())
                        .subscribe(aLong -> {
                            delayOpenSendActivity(type);
                        });
            });
        }
    }

    public ArrayList<File> mFileArrayList;

    private void delayOpenSendActivity(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("orderId", mId);
        bundle.putSerializable("file", mFileArrayList);
        bundle.putSerializable("banner", mFileArrayList.get(0));
        if ("heji".equals(type) || "riji".equals(type)) {
            ActivityUtils.init().start(mActivity, ForumHejiSendActivity.class, "", bundle);
        } else {
            ActivityUtils.init().start(mActivity, ForumXindeSendActivity.class, "", bundle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }


}
