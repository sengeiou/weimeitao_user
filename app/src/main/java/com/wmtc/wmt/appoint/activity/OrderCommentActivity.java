package com.wmtc.wmt.appoint.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.event.JudgeEvent;
import com.wmtc.wmt.appoint.pojo.JudgePojo;
import com.wmtc.wmt.appoint.presenter.OrderCommentPresenter;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.base.WmtApplication;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/5/15.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderCommentActivity extends CommonWmtActivity {
    @BindView(R.id.ivPic)
    PolygonImageView mIvPic;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    @BindView(R.id.tvPrice)
    TextView mTvPrice;
    @BindView(R.id.tvProName)
    TextView mTvProName;
    @BindView(R.id.edInput)
    EditText mEdInput;
    @BindView(R.id.btnSure)
    Button mBtnSure;
    private Unbinder mBind;
    private OneOrderBean.DataBean mOrder;
    private OrderCommentPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mOrder = mBundle.getParcelable("order");
        mTvName.setText(StringUtils.init().fixNullStr(mOrder.shopName));
        mTvProName.setText(StringUtils.init().fixNullStr(mOrder.projectName));
        mTvTime.setText(StringUtils.init().fixNullStr(mOrder.createTime));
        mTvPrice.setText(StringUtils.init().fixNullStrMoney(mOrder.projectPriceFirst, "￥"));

        Glide.with(mActivity)
                .load(mOrder.proPic)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvPic);
        mPresenter = new OrderCommentPresenter(this);
        mBtnSure.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEdInput)) {
                ToastUtils.init().showInfoToast(mActivity, "请说说他的优点和不足吧");
                return;
            }
            JudgePojo pojo = new JudgePojo();
            pojo.isAnnoy = "1";
            pojo.judgeContent = mEdInput.getText().toString();
            pojo.orderId = mOrder.id;
            pojo.projectId = mOrder.projectId;
            pojo.shopId = mOrder.shopId;
            pojo.userId = WmtApplication.id;
            mPresenter.saveOrder(pojo);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mPresenter.detachView();
    }

    public void saveOrderJudgeOk() {
        EventBus.getDefault().post(new JudgeEvent());
        finish();
    }
}
