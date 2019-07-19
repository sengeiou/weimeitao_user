package com.wmtc.wmt.forum.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.wmtc.wmt.appoint.adapter.SkinBkInfoAdapter;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.HotSearchAdapter;
import com.wmtc.wmt.forum.dialog.EditHejiDialog;
import com.wmtc.wmt.forum.event.FileUpdateOkEvent;
import com.wmtc.wmt.forum.event.FinishSendStartEvent;
import com.wmtc.wmt.forum.event.LocalSelEvent;
import com.wmtc.wmt.forum.event.SendRijiEvent;
import com.wmtc.wmt.forum.event.TopicSelEvent;
import com.wmtc.wmt.forum.pojo.SendPojo;
import com.wmtc.wmt.forum.presenter.ForumSendHejiPresenter;
import com.wmtc.wmt.personal.pojo.FeedBackPojo;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.richeditor.RichEditor;
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
 * Created by Obl on 2019/6/11.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumHejiSendActivity extends SuperBaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.tvBanner)
    TextView mTvBanner;
    @BindView(R.id.ivBanner)
    ImageView mIvBanner;
    @BindView(R.id.editTitle)
    EditText mEditTitle;
    @BindView(R.id.llHeji)
    LinearLayout mLlHeji;
    @BindView(R.id.editor)
    RichEditor mEditor;
    @BindView(R.id.tvHot)
    TextView mTvHot;
    @BindView(R.id.tvLocal)
    TextView mTvLocal;
    @BindView(R.id.llLocal)
    LinearLayout mLlLocal;
    @BindView(R.id.tagFlow)
    TagFlowLayout mTagFlow;
    private SendPojo mSendPojo;
    private SkinBkInfoAdapter mAdapter;
    private View mHeader;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private Unbinder mBind;
    private ForumSendHejiPresenter mPresenter;
    private File mBannerFile;
    private View mLlBottom;
    private TextView mTvPic;
    private String mType;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_heji_send;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).keyboardEnable(true).setOnKeyboardListener((isPopup, keyboardHeight) -> {
            if (isPopup) {
                if (mEditor.hasFocus()) {
                    int[] outLocation = new int[2];
                    mEditor.getLocationOnScreen(outLocation);
                    LogUtil.e(outLocation);
                    LogUtil.e("--------------------------");
                    Observable.timer(500, TimeUnit.MILLISECONDS)
                            .compose(new IoMainSchedule<>())
                            .subscribe(aLong1 -> {
                                int bottomHeight = outLocation[1] + mEditor.getHeight();
                                if (keyboardHeight < bottomHeight) {
                                    mRecyclerView.smoothScrollBy(0, bottomHeight - keyboardHeight + 100);
                                    mEditor.focusEditor();

                                }
                            });
                    showPicDialog();
                } else if (mEditTitle.hasFocus()) {
                    int[] outLocation = new int[2];
                    mEditTitle.getLocationOnScreen(outLocation);
                    Observable.timer(500, TimeUnit.MILLISECONDS)
                            .compose(new IoMainSchedule<>())
                            .subscribe(aLong1 -> {
                                int bottomHeight = outLocation[1];
                                if (keyboardHeight < bottomHeight) {
                                    mRecyclerView.smoothScrollBy(0, bottomHeight - keyboardHeight + 100);
                                }
                            });

                }
            } else {
                dismissPicDialog();
            }
        }).init();
    }

    private void dismissPicDialog() {
        mLlBottom.setVisibility(View.INVISIBLE);
    }

    private void showPicDialog() {
        mLlBottom.setVisibility(View.VISIBLE);
        mTvPic.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(mActivity))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mType = mBundle.getString("type");
        mLlBottom = view.findViewById(R.id.llBottom);
        mTvPic = mLlBottom.findViewById(R.id.tvPic);
        mHeader = View.inflate(mActivity, R.layout.header_heji, null);
        mBind = ButterKnife.bind(this, mHeader);
        mEditor.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showPicDialog();
            } else {
                dismissPicDialog();
            }
        });
        mSendPojo = new SendPojo();
        mSendPojo.userId = WmtApplication.id;
        mSendPojo.userType = WmtApplication.type;
        requestLocation();
        EventBus.getDefault().register(this);
        mPresenter = new ForumSendHejiPresenter(this);
        mPresenter.ossToken();
        mBannerFile = (File) mBundle.getSerializable("banner");
        Glide.with(mActivity).load(mBannerFile)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvBanner);
        mAdapter = new SkinBkInfoAdapter(null);

        mAdapter.addHeaderView(mHeader);
        mRecyclerView.setAdapter(mAdapter);
        //初始化编辑高度
        mEditor.setEditorHeight(150);
        //初始化字体大小
        mEditor.setEditorFontSize(14);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.parseColor("#666666"));
        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置默认显示语句
        mEditor.setPlaceholder("分享您的生美小常识 比如有趣的护肤体验，新奇有效的护肤小秘诀...");
        //设置编辑器是否可用
        mEditor.setInputEnabled(true);

        Observable.timer(1, TimeUnit.SECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> {
                    EventBus.getDefault().post(new FinishSendStartEvent());
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

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());
        mTvLocal.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, NearByLocalActivity.class, "");
        });
        mTvHot.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, TopicActivity.class, "添加话题");
        });
        view.findViewById(R.id.tvPreSave).setOnClickListener(v -> {
            saveSend("5");
        });
        view.findViewById(R.id.tvSave).setOnClickListener(v -> {
            saveSend("");
        });
    }

    private void saveSend(String status) {
        if (StringUtils.init().isEmpty(mEditTitle)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入护肤标题");
            return;
        }
        if (StringUtils.isBlank(mEditor.getHtml())) {
            ToastUtils.init().showInfoToast(mActivity, "请分享一下您的护肤小秘诀");
            return;
        }
        mSendPojo.status = status;
        if (mLoading == null) {
            mLoading = new DialogLoading(this);
        }
        mLoading.show();
        updatePhoto(mBannerFile, mType, "2");
        mSendPojo.title = StringUtils.init().fixNullStr(mEditTitle.getText());
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

    @Subscribe
    public void onEvent(TopicSelEvent event) {
        mTvHot.setText(event.title.replace("#", ""));
        mSendPojo.topicList = mTvHot.getText().toString();
    }

    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {
        if (event.fileUrl.contains(mType + "Image")) {
            Observable.just(1).compose(new IoMainSchedule<>()).subscribe(integer -> {
                mEditor.focusEditor();
                LogUtil.e(event.fileUrl + "------------------------");
                String fileAbsolutePath = WmtApplication.url_host + event.fileUrl;
                mEditor.insertImage(fileAbsolutePath, fileAbsolutePath + "\" style=\"width:100%; margin-top:10px;margin-bottom:10px;");
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>())
                        .subscribe(aLong -> {
                            System.out.println(mEditor.getHtml());
                            mEditor.setHtml(mEditor.getHtml() + "&nbsp;");
                            mEditor.focusEditor();
                        }, throwable -> {
                        });
            }, throwable -> {
            });
        } else {
            mSendPojo.bannerUrl = event.fileUrl;
            mSendPojo.content = mEditor.getHtml();
            if (mType.equals("heji")) {
                mPresenter.saveHeji(mSendPojo);
            } else {
                mSendPojo.orderId = mBundle.getString("orderId");
                mPresenter.saveRiji(mSendPojo);
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }


    public void sendOk() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        ToastUtils.init().showInfoToast(mActivity, "上传成功");
        if ("riji".equals(mType)) {
            EventBus.getDefault().post(new SendRijiEvent());
        }
        finish();
    }

    private DialogLoading mLoading;
    public boolean isZiped = true;
    public ArrayList<File> mArrayList = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mArrayList != null) {
            mArrayList.clear();
        }
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
                    mArrayList.add(file);
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                size = 0;
                updatePhoto(files.get(0), mType + "Image", "2");
            });
        }
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
                        EventBus.getDefault().post(new FileUpdateOkEvent(stringMap.get("fileUrl")));
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
