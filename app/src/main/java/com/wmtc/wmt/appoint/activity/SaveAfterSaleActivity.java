package com.wmtc.wmt.appoint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.dialog.DialogRefundPush;
import com.wmtc.wmt.appoint.event.RefundOrderEvent;
import com.wmtc.wmt.appoint.event.RefundSaleEvent;
import com.wmtc.wmt.appoint.pojo.SaveAfterSalePojo;
import com.wmtc.wmt.appoint.presenter.SaveReasonPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.PicsAdapter;
import com.wmtc.wmt.forum.event.FileUpdateOkEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/6/27.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SaveAfterSaleActivity extends CommonWmtActivity {
    @BindView(R.id.tvOrderTime)
    TextView mTvOrderTime;
    @BindView(R.id.tvOrderStatus)
    TextView mTvOrderStatus;
    @BindView(R.id.llTimeStatus)
    LinearLayout mLlTimeStatus;
    @BindView(R.id.ivOrderPic)
    ImageView mIvOrderPic;
    @BindView(R.id.tvOrderName)
    TextView mTvOrderName;
    @BindView(R.id.tvOrderPrice)
    TextView mTvOrderPrice;
    @BindView(R.id.tvOrderShop)
    TextView mTvOrderShop;
    @BindView(R.id.tvOrderAppointTime)
    TextView mTvOrderAppointTime;
    @BindView(R.id.tvOrderRefundPrice)
    TextView mTvOrderRefundPrice;
    @BindView(R.id.tvWhyRefundTip)
    TextView mTvWhyRefundTip;
    @BindView(R.id.tvWhyRefund)
    TextView mTvWhyRefund;
    @BindView(R.id.editResume)
    EditText mEditResume;
    @BindView(R.id.recyclerViewFooter)
    RecyclerView mRecyclerViewFooter;
    private Unbinder mUnbinder;
    private SaveReasonPresenter mPresenter;
    private ArrayList<File> mFiles;
    private PicsAdapter mFooterAdapter;
    private View mFooterAdd;
    private SaveAfterSalePojo mPojo;

    @Override
    public int initAddLayout() {
        return R.layout.activity_save_after_sale;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new SaveReasonPresenter(this);
        mPresenter.ossToken();
        mPojo = new SaveAfterSalePojo();
        mPojo.orderId = mBundle.getString("id");
        EventBus.getDefault().register(this);
        mTvOrderAppointTime.setText(mBundle.getString("appointTime"));
        mTvOrderName.setText(mBundle.getString("projectName"));
        mTvOrderShop.setText(mBundle.getString("shopName"));
        Glide.with(mActivity)
                .load(WmtApplication.url_host + mBundle.getString("picUrl"))
                .apply(GlideUtils.init().roundTransFrom(mActivity, 5, R.drawable.wmt_default))
                .into(mIvOrderPic);
        mTvOrderTime.setText(mBundle.getString("createTime"));
        mTvOrderPrice.setText(StringUtils.init().fixNullStrMoney(mBundle.getInt("unpayPay") + "", "￥"));
        mTvOrderRefundPrice.setText(StringUtils.init().fixNullStrMoney(mBundle.getInt("paidPay") + "", "￥"));
        mTvWhyRefund.setOnClickListener(v -> {
            new DialogRefundPush(mActivity)
                    .setDict("after_sale_reason", mBundle.getString("id"))
                    .show();
        });
        mTvToolRight.setVisibility(View.VISIBLE);
        mTvToolRight.setText("提交");
        mRecyclerViewFooter.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mFiles = new ArrayList<>();
        mFooterAdapter = new PicsAdapter(mFiles);
        mRecyclerViewFooter.setAdapter(mFooterAdapter);
        mFooterAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                mFooterAdapter.remove(position);
                if (mFooterAdapter.getData().size() < 1) {
                    mFooterAdapter.removeAllFooterView();
                    mFooterAdapter.addFooterView(mFooterAdd);
                }
                if (mFooterAdapter.getData().size() >= 9) {
                    mFooterAdapter.removeAllFooterView();
                }
            }
            return false;
        });
        mFooterAdd = View.inflate(mActivity, R.layout.footer_add, null);
        mFooterAdd.findViewById(R.id.ivItemSrc).setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCameraWithSize(mActivity, 9))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        mFooterAdapter.addFooterView(mFooterAdd);


    }

    @SuppressLint("CheckResult")
    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);

        if (StringUtils.isBlank(mTvWhyRefund.getText().toString())) {
            ToastUtils.init().showInfoToast(mActivity, "请选择退款理由");
            return;
        }
        mPojo.afterSaleReason = mTvWhyRefund.getText().toString();
        if (mLoading == null) {
            mLoading = new DialogLoading(mActivity);
        }
        mLoading.show();
        if (mFooterAdapter.getData().size() > 0) {
            size = mFooterAdapter.getData().size() - 1;
            Observable.fromIterable(mFooterAdapter.getData())
                    .subscribe(file -> {
                        updatePhoto(file, "1", "refundImage");
                    });
        } else {
            mPojo.afterSaleDescription = mEditResume.getText().toString();
            mPresenter.saveAfterSale(mPojo);
        }
    }

    @Subscribe
    public void onEvent(RefundOrderEvent event) {
        mTvWhyRefund.setText(event.reason);
    }

    private DialogLoading mLoading;
    public boolean isZiped = true;

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
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                Observable.fromIterable(strings).subscribe(s -> {
                    File file = BitmapUtil.compressImage(new File(s));
                    mFiles.add(file);
                });
                return mFiles;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                mFooterAdapter.setNewData(mFiles);
                if (mFooterAdapter.getData().size() < 1) {
                    mFooterAdapter.removeAllFooterView();
                    mFooterAdapter.addFooterView(mFooterAdd);
                }
                if (mFooterAdapter.getData().size() >= 9) {
                    mFooterAdapter.removeAllFooterView();
                }
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
            });
        }
    }

    public void saveAfterSale() {
        ToastUtils.init().showSuccessToast(mActivity, "已提交售后申请，请耐心等待客服介入");
        EventBus.getDefault().post(new RefundSaleEvent());
        finish();
    }


    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {

        Observable.just(1).compose(new IoMainSchedule<>()).subscribe(integer -> {
            String s = mStringBuilder.toString();
            if (StringUtils.isNotBlank(s)) {
                if (s.contains(",")) {
                    mPojo.afterSalePics = s.substring(0, s.length() - 1);
                } else {
                    mPojo.afterSalePics = s;
                }
            }
            mPresenter.saveAfterSale(mPojo);
        }, throwable -> {
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    private int curNum = 0;
    private Bitmap mBitmap;
    private int size;
    private OSSClient mOss;
    private StringBuilder mStringBuilder;

    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }

    /**
     * OSS图片上传
     *
     * @param file
     */
    public void updatePhoto(File file, String type, String serviceType) {
        String md5 = MD5Utils.getMD5(file.getName());
        String ext = file.getPath().substring(file.getPath().lastIndexOf("."));
        mBitmap = BitmapUtil.adjustBitmap(file.getAbsolutePath());
        byte[] bytes = BitmapUtil.compressByQuality(mBitmap, 250 * 1024, true);
        String fileUrl = "feedback/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc", fileUrl, bytes);
        put.setCallbackParam(new HashMap<String, String>() {
            {
                // 图片 callbackUrl
                // put("callbackUrl", BaseVar.ADDPHOTO + "?fid=" + CommonInit.fid + "&uid=" + CommonInit.uid);
                // put("callbackBody", "object=${object}");
            }
        });
        ObjectMetadata metadata = new ObjectMetadata();
        HashMap<String, String> userMetadata = new HashMap<>();
        userMetadata.put("type", type);
        userMetadata.put("serviceType", serviceType);
        userMetadata.put("fileUrl", fileUrl);
        metadata.setUserMetadata(userMetadata);
        put.setMetadata(metadata);
        //   异步上传时可以设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> LogUtil.e("currentSize: " + currentSize + " totalSize: " + totalSize));
        if (mOss != null) {
            OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    if (mStringBuilder == null) {
                        mStringBuilder = new StringBuilder();
                    }
                    Map<String, String> stringMap = request.getMetadata().getUserMetadata();
                    mStringBuilder.append(stringMap.get("fileUrl"));
                    mStringBuilder.append(",");
                    ++curNum;
                    LogUtil.e(curNum + "---------------" + size);
                    if (curNum > size) {
                        EventBus.getDefault().post(new FileUpdateOkEvent());
                    }
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常
                    if (clientExcepion != null) {
                        // 本地异常如网络异常等
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {

                    }
                }
            });
        }
    }

}
