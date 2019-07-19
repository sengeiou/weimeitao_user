package com.wmtc.chatmodule.adapter.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.MessageAdapter;
import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.enity.ImageInfo;
import com.wmtc.chatmodule.enity.ImageMessage;
import com.wmtc.chatmodule.ui.activity.ScanImageActivity;
import com.wmtc.chatmodule.widget.BubbleImageView;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/7/9.
 * com.wmtc.chatmodule.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageImageHolder extends MessageBaseHolder<ImageMessage> {

    public MessageImageHolder(View view, MessageAdapter adapter) {
        super(view, adapter);
    }

    @Override
    public void bindData(ImageMessage msg, int position) {
        super.bindData(msg, position);
        BubbleImageView ivImageLeft = itemView.findViewById(R.id.ivImageLeft);
        BubbleImageView ivImageRight = itemView.findViewById(R.id.ivImageRight);
        Glide.with(mContext)
                .load(msg.imageUrl)
                .apply(GlideUtils.init().options(R.drawable.shape_default_999))
                .into(ivImageLeft);
        Glide.with(mContext)
                .load(msg.imageUrl)
                .apply(GlideUtils.init().options(R.drawable.shape_default_999))
                .into(ivImageRight);
        ivImageLeft.setOnClickListener(v -> scanImage(ivImageLeft, msg));
        ivImageRight.setOnClickListener(v -> scanImage(ivImageRight, msg));
    }

    private void scanImage(ImageView view, ImageMessage msg) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setLocationX(location[0]);
        imageInfo.setLocationY(location[1]);
        imageInfo.setWidth(view.getWidth());
        imageInfo.setHeight(view.getHeight());
        imageInfo.setImageUrl(msg.imageUrl);
        Intent intent = new Intent(mContext, ScanImageActivity.class);
        intent.putExtra("imageInfo", imageInfo);
        mContext.startActivity(intent);
    }
}
