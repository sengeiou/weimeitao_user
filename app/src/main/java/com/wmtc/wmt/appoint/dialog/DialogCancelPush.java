package com.wmtc.wmt.appoint.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.event.CancelOrderEvent;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.appoint.presenter.DialogCancelPushPresenter;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.adapter.ReportAdapter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.appoint.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DialogCancelPush extends BaseCustomDialog {

    private RecyclerView mRecyclerView;
    private ReportAdapter mAdapter;
    private DialogCancelPushPresenter mPresenter;
    private String dict;
    private EditText mEditResume;
    private RefundPojo mPojo;
    public String id;

    public DialogCancelPush(Context context) {
        super(context);
    }

    public DialogCancelPush setDict(String dict, String id) {
        this.dict = dict;
        this.id = id;
        mPresenter.getDict(dict);
        return this;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPresenter = new DialogCancelPushPresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReportAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        view.findViewById(R.id.ivCancel).setOnClickListener(v -> dismiss());
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        bean.isSel = false;
                    });
            DictListBean.DataBean dataBean = mAdapter.getData().get(position);
            dataBean.isSel = true;
            mAdapter.notifyDataSetChanged();
        });
        mEditResume = view.findViewById(R.id.editResume);
        view.findViewById(R.id.tvCommit).setOnClickListener(v -> {
            mPojo = new RefundPojo();
            mPojo.id = id;
            mPojo.orderId = id;
            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        if (bean.isSel) {
                            mPojo.cancelOrderReason = bean.name;
                        }
                    });
            if (StringUtils.isBlank(mPojo.cancelOrderReason)) {
                ToastUtils.init().showInfoToast(getContext(), "请选择取消理由");
                return;
            }
            mPojo.cancelOrderDescription = mEditResume.getText().toString();
            mPojo.setUserId(WmtApplication.id);
            EventBus.getDefault().post(new CancelOrderEvent(mPojo, dict));
            dismiss();
        });
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_cancel_push;
    }

    public void getList(DictListBean bean) {
        if (bean != null && bean.data != null) {
            mAdapter.setNewData(bean.data);
        }
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setHeight() {
        return (int) (ScreenUtils.getScreenHeight() * 0.8);

    }

    @Override
    public int setSoftInputState() {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mPresenter.detachView();
    }
}
