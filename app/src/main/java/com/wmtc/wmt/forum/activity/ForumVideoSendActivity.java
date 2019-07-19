package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.EmptyUiVideoPlayer;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.event.FileUpdateOkEvent;
import com.wmtc.wmt.forum.pojo.SendPojo;
import com.wmtc.wmt.forum.presenter.ForumVideoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/6/22.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumVideoSendActivity extends SuperBaseActivity {
    EmptyUiVideoPlayer mVideoPlayer;
    private ImageView mIvVideoImg;
    public boolean isPlay = false;
    public DialogLoading mLoading;
    private EditText mEditTitle;
    private ForumVideoPresenter mPresenter;
    private SendPojo mSendPojo;
    private String mVideoPath;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).keyboardEnable(true).init();
    }

    @Override
    protected int initRootLayout() {
        return R.layout.forum_video_send;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new ForumVideoPresenter(this);
        EventBus.getDefault().register(this);
        mPresenter.ossToken();
        mVideoPlayer = findViewById(R.id.player);
        mEditTitle = findViewById(R.id.editTitle);
        mSendPojo = new SendPojo();
        mSendPojo.userId = WmtApplication.id;
        mSendPojo.userType = WmtApplication.type;
        mVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        mVideoPlayer.getBackButton().setVisibility(View.GONE);
        //增加封面
        mIvVideoImg = new ImageView(this);
        mIvVideoImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mVideoPlayer.setThumbImageView(mIvVideoImg);
        mVideoPath = mBundle.getString("videoPath");
        LogUtil.e(mVideoPath + "12312312312");
        if (StringUtils.isNotBlank(mVideoPath)) {
            initVideo(mVideoPath);
            Observable.just(mVideoPath).subscribeOn(Schedulers.io())
                    .map(BitmapUtil::getVideoThumb)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        mIvVideoImg.setImageBitmap(bitmap);
                    }, throwable -> {
                    });
        }


        mEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 30) {
                    ToastUtils.init().showErrorToast(mActivity, "标题长度不可超过30字");
                    mEditTitle.setText(s.subSequence(0, 30));
                    mEditTitle.setSelection(30);
                }
            }
        });
        findViewById(R.id.tvSave).setOnClickListener(v -> {
            saveSend("");
        });
        findViewById(R.id.tvPreSave).setOnClickListener(v -> {
            saveSend("5");
        });
    }

    private void saveSend(String status) {
        if (StringUtils.init().isEmpty(mEditTitle)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入短视频标题");
            return;
        }
        if (mLoading == null) {
            mLoading = new DialogLoading(this);
        }
        mLoading.show();
        mSendPojo.title = mEditTitle.getText().toString();
        mSendPojo.status = status;
        updateVideo(mVideoPath, "1", "video");
    }

    private void initVideo(String url) {
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setCachePath(new File(url))
                .setLooping(true)
                .build(mVideoPlayer);
    }

    private int curNum = 0;
    private OSSClient mOss;
    private StringBuilder mStringBuilder;

    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {

        Observable.just(1).compose(new IoMainSchedule<>())
                .subscribe(integer -> {
                    mSendPojo.videoUrl = event.fileUrl;
                    mPresenter.saveVideo(mSendPojo);
                }, throwable -> {

                });
    }

    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }

    public void updateVideo(String filePath, String type, String serviceType) {
        String md5 = MD5Utils.getMD5(filePath);
        String ext = filePath.substring(filePath.lastIndexOf("."));
        String fileUrl = serviceType + "/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc", fileUrl, filePath);
        put.setCallbackParam(new HashMap<String, String>() {
            {

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
                    LogUtil.e(curNum + "---------------");
                    EventBus.getDefault().post(new FileUpdateOkEvent(fileUrl, serviceType));
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


    @Override
    public void onPause() {
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayer.getCurrentPlayer().release();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void sendOk() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        ToastUtils.init().showSuccessToast(mActivity, "上传成功");
        finish();
    }
}
