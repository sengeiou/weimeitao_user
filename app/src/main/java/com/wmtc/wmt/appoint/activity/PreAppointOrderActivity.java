package com.wmtc.wmt.appoint.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.PreOrderBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.appoint.bean.TimeSelBean;
import com.wmtc.wmt.appoint.event.PayOkEvent;
import com.wmtc.wmt.appoint.event.TeachSelEvent;
import com.wmtc.wmt.appoint.pojo.PreOrderPojo;
import com.wmtc.wmt.appoint.pojo.ShopTeachPojo;
import com.wmtc.wmt.appoint.presenter.ProAppointOrderPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PreAppointOrderActivity extends CommonWmtActivity {
    @BindView(R.id.ivProPic)
    ImageView mIvProPic;
    @BindView(R.id.tvProTitle)
    TextView mTvProTitle;
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvToTime)
    TextView mTvToTime;
    @BindView(R.id.tvWhoServer)
    TextView mTvWhoServer;
    @BindView(R.id.tvManNum)
    TextView mTvManNum;
    @BindView(R.id.tvMsg)
    EditText mTvMsg;
    @BindView(R.id.tvFirstPrice)
    TextView mTvFirstPrice;
    @BindView(R.id.tvEndPrice)
    TextView mTvEndPrice;
    @BindView(R.id.tvToAppoint)
    TextView mTvToAppoint;
    private Unbinder mBind;
    private ProAppointOrderPresenter mPresenter;
    private ProjectInfoBean.DataBean mProInfo;
    private TeachSelBean.DataBean mTeachSelBean;
    private OptionsPickerView mPickerView;
    private List<String> mOption0;
    private List<List<String>> mOption1;
    private List<List<List<String>>> mOption2;
    private List<TimeSelBean.DataBean> mTimeSelBean;
    private String arrivalTime = "";

    @Override

    public int initAddLayout() {
        return R.layout.activity_pre_appoint_order;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new ProAppointOrderPresenter(this);
        mProInfo = mBundle.getParcelable("proInfo");
        mTvFirstPrice.setText(StringUtils.init().fixNullStrMoney(mProInfo.pPriceFirst + "", "￥"));
        mTvEndPrice.setText(StringUtils.init().fixNullStrMoney(mProInfo.pPriceEnd + "", "到店再付：￥"));
        mTvProTitle.setText(StringUtils.init().fixNullStr(mProInfo.pTitle));
        mTvPrice.setText(StringUtils.init().fixNullStrMoney(mProInfo.pPrice + "", "￥"));
        List<String> banner = mProInfo.banner;
        if (banner != null && banner.size() > 0) {
            Glide.with(mActivity)
                    .load(banner.get(0))
                    .apply(GlideUtils.init().roundTransFrom(mActivity, 5))
                    .into(mIvProPic);
        }
        mTvWhoServer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", mProInfo.shopId);
            bundle.putString("arrivalTime", arrivalTime);
            ActivityUtils.init().start(mActivity, SelectTeachActivity.class, "技师选择", bundle);
        });
        mTvToTime.setOnClickListener(v -> {
            ShopTeachPojo pojo = new ShopTeachPojo();
            pojo.shopId = mProInfo.shopId;
            pojo.userId = WmtApplication.id;
            pojo.needTime = mProInfo.pPrpoTime;
            if (mTeachSelBean != null) {
                pojo.teachId = mTeachSelBean.id + "";
            }
            mPresenter.timeSel(pojo);
        });
        mPickerView = initTimePicker();
        mTvToAppoint.setOnClickListener(v -> {
            if (StringUtils.isBlank(arrivalTime)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择预约时间");
                return;
            }
            PreOrderPojo pojo = new PreOrderPojo();
            pojo.proId = mProInfo.id;
            pojo.arrivalTime = arrivalTime;
            pojo.customerNum = "1";
            pojo.leavingMsg = mTvMsg.getText().toString();
            pojo.shopName = mProInfo.shopInfo.shopName;
            if (mTeachSelBean != null) {
                pojo.teachId = mTeachSelBean.id + "";
            }
            pojo.uid = WmtApplication.id;

            mPresenter.preOrder(pojo);

        });
    }

    public void preOrder(PreOrderBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.data.id);
        ActivityUtils.init().start(mActivity, CheckPayActivity.class, "检查并支付", bundle);
    }

    public void timeSel(TimeSelBean timeSelBean) {
        mTimeSelBean = timeSelBean.data;
        mOption0.clear();
        mOption1.clear();
        mOption2.clear();
        if (mTimeSelBean != null && mTimeSelBean.size() > 0) {
            for (int i = 0; i < mTimeSelBean.size(); i++) {
                TimeSelBean.DataBean day = mTimeSelBean.get(i);
                mOption0.add(day.daysFix + day.alias);
                List<String> hour = new ArrayList<>();
                List<String> timesKeys = day.timesKeys;
                List<List<String>> minute = new ArrayList<>();
                List<List<String>> timesValues = day.timesValues;
                if (timesKeys != null && timesKeys.size() > 0) {
                    hour.addAll(timesKeys);
                } else {
                    List<String> newAdd = new ArrayList<>();
                    newAdd.add("--");
                    hour.addAll(newAdd);
                }
                if (timesValues != null && timesValues.size() > 0) {
                    minute.addAll(timesValues);
                } else {
                    List<String> newAdd = new ArrayList<>();
                    newAdd.add("--");
                    minute.add(newAdd);
                }
                mOption1.add(hour);
                mOption2.add(minute);
            }
        }

        mPickerView.setPicker(mOption0, mOption1, mOption2);
        if (!mPickerView.isShowing()) {
            mPickerView.show();
        }
    }

    private OptionsPickerView initTimePicker() {

        mOption0 = new ArrayList<>();
        mOption1 = new ArrayList<>();
        mOption2 = new ArrayList<>();
        return new OptionsPickerView.Builder(mActivity,
                (options1, options2, options3, v) -> {
                    String mDay = "--";
                    String mHour = "--";
                    String mMinute = "--";
                    if (mTimeSelBean != null) {
                        TimeSelBean.DataBean day = mTimeSelBean.get(options1);
                        List<String> hour = day.timesKeys;
                        List<List<String>> minute = day.timesValues;
                        LogUtil.e(day);
                        mDay = day.daysFix + day.alias;
                        if (hour != null && hour.size() > options2) {
                            LogUtil.e(hour.get(options2));
                            mHour = hour.get(options2);
                        } else {
                            LogUtil.e("--");
                        }
                        if (minute != null && minute.size() > options2) {
                            List<String> strings = minute.get(options2);
                            if (strings != null && strings.size() > options3) {
                                LogUtil.e(strings.get(options3));
                                mMinute = strings.get(options3);
                            } else {
                                LogUtil.e("--");
                            }
                        } else {
                            LogUtil.e("--");
                        }
                        String selTime = mDay + " " + mHour + mMinute;
                        if (selTime.contains("--")) {
                            ToastUtils.init().showErrorToast(mActivity, "当前时间不可选");
                            return;
                        }
                        mTvToTime.setText(selTime);
                        arrivalTime = day.days + " " + mHour + mMinute;
                        LogUtil.e(arrivalTime);
                    }
                })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubmitColor(getResources().getColor(top.jplayer.baseprolibrary.R.color.colorBlackGold))
                .setCancelColor(getResources().getColor(top.jplayer.baseprolibrary.R.color.grey))
                .setSubCalSize(18)//确定和取消文字大小
                .setContentTextSize(21)//滚轮文字大小
                .setDividerColor(Color.DKGRAY)
                .setCyclic(false, false, false)//循环与否
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvnet(TeachSelEvent event) {
        mTeachSelBean = event.dataBean;
        if (mTeachSelBean != null) {
            mTvWhoServer.setText(mTeachSelBean.technicianName);
        }
    }

    @Subscribe
    public void onEvnet(PayOkEvent event) {
        finish();
    }

    public void proInfo(ProjectInfoBean bean) {

    }

}
