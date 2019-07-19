package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.PicsAdapter;
import com.wmtc.wmt.forum.adapter.ReportAdapter;
import com.wmtc.wmt.forum.event.FileUpdateOkEvent;
import com.wmtc.wmt.forum.pojo.AccusationPojo;
import com.wmtc.wmt.forum.presenter.ReportPresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/6/24.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ReportActivity extends CommonWmtActivity {

    private ReportPresenter mPresenter;
    private ReportAdapter mAdapter;
    private View mFooter;
    private EditText mEditResume;
    private TextView mTvCommit;
    private RecyclerView mRecyclerViewFooter;
    private ArrayList<File> mFiles;
    private PicsAdapter mFooterAdapter;
    private View mFooterAdd;
    private AccusationPojo mPojo;

    @Override
    public int initAddLayout() {
        return R.layout.activity_report;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        EventBus.getDefault().register(this);
        mPresenter = new ReportPresenter(this);
        mPresenter.accusation();
        mPresenter.ossToken();
        mPojo = new AccusationPojo();
        mPojo.accusationType = mBundle.getString("type");
        mPojo.accusationTypeId = mBundle.getString("id");
        mAdapter = new ReportAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mFooter = View.inflate(mActivity, R.layout.footer_report, null);
        mAdapter.addFooterView(mFooter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        bean.isSel = false;
                    });
            DictListBean.DataBean dataBean = mAdapter.getData().get(position);
            dataBean.isSel = true;
            mAdapter.notifyDataSetChanged();
        });

        mEditResume = mFooter.findViewById(R.id.editResume);
        mTvCommit = mFooter.findViewById(R.id.tvCommit);
        mRecyclerViewFooter = mFooter.findViewById(R.id.recyclerViewFooter);
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
                if (mFooterAdapter.getData().size() >= 4) {
                    mFooterAdapter.removeAllFooterView();
                }
            }
            return false;
        });
        mFooterAdd = View.inflate(mActivity, R.layout.footer_add, null);
        mFooterAdd.findViewById(R.id.ivItemSrc).setOnClickListener(v -> {

            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCameraWithSize(mActivity, 4 - mFooterAdapter.getData().size()))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        mFooterAdapter.addFooterView(mFooterAdd);

        mTvCommit.setOnClickListener(v -> {

            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        if (bean.isSel) {
                            mPojo.accusationTitleId = bean.id;
                            mPojo.accusationTitleName = bean.name;
                        }
                    });
            if (StringUtils.isBlank(mPojo.accusationTitleId)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择举报理由");
                return;
            }
            if (mLoading == null) {
                mLoading = new DialogLoading(mActivity);
            }
            mLoading.show();
            if (mFooterAdapter.getData().size() > 0) {
                size = mFooterAdapter.getData().size() - 1;
                Observable.fromIterable(mFooterAdapter.getData())
                        .subscribe(file -> {
                            updatePhoto(file, "1", "jubaoImage");
                        });
            } else {
                mPojo.accusationContent = mEditResume.getText().toString();
                mPresenter.saveAccusation(mPojo);
            }

        });

    }


    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {

        Observable.just(1).compose(new IoMainSchedule<>()).subscribe(integer -> {
            String s = mStringBuilder.toString();
            if (StringUtils.isNotBlank(s)) {
                if (s.contains(",")) {
                    mPojo.accusationPics = s.substring(0, s.length() - 1);
                } else {
                    mPojo.accusationPics = s;
                }
            }
            mPresenter.saveAccusation(mPojo);
        }, throwable -> {
        });

    }


    public void getList(DictListBean bean) {
        if (bean != null && bean.data != null) {
            mAdapter.setNewData(bean.data);
        }
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
                if (mFooterAdapter.getData().size() >= 4) {
                    mFooterAdapter.removeAllFooterView();
                }
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void saveAccusation() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        ToastUtils.init().showInfoToast(mActivity, "感谢您的反馈，工作人员将对你所举报的评论进行相应的处理，感谢您的支持！");
        finish();
    }


    private volatile int curNum = 0;
    private Bitmap mBitmap;
    private volatile int size;
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
