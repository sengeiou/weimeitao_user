package com.wmtc.wmt.appoint.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;
import com.wmtc.wmt.forum.adapter.DialogAdapter;

import java.util.List;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;


/**
 * 优惠劵弹出框
 */
public class CouponDialog extends BaseCustomDialog {

    private Context mContext;
    private RecyclerView rv_list;
    private DialogAdapter mAdapter;

    public CouponDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int initLayout() {
        return R.layout.coupondialog_layout;
    }

    @Override
    protected void initView(View view) {
        view.setOnClickListener(v -> dismiss());
        rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DialogAdapter(null);
        rv_list.setAdapter(mAdapter);
    }

    public void setBean(List<CouponDialogbean.DataBean> data) {
        if (data != null && data.size() > 0) {
            mAdapter.setNewData(data);
        }
    }

    @Override
    public int setHeight() {
        return  ViewGroup.LayoutParams.MATCH_PARENT;

    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }
}
