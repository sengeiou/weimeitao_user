package com.wmtc.chatmodule.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.base.MessageBaseInfo;
import com.wmtc.chatmodule.base.OblMessageManager;
import com.wmtc.chatmodule.enity.CustomHolder;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Obl on 2019/7/4.
 * com.wmtc.chatmodule.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageBaseInfo, MessageBaseHolder<MessageBaseInfo>> {

    public Handler mHandler;
    public Map<ImageView, Integer> mVoiceMessageMap;//修复语音动画错乱问题

    public MessageAdapter(List<MessageBaseInfo> data) {
        super(data);
        mHandler = new Handler();
        mVoiceMessageMap = new LinkedHashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected MessageBaseHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        Map<Integer, CustomHolder> holderMap = OblMessageManager.mHolderMap;
        if (holderMap != null) {
            CustomHolder holderBean = holderMap.get(viewType);
            if (holderBean != null) {
                return createCustomHolder(this, parent,
                        holderBean.holderClass,
                        holderBean.layout);
            } else {
                throw new RuntimeException("Item 类型 为空或 msgType不一致");
            }
        } else {
            throw new RuntimeException("holderMap 为空");
        }
    }

    @Override
    protected void convert(MessageBaseHolder<MessageBaseInfo> helper, MessageBaseInfo item) {
        helper.bindData(item, helper.getLayoutPosition());
    }

    public MessageBaseHolder createCustomHolder(MessageAdapter adapter, ViewGroup parent, Class<MessageBaseHolder> holderClass
            , int layout) {
        MessageBaseHolder holder = null;
        Class[] parameterTypes = {View.class, MessageAdapter.class};
        Constructor constructor;
        try {
            constructor = holderClass.getConstructor(parameterTypes);
            Object[] objects = {LayoutInflater.from(parent.getContext()).inflate(layout, parent, false), adapter};
            holder = (MessageBaseHolder) constructor.newInstance(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder;
    }

}
