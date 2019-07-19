package com.wmtc.wmt.forum.activity;

import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmt.R;
import com.wmtc.wmt.base.CommonWmtActivity;
import com.wmtc.wmt.forum.adapter.TopicAdapter;
import com.wmtc.wmt.forum.event.TopicSelEvent;
import com.wmtc.wmt.forum.presenter.TopicPresenter;
import com.wmtc.wmt.appoint.bean.DictListBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Locale;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.jpush.im.android.api.JMessageClient;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TopicActivity extends SuperBaseActivity {

    private TopicPresenter mPresenter;
    private TopicAdapter mAdapter;
    private TextView mTvSureSel;
    private ArrayList<String> mSelList;
    private EditText mEditText;
    private String mKeyword = "";

    @Override
    protected int initRootLayout() {
        return R.layout.activity_forum_topic;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.llSearchAndScan)
                .keyboardEnable(true)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> {
                    mTvSureSel.setVisibility(isPopup ? View.GONE : View.VISIBLE);
                })
                .init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mPresenter = new TopicPresenter(this);
        mSelList = new ArrayList<>();
        showLoading();
        mPresenter.topic(mKeyword);
        mAdapter = new TopicAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        mTvSureSel = view.findViewById(R.id.tvSureSel);
        mEditText = view.findViewById(R.id.editSearch);
        view.findViewById(R.id.ivBack).setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(mActivity);
            finish();
        });
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> {
            BGAKeyboardUtil.closeKeyboard(mActivity);
        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            if (mSelList.size() >= 3) {
                ToastUtils.init().showInfoToast(mActivity, "最多可选3个");
                return;
            }
            DictListBean.DataBean dataBean = mAdapter.getData().get(position);
            dataBean.isSel = !dataBean.isSel;
            mAdapter.notifyItemChanged(position);

            mSelList.clear();
            Observable.fromIterable(mAdapter.getData())
                    .subscribe(bean -> {
                        if (bean.isSel) {
                            mSelList.add(bean.name);
                        }
                    }, throwable -> {
                    });
            mTvSureSel.setText(String.format(Locale.CHINA, "确认选择（%d/3）", mSelList.size()));
        });

        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean isOK = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!StringUtils.init().isEmpty(mEditText)) {
                    mKeyword = mEditText.getText().toString();
                }
                mEditText.clearFocus();
                isOK = true;
                mPresenter.topic(mKeyword);
                BGAKeyboardUtil.closeKeyboard(mActivity);
            }
            return isOK;
        });


        mTvSureSel.setOnClickListener(v -> {
            StringBuffer stringBuffer = new StringBuffer();
            Observable.fromIterable(mSelList)
                    .subscribe(s -> {
                        stringBuffer.append(s);
                        stringBuffer.append(",");
                    }, throwable -> {
                    });
            String string = stringBuffer.toString();
            if (stringBuffer.toString().contains(",")) {
                string = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
            }
            EventBus.getDefault().post(new TopicSelEvent(string, "0"));
            finish();
        });
    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        mKeyword = "";
        mEditText.setText("");
        mPresenter.topic(mKeyword);
    }


    public void getTopic(DictListBean bean) {
        responseSuccess();
        if (bean.data != null) {
            mAdapter.setNewData(bean.data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
