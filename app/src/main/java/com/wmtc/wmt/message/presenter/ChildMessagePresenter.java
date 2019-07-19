package com.wmtc.wmt.message.presenter;

import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.fragment.ChildMessageFragment;
import com.wmtc.wmt.message.pojo.MessagePojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChildMessagePresenter extends BasePresenter<ChildMessageFragment> {

    private final CommonModel mCommonModel;

    public ChildMessagePresenter(ChildMessageFragment iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void requestMessage(String type) {
        MessagePojo pojo = new MessagePojo();
        pojo.messageType = type;
        pojo.userId = WmtApplication.id;
        mCommonModel.getMessageList(pojo).subscribe(new DefaultCallBackObserver<MessageBean>() {
            @Override
            public void responseSuccess(MessageBean messageBean) {
                mIView.responseMessage(messageBean);
            }

            @Override
            public void responseFail(MessageBean messageBean) {

            }
        });
    }

    public void deleteMessage(String id) {
        MessagePojo pojo = new MessagePojo();
        pojo.messageId = id;
        mCommonModel.deleteMessage(pojo).subscribe(new DefaultCallBackObserver<BaseBean>() {
            @Override
            public void responseSuccess(BaseBean messageBean) {
                mIView.delMessage();
            }

            @Override
            public void responseFail(BaseBean messageBean) {

            }
        });
    }
}
