package com.wmtc.wmt.wxapi;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import top.jplayer.baseprolibrary.utils.klog.KLog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wmtc.wmt.R;
import com.wmtc.wmt.base.WmtApplication;
import com.wmtc.wmt.personal.event.WxLoginEvent;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

public class WXEntryActivity extends SuperBaseActivity implements IWXAPIEventHandler {
    private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        api = WXAPIFactory.createWXAPI(this, WmtApplication.WXAPP_ID, false);

        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initRootLayout() {
        return R.layout.fragment_base;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        KLog.i(TAG, "onResp:------>");
        KLog.i(TAG, "error_code:---->" + resp.errCode);
        int type = resp.getType(); //类型：分享还是登录
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtils.init().showInfoToast(getApplication(), "拒绝授权微信登录");
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == 1) {
                    message = "取消了微信登录";
                    EventBus.getDefault().post(new WxLoginEvent("", "取消了微信登录"));
                    finish();
                } else if (type == 2) {
                    message = "取消了微信分享";
                    finish();
                }
                ToastUtils.init().showInfoToast(getApplication(), message);
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == 1) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) resp).code;
                    KLog.i(TAG, "code:------>" + code);
                    KLog.e("TAG", "微信++++++++++" + new Gson().toJson(resp));
                    EventBus.getDefault().post(new WxLoginEvent(code, "微信登录成功"));
                    finish();
                } else if (type == 2) {
//                    ToastUtils.init().showInfoToast(getApplication(), "微信分享成功");
                    finish();
                }
                break;
        }

    }

    private void goToGetMsg() {

    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer();
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

    }
}