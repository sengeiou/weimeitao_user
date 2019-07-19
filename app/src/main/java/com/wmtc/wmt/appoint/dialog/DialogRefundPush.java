package com.wmtc.wmt.appoint.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.event.RefundOrderEvent;
import com.wmtc.wmt.appoint.presenter.DialogRefundPushPresenter;
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
public class DialogRefundPush extends BaseCustomDialog {

    private RecyclerView mRecyclerView;
    private ReportAdapter mAdapter;
    private DialogRefundPushPresenter mPresenter;
    private String dict;
    private String mReason = "";

    public DialogRefundPush(Context context) {
        super(context);
    }

    public DialogRefundPush setDict(String dict, String id) {
        this.dict = dict;
        mPresenter.getDict(dict);
        return this;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPresenter = new DialogRefundPushPresenter(this);
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
        view.findViewById(R.id.tvCommit).setOnClickListener(v -> {
            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        if (bean.isSel) {
                            mReason = bean.name;
                        }
                    });
            if (StringUtils.isBlank(mReason)) {
                ToastUtils.init().showInfoToast(getContext(), "请选择退款理由");
                return;
            }
            EventBus.getDefault().post(new RefundOrderEvent(mReason));
            dismiss();
        });
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_refund_push;
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
        return (int) (ScreenUtils.getScreenHeight() * 0.7);

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
