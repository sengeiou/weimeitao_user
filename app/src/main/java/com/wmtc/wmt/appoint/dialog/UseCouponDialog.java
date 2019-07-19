package com.wmtc.wmt.appoint.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.event.SelCouponEvent;
import com.wmtc.wmt.appoint.event.SelHbEvent;
import com.wmtc.wmt.forum.adapter.UseCouponAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialogFragment;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class UseCouponDialog extends BaseCustomDialog {

    private TextView mTvCouponUse;
    private TextView mTvCouponNotUse;
    private RecyclerView mRecyclerView;
    private TextView mTvSure;
    private TextView mTvDelMoney;
    private UseCouponAdapter mAdapter;
    public boolean isNotUse = false;
    private CanCouponBean.DataBean.ListBean mListBean;
    private String type;

    public UseCouponDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mTvCouponUse = view.findViewById(R.id.tvCouponUse);
        mTvCouponNotUse = view.findViewById(R.id.tvCouponNotUse);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvSure = view.findViewById(R.id.tvSure);
        mTvDelMoney = view.findViewById(R.id.tvDelMoney);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new UseCouponAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (!isNotUse) {
                mListBean = mAdapter.getData().get(position);
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    if (i == position) {
                        mAdapter.getData().get(i).isSel = true;
                    } else {
                        mAdapter.getData().get(i).isSel = false;
                    }
                }
                mAdapter.notifyDataSetChanged();
                mTvDelMoney.setText(String.format(Locale.CHINA, "（共省 ￥%s）",
                        StringUtils.init().fixNullStrMoney(mListBean.discountedAmount)));
            }
            return false;
        });
        mTvSure.setOnClickListener(v -> {
            if (mListBean == null) {
                ToastUtils.init().showInfoToast(getContext(), "请选择优惠券");
                return;
            }
            if ("红包".equals(type)) {
                EventBus.getDefault().post(new SelHbEvent(mListBean));
            } else {
                EventBus.getDefault().post(new SelCouponEvent(mListBean));
            }
            dismiss();
        });
    }

    @SuppressLint("CheckResult")
    public void setBean(CanCouponBean.DataBean dataBean) {
        List<CanCouponBean.DataBean.ListBean> cannot = dataBean.cannot;
        List<CanCouponBean.DataBean.ListBean> can = dataBean.can;
        int canNotSize;
        if (cannot != null) {
            canNotSize = cannot.size();
        } else {
            canNotSize = 0;
        }
        mTvCouponNotUse.setText(String.format(Locale.CHINA, "不可使用%s（%d）", type, canNotSize));

        int canSize;
        if (can != null) {
            canSize = can.size();
            Observable.fromIterable(can).subscribe(listBean -> {
                if (listBean.isSel) {
                    mListBean = listBean;
                    mTvDelMoney.setText(String.format(Locale.CHINA, "（共省 ￥%s）",
                            StringUtils.init().fixNullStrMoney(listBean.discountedAmount)));
                    return;
                }
            });
        } else {
            canSize = 0;
        }
        mTvCouponUse.setText(String.format(Locale.CHINA, "可使用%s（%d）", type, canSize));
        if (can != null) {
            mAdapter.setNewData(can);
        }
        mTvCouponUse.setOnClickListener(v -> {
            if (can != null) {
                mAdapter.setNewData(can);
            }
            mTvCouponUse.setEnabled(false);
            mTvCouponNotUse.setEnabled(true);
            isNotUse = false;
        });
        mTvCouponNotUse.setOnClickListener(v -> {
            if (cannot != null) {
                mAdapter.setNewData(cannot);
            }
            mTvCouponUse.setEnabled(true);
            mTvCouponNotUse.setEnabled(false);
            isNotUse = true;
        });
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int setHeight() {
        return super.setHeight();
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setAnim() {
        return top.jplayer.baseprolibrary.R.style.AnimBottom;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_use_coupon;
    }


}
