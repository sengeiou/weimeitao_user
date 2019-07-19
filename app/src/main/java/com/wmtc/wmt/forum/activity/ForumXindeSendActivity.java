package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.HotSearchAdapter;
import com.wmtc.wmt.forum.adapter.PicsAdapter;
import com.wmtc.wmt.forum.event.FileUpdateOkEvent;
import com.wmtc.wmt.forum.event.FinishSendStartEvent;
import com.wmtc.wmt.forum.event.LocalSelEvent;
import com.wmtc.wmt.forum.event.TopicSelEvent;
import com.wmtc.wmt.forum.pojo.SendPojo;
import com.wmtc.wmt.forum.presenter.ForumSendXindePresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumXindeSendActivity extends SuperBaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.tvBack)
    TextView mTvBack;
    @BindView(R.id.tvPreSave)
    TextView mTvPreSave;
    @BindView(R.id.tvSave)
    TextView mTvSave;
    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.recyclerViewPics)
    RecyclerView mRecyclerViewPics;
    @BindView(R.id.edContent)
    EditText mEdContent;
    @BindView(R.id.editTitle)
    EditText mEditTitle;
    @BindView(R.id.tvHot)
    TextView mTvHot;
    @BindView(R.id.llLocal)
    LinearLayout mLlLocal;
    @BindView(R.id.llHeji)
    LinearLayout mLlHeji;
    @BindView(R.id.tvLocal)
    TextView mTvLocal;
    @BindView(R.id.tagFlow)
    TagFlowLayout mTagFlow;
    private Unbinder mBind;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private SendPojo mSendPojo;
    private ArrayList<File> mFileArrayList;
    private PicsAdapter mAdapter;
    private int maxPicCamera = 9;
    private View mFooter;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private String mType;
    private ForumSendXindePresenter mPresenter;
    private File mBannerFile;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_finish_send;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).keyboardEnable(true).init();
    }

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        mBind = ButterKnife.bind(this);
        requestLocation();
        mPresenter = new ForumSendXindePresenter(this);
        mPresenter.ossToken();
        mTvLocal.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, NearByLocalActivity.class, "");
        });
        mTvHot.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, TopicActivity.class, "添加话题");
        });
        mTvBack.setOnClickListener(v -> finish());
        mType = mBundle.getString("type");
        if ("xinde".equals(mType)) {
            mRecyclerViewPics.setVisibility(View.VISIBLE);
            mLlHeji.setVisibility(View.GONE);
            mFileArrayList = (ArrayList<File>) mBundle.getSerializable("file");
        } else {
            mRecyclerViewPics.setVisibility(View.GONE);
            mLlHeji.setVisibility(View.VISIBLE);
            mFileArrayList = new ArrayList<>();
            mBannerFile = (File) mBundle.getSerializable("banner");
            Glide.with(mActivity).load(mBannerFile)
                    .apply(GlideUtils.init().roundTransFrom(mActivity, 10, R.drawable.wmt_default))
                    .into(ivBanner);
        }
        if (mFileArrayList != null) {
            maxPicCamera -= mFileArrayList.size();
        } else {
            mFileArrayList = new ArrayList<>();
        }
        mRecyclerViewPics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new PicsAdapter(mFileArrayList);
        mRecyclerViewPics.setAdapter(mAdapter);

        mSendPojo = new SendPojo();
        mSendPojo.userId = WmtApplication.id;
        mSendPojo.userType = WmtApplication.type;
        mFooter = View.inflate(this, R.layout.adapter_footer_create_pro_pic, null);
        mFooter.findViewById(R.id.ivPicUpload).setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCameraWithSize(this.mActivity,
                            maxPicCamera))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });

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

        mAdapter.addFooterView(mFooter);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                mAdapter.remove(position);
                maxPicCamera = 9 - mAdapter.getData().size();
                if (mAdapter.getFooterLayoutCount() < 1 && mAdapter.getData().size() < maxPicCamera) {
                    mAdapter.addFooterView(mFooter);
                }
            }
            return false;
        });
        Observable.timer(1, TimeUnit.SECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> {
                    EventBus.getDefault().post(new FinishSendStartEvent());
                });
        mTvPreSave.setOnClickListener(v -> {
            saveSend("5");
        });
        mTvSave.setOnClickListener(v -> {
            saveSend("");
        });
    }

    @SuppressLint("CheckResult")
    private void saveSend(String status) {
        if (mType.equals("heji") && StringUtils.init().isEmpty(mEditTitle)) {
            ToastUtils.init().showInfoToast(mActivity, "请心得标题");
            return;
        }
        if (StringUtils.init().isEmpty(mEdContent)) {
            ToastUtils.init().showInfoToast(mActivity, "请分享一下您的护肤小秘诀");
            return;
        }
        mSendPojo.status = status;

        if (mType.equals("xinde")) {
            List<File> data = mAdapter.getData();
            size = data.size() - 1;
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();

            Observable.fromIterable(data).subscribe(file -> {
                updatePhoto(file, mType, "");
            });
        } else {
            size = 0;
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();
            updatePhoto(mBannerFile, mType, "");
        }
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
                maxPicCamera = 9 - mFileArrayList.size();
                mAdapter.setNewData(mFileArrayList);
            });
        }
    }


    private void requestLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationOption.setNeedAddress(true);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    @Subscribe
    public void onEvent(TopicSelEvent event) {
        mTvHot.setText(event.title.replace("#", ""));
        mSendPojo.topicList = mTvHot.getText().toString();
    }


    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {
        String string = mStringBuilder.toString();
        if (mStringBuilder.lastIndexOf(",") == mStringBuilder.length() - 1) {
            string = string.substring(0, string.length() - 1);
        }
        mSendPojo.content = StringUtils.init().fixNullStr(mEdContent.getText());
        LogUtil.e(mSendPojo);

        if (mType.equals("xinde")) {
            mSendPojo.xindeBannerList = Arrays.asList(string.split(","));
            mPresenter.saveXinde(mSendPojo);
        } else {
            mSendPojo.title = StringUtils.init().fixNullStr(mEditTitle.getText());
            mSendPojo.bannerUrl = string;
            mPresenter.saveHeji(mSendPojo);
        }
    }


    @Subscribe
    public void onEvent(LocalSelEvent event) {
        LogUtil.e(event);
        mTvLocal.setText(event.local);
        mSendPojo.positionLongitude = event.lot;
        mSendPojo.positionLatitude = event.lat;
        mSendPojo.positionName = event.local;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtil.e("=================");
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double lat = aMapLocation.getLatitude();//获取纬度
                double lot = aMapLocation.getLongitude();//获取经度
                requestPOI(lat, lot, aMapLocation.getCity());
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtil.e("++++++++++");
        ArrayList<PoiItem> poiResultPois = poiResult.getPois();
        if (poiResultPois != null && poiResultPois.size() > 4) {
            ArrayList<String> list = new ArrayList<>();
            mLlLocal.setVisibility(View.VISIBLE);
            Observable.fromIterable(poiResultPois.subList(0, 4)).map(PoiItem::getTitle).subscribe(list::add);
            mTagFlow.setAdapter(new HotSearchAdapter(list));
            mTagFlow.setOnTagClickListener((view, position, parent) -> {
                mTvLocal.setText(list.get(position));
                return false;
            });
            mTvLocal.setText(list.get(0));
            PoiItem poiItem = poiResultPois.get(0);
            mSendPojo.positionLongitude = poiItem.getLatLonPoint().getLongitude() + "";
            mSendPojo.positionLatitude = poiItem.getLatLonPoint().getLatitude() + "";
            mSendPojo.positionName = poiItem.getTitle();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtil.e("----------");
        LogUtil.e(poiItem);
    }

    private void requestPOI(double lat, double lot, String city) {
        LatLonPoint latLonPoint = new LatLonPoint(lat, lot);
        PoiSearch.Query query = new PoiSearch.Query("", "", city);
        query.setPageSize(20);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 5000));
        poiSearch.searchPOIAsyn();
    }


    public void sendOk() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        ToastUtils.init().showInfoToast(mActivity, "上传成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }


    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }


    private int curNum = 0;
    private Bitmap mBitmap;
    private int size;
    private OSSClient mOss;
    private StringBuilder mStringBuilder;

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
        String fileUrl = type + "/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc", fileUrl, bytes);
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
