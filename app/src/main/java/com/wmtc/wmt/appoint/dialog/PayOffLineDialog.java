package com.wmtc.wmt.appoint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.ShopHbBean;
import com.wmtc.wmt.appoint.event.PayOffLineEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PayOffLineDialog extends BaseCustomDialog {
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvOldPrice)
    TextView mTvOldPrice;
    @BindView(R.id.tvDelPrice)
    TextView mTvDelPrice;
    @BindView(R.id.tvTip)
    TextView mTvTip;
    @BindView(R.id.tvCheckZfb)
    TextView mTvCheckZfb;
    @BindView(R.id.flZfb)
    FrameLayout mFlZfb;
    @BindView(R.id.tvCheckWx)
    TextView mTvCheckWx;
    @BindView(R.id.llHb)
    LinearLayout llHb;
    @BindView(R.id.flWx)
    FrameLayout mFlWx;
    @BindView(R.id.tvWillPay)
    TextView mTvWillPay;
    private Unbinder mBind;
    private int oldPrice;
    private int mInt;

    public PayOffLineDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mBind = ButterKnife.bind(this, view);
        mTvCheckWx.setOnClickListener(v -> {
            mTvCheckWx.setSelected(true);
            mTvCheckZfb.setSelected(false);
        });
        mTvCheckZfb.setOnClickListener(v -> {
            mTvCheckZfb.setSelected(true);
            mTvCheckWx.setSelected(false);
        });
        mTvWillPay.setOnClickListener(v -> {
            if (!mTvCheckWx.isSelected() && !mTvCheckZfb.isSelected()) {
                ToastUtils.init().showInfoToast(getContext(), "请选择支付方式");
                return;
            }
            EventBus.getDefault().post(new PayOffLineEvent(oldPrice, mTvCheckZfb.isSelected() ? "zfb" : "wx"));
            dismiss();
        });
    }

    public void setBean(ShopHbBean.DataBean dataBean) {
        if (dataBean != null) {
            llHb.setVisibility(View.VISIBLE);
            String delPrice = dataBean.discountedAmount;
            int delIntPrice = Integer.parseInt(delPrice);
            mTvDelPrice.setText(StringUtils.init().fixNullStrMoney(delPrice, "-￥"));
            mInt = oldPrice * 100 - delIntPrice;
            if (mInt < 1) {
                mInt = 1;
            }
            mTvPrice.setText(StringUtils.init().fixNullStrMoney(mInt + ""));
        } else {
            llHb.setVisibility(View.GONE);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_pay_offline;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBind.unbind();
    }

    public void setInput(String oldPrice) {
        this.oldPrice = Integer.parseInt(oldPrice);
        String text = "￥" + oldPrice;
        mTvOldPrice.setText(text);
        mTvPrice.setText(oldPrice);
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;

    }
}
