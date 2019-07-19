package com.wmtc.wmt.forum.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.wmtc.wmt.R;
import com.wmtc.wmt.forum.event.LocalSelEvent;
import com.wmtc.wmt.forum.adapter.NearByAdapter;
import com.wmtc.wmt.forum.event.TopicSelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;

/**
 * Created by Obl on 2019/4/25.
 * com.wmtc.wmt.mvui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NearByLocalActivity extends SuperBaseActivity implements PoiSearch.OnPoiSearchListener, AMapLocationListener {
    private int page = 1;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private NearByAdapter mAdapter;
    private double lat = 36.0897530000;
    private double lot = 120.3786230000;
    private String search;
    private String city;
    private TextView mTvCurLocal;
    private TextView mTvResetLocal;
    private EditText mEditSearch;
    private PickerUtils mPickerUtils;
    private LinearLayout mLlTvSel;
    private TextView mTvSelLocal;
    private String local;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_nearby_local;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        showLoading();
        mTvCurLocal = view.findViewById(R.id.tvCurLocal);
        mEditSearch = view.findViewById(R.id.editSearch);
        mLlTvSel = view.findViewById(R.id.llTvSel);
        mTvSelLocal = view.findViewById(R.id.tvSelLocal);
        mPickerUtils = new PickerUtils();
        mTvResetLocal = view.findViewById(R.id.tvResetLocal);
        responseLocation();
        responsePOI(lat, lot, search, city, page, true);
        mAdapter = new NearByAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            responsePOI(lat, lot, search, city, ++page, false);
        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PoiItem poiItem = mAdapter.getData().get(position);
            local = poiItem.getTitle();
            this.lat = poiItem.getLatLonPoint().getLatitude();
            this.lot = poiItem.getLatLonPoint().getLongitude();
            EventBus.getDefault().post(new LocalSelEvent(local, this.lat + "", this.lot + "", poiItem.getCityName()));
            finish();
        });
        mTvResetLocal.setOnClickListener(v -> {
            if (null != mLocationClient) {
                mLocationClient.setLocationOption(mLocationOption);
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
            }
        });
        mEditSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                BGAKeyboardUtil.closeKeyboard(mActivity);
                search = mEditSearch.getText().toString();
                page = 1;
                responsePOI(lat, lot, search, city, page, true);
            }
            return false;
        });
        mPickerUtils.initLocalPicker(mActivity, list -> {
            String name = list.get(2).name;
            mTvSelLocal.setText(name);
            city = name;
        });
        mLlTvSel.setOnClickListener(v -> {
            mPickerUtils.localShow(mActivity);
        });
    }

    private void responseLocation() {
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

    private void responsePOI(double lat, double lot, String search, String city, int page, boolean isSearch) {
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        LatLonPoint latLonPoint = new LatLonPoint(lat, lot);
        PoiSearch.Query query = new PoiSearch.Query(search, "", city);
        query.setPageSize(20);
        query.setPageNum(page);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        if (!isSearch) {
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 5000));
        }
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        page = 1;
        search = "";
        mEditSearch.setText("");
        responsePOI(lat, lot, search, city, page, false);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtil.e("++++++++++");
        responseSuccess();
        ArrayList<PoiItem> poiResultPois = poiResult.getPois();
        if (page == 1) {
            mAdapter.setNewData(poiResultPois);
            if (poiResultPois.size() > 0) {
                local = poiResultPois.get(0).getTitle();
            }
        } else {
            mSmartRefreshLayout.finishLoadMore();
            mAdapter.addData(poiResultPois);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtil.e("----------");
        LogUtil.e(poiItem);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtil.e("=================");
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lot = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String address = amapLocation.getAddress();
                mTvCurLocal.setText(address);
                page = 1;
                city = amapLocation.getDistrict();
                mTvSelLocal.setText(city);
                responsePOI(lat, lot, search, city, page, false);
                SharePreUtil.saveData(mActivity, "longlat", lot + "," + lat);
                SharePreUtil.saveData(mActivity, "aoi", amapLocation.getAoiName());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }
}
