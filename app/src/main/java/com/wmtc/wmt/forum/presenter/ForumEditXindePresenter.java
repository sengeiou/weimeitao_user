package com.wmtc.wmt.forum.presenter;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.wmtc.wmt.base.BaseBean;
import com.wmtc.wmt.base.CommonModel;
import com.wmtc.wmt.base.CommonModel$Auto;
import com.wmtc.wmt.base.DefaultCallBackObserver;
import com.wmtc.wmt.base.WmtServer;
import com.wmtc.wmt.forum.activity.ForumXindeEditActivity;
import com.wmtc.wmt.forum.activity.ForumXindeSendActivity;
import com.wmtc.wmt.forum.bean.OSSBean;
import com.wmtc.wmt.forum.pojo.SendPojo;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static com.wmtc.wmt.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumEditXindePresenter extends BasePresenter<ForumXindeEditActivity> {

    private final CommonModel mModel;
    private final CommonModel$Auto mModel$Auto;

    public ForumEditXindePresenter(ForumXindeEditActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
        mModel$Auto = new CommonModel$Auto(WmtServer.class);
    }

    public void ossToken() {
        mModel.ossToken().subscribe(new DefaultCallBackObserver<OSSBean>(this) {
            @Override
            public void responseSuccess(OSSBean bean) {
                OSSBean.DataBean mBean = bean.data;
                OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {

                    @Override
                    public OSSFederationToken getFederationToken() {
                        return new OSSFederationToken(mBean.AccessKeyId, mBean.AccessKeySecret, mBean.SecurityToken, mBean.Expiration);
                    }
                };
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                OSSClient mOss = new OSSClient(getApplicationContext(), ENDPOINT, credentialProvider, conf);
                mIView.ossClient(mOss);
            }

            @Override
            public void responseFail(OSSBean proListBean) {

            }
        });
    }

    public void saveXinde(SendPojo pojo) {
        mModel.saveXinde(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.sendOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void editXinde(SendPojo pojo) {
        mModel$Auto.editXinde(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.sendOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void saveHeji(SendPojo pojo) {
        mModel.saveHeji(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.sendOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

}
