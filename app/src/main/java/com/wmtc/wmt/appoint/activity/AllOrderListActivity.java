package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.utils.klog.KLog;

import com.google.gson.Gson;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.dialog.DelOrderDialog;
import com.wmtc.wmt.appoint.dialog.DialogCancelPush;
import com.wmtc.wmt.appoint.event.CancelOrderEvent;
import com.wmtc.wmt.appoint.event.JudgeEvent;
import com.wmtc.wmt.appoint.event.PayOkEvent;
import com.wmtc.wmt.appoint.event.RefundSaleEvent;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.appoint.presenter.AllOrderListPresenter;
import com.wmtc.wmt.forum.activity.ForumHejiSendActivity;
import com.wmtc.wmt.forum.activity.ForumXindeSendActivity;
import com.wmtc.wmt.forum.adapter.AllOrderAdapter;
import com.wmtc.wmt.forum.adapter.OrderListAdapter;
import com.wmtc.wmt.forum.dialog.ForumMoreDialog;
import com.wmtc.wmt.forum.event.SendRijiEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

public class AllOrderListActivity extends CommonWmtActivity {

    private AllOrderListPresenter mPresenter;
    private OrderListAdapter mAdapter;
    private String status;
    private DelOrderDialog mDialog;
    private String mType;
    private int currentPage = 1;

    @Override
    public int initAddLayout() {
        return R.layout.activity_all_order_list;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mPresenter = new AllOrderListPresenter(this);
        EventBus.getDefault().register(this);
        status = mBundle.getString("status");
        showLoading();
        mAdapter = new OrderListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Bundle bundle = new Bundle();
            OrderListBean.DataBean.ListBean bean = mAdapter.getData().get(position);
            bundle.putString("id", bean.id);
            ActivityUtils.init().start(mActivity, OrderInfoActivity.class, "订单详情", bundle);
        });
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            OrderListBean.DataBean.ListBean bean = mAdapter.getData().get(position);
            if (view1.getId() == R.id.tvPayFirst) {//待支付
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.id);
                ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
            } else if (view1.getId() == R.id.tvPayEnd) { //待到店 支付
                long preTime = DateUtils.dateToStamp(bean.arrivalTime);
                long nowTime = new Date().getTime();
                LogUtil.e(preTime + "-----" + nowTime);
                if (preTime < nowTime) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", bean.id);
                    ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
                } else {
                    ToastUtils.init().showInfoToast(mActivity, "到店后方可支付尾款");
                }
            } else if (view1.getId() == R.id.tvCancel) {
                String cancelType = "paid_cancel_order_reason";
                if ("1".contains(bean.orderStatus)) {
                    cancelType = "unpay_cancel_order_reason";
                }
                new DialogCancelPush(mActivity)
                        .setDict(cancelType, bean.id)
                        .show();

            } else if (view1.getId() == R.id.ivDelOrder) {
                //删除
                mDialog = new DelOrderDialog(this);
                mDialog.show(R.id.tvDel, view -> {
                    RefundPojo pojo = new RefundPojo();
                    pojo.setId(bean.id);
                    pojo.setUserId(WmtApplication.id);
                    mPresenter.delete(pojo);
                });

            } else if (view1.getId() == R.id.tvCommit) {
                //评价
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                String json = gson.toJson(bean);
                OneOrderBean.DataBean jsonBean = gson.fromJson(json, OneOrderBean.DataBean.class);
                bundle.putParcelable("order", jsonBean);
                ActivityUtils.init().start(mActivity, OrderCommentActivity.class, "", bundle);
            } else if (view1.getId() == R.id.tvRiji) {
                if ("1".equals(bean.writeRiji)) {
                    ToastUtils.init().showInfoToast(mActivity, "该订单已写过日记");
                    return false;
                }
                mType = "riji";
                orderId = bean.id;
                AndPermission.with(this)
                        .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            } else if (view1.getId() == R.id.tvCreateNew) {
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.projectId);
                ActivityUtils.init().start(mActivity, ProjectInfoActivity.class, "", bundle);
            }
            return false;
        });
        mRecyclerView.setAdapter(mAdapter);
        getAllOrderList(status, currentPage);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            ++currentPage;
            getAllOrderList(status, currentPage);
        });
    }


    private void getAllOrderList(String status, int currentPage) {
        OrderListPojo pojo = new OrderListPojo();
        pojo.setOrderStatus(status);
        pojo.setCurrentPage(currentPage + "");
        pojo.setUserId(WmtApplication.id);
        mPresenter.getAllOrderList(pojo);
    }

    public void getAllOrderListDate(OrderListBean bean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        OrderListBean.DataBean data = bean.data;

        if (currentPage <= 1) {
            if (data != null && data.list != null && data.list.size() > 0) {
                mAdapter.setNewData(data.list);
            } else {
                showEmpty();
            }
        } else {
            if (data != null && data.list != null && data.list.size() > 0) {
                mAdapter.addData(data.list);
            }
        }

    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        currentPage = 1;
        getAllOrderList(status, currentPage);
    }

    public void getRefundDate(RefundBean bean) {
        currentPage = 1;
        getAllOrderList(status, currentPage);
    }

    public void cancelOk() {
        currentPage = 1;

        getAllOrderList(status, currentPage);
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
                    File file = BitmapUtil.compressImage(new File(s), mType + UUID.randomUUID());
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
                            delayOpenSendActivity(mType);
                        });
            });
        }
    }

    public ArrayList<File> mFileArrayList;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    public String orderId;

    private void delayOpenSendActivity(String type) {
        if (orderId != null) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("orderId", orderId);
            bundle.putSerializable("file", mFileArrayList);
            bundle.putSerializable("banner", mFileArrayList.get(0));
            if ("heji".equals(type) || "riji".equals(type)) {
                ActivityUtils.init().start(mActivity, ForumHejiSendActivity.class, "", bundle);
            } else {
                ActivityUtils.init().start(mActivity, ForumXindeSendActivity.class, "", bundle);
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(CancelOrderEvent event) {
        if ("unpay_cancel_order_reason".equals(event.type)) {
            mPresenter.cancelOrder(event.mRefundPojo);
        } else {
            mPresenter.getRefund(event.mRefundPojo);
        }
    }

    @Subscribe
    public void onEvent(RefundSaleEvent event) {
        currentPage = 1;
        getAllOrderList(status, currentPage);
    }

    @Subscribe
    public void onEvent(PayOkEvent event) {
        currentPage = 1;

        getAllOrderList(status, currentPage);
    }

    @Subscribe
    public void onEvent(SendRijiEvent event) {
        currentPage = 1;
        getAllOrderList(status, currentPage);
    }

    @Subscribe
    public void onEvent(JudgeEvent event) {
        currentPage = 1;

        getAllOrderList(status, currentPage);
    }

    public void delete(BaseBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        currentPage = 1;

        getAllOrderList(status, currentPage);
    }

}
