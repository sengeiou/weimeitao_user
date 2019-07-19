package com.wmtc.wmt.personal.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.forum.event.ShareAllEvent;
import com.wmtc.wmt.forum.event.ShareOneEvent;
import com.wmtc.wmt.forum.event.SharePicEvent;
import com.wmtc.wmt.forum.event.ShareReportEvent;
import com.wmtc.wmt.personal.event.ShareCodeEvent;
import com.wmtc.wmt.personal.event.ShareDelEvent;
import com.wmtc.wmt.personal.event.ShareEditEvent;

import org.greenrobot.eventbus.EventBus;

import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.ui.diolog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShareHaveReportDialog extends BaseCustomDialog {

    private LinearLayout mllShareUrl;
    private View mLlShareDel;
    private View mLlShareEdit;
    private boolean interceptor;

    public ShareHaveReportDialog(Context context) {
        super(context);
    }

    private String url;
    private String id;

    public ShareHaveReportDialog setInterceptor(boolean interceptor) {
        this.interceptor = interceptor;
        LogUtil.e("setInterceptor");
        return this;
    }

    public ShareHaveReportDialog setUrl(String url) {
        this.url = url;
        return this;
    }

    public ShareHaveReportDialog setId(String id) {
        this.id = id;
        if (StringUtils.isNotBlank(id) && WmtApplication.id.equals(id)) {
            mLlShareDel.setVisibility(View.VISIBLE);
            mLlShareEdit.setVisibility(View.VISIBLE);
        } else {
            mLlShareDel.setVisibility(View.GONE);
            mLlShareEdit.setVisibility(View.GONE);
        }
        return this;
    }

    public ShareHaveReportDialog setCode() {
        mllShareUrl.setVisibility(View.VISIBLE);
        mllShareUrl.setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            EventBus.getDefault().post(new ShareCodeEvent(url));
            dismiss();
        });
        return this;
    }

    @Override
    protected void initView(View view) {
        LogUtil.e("initView");
        mllShareUrl = view.findViewById(R.id.llShareUrl);
        view.findViewById(R.id.llWxOne).setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            EventBus.getDefault().post(new ShareOneEvent(url));
            this.dismiss();
        });
        mLlShareEdit = view.findViewById(R.id.llShareEdit);
        mLlShareEdit.setOnClickListener(v -> {

            EventBus.getDefault().post(new ShareEditEvent());
            this.dismiss();
        });
        mLlShareDel = view.findViewById(R.id.llShareDel);
        mLlShareDel.setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareDelEvent());
            this.dismiss();
        });
        view.findViewById(R.id.llWxAll).setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            EventBus.getDefault().post(new ShareAllEvent(url));
            this.dismiss();
        });
        view.findViewById(R.id.llSharePic).setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            EventBus.getDefault().post(new SharePicEvent(url));
            this.dismiss();
        });
        view.findViewById(R.id.llShareReport).setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            EventBus.getDefault().post(new ShareReportEvent(url));
            this.dismiss();
        });
        view.findViewById(R.id.llShareUrl).setOnClickListener(v -> {
            if (interceptor) {
                ToastUtils.init().showInfoToast(getContext(), "当前帖子不可操作");
                dismiss();
                return;
            }
            StringUtils.init().copyString(getContext(), url);
            ToastUtils.init().showSuccessToast(getContext(), "已复制到粘贴板");
            this.dismiss();
        });
        view.findViewById(R.id.ivCancel).setOnClickListener(v -> this.dismiss());
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_share_report;
    }
}
