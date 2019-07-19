package com.wmtc.chatmodule.adapter.holder;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.adapter.ChatAdapter;
import com.wmtc.chatmodule.enity.MessageInfo;
import com.wmtc.chatmodule.util.Constants;
import com.wmtc.chatmodule.util.Utils;
import com.wmtc.chatmodule.widget.BubbleImageView;
import com.wmtc.chatmodule.widget.GifTextView;

import top.jplayer.baseprolibrary.glide.GlideUtils;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatSendViewHolder extends BaseViewHolder<MessageInfo> {

    private TextView chatItemDate;
    private ImageView chatItemHeader;
    private GifTextView chatItemContentText;
    private BubbleImageView chatItemContentImage;
    private ImageView chatItemFail;
    private ProgressBar chatItemProgress;
    private ImageView chatItemVoice;
    private LinearLayout chatItemLayoutContent;
    private TextView chatItemVoiceTime;
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;

    public ChatSendViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_send);
        chatItemDate = itemView.findViewById(R.id.chat_item_date);
        chatItemHeader = itemView.findViewById(R.id.chat_item_header);
        chatItemContentText = itemView.findViewById(R.id.chat_item_content_text);
        chatItemContentImage = itemView.findViewById(R.id.chat_item_content_image);
        chatItemFail = itemView.findViewById(R.id.chat_item_fail);
        chatItemProgress = itemView.findViewById(R.id.chat_item_progress);
        chatItemVoice = itemView.findViewById(R.id.chat_item_voice);
        chatItemLayoutContent = itemView.findViewById(R.id.chat_item_layout_content);
        chatItemVoiceTime = itemView.findViewById(R.id.chat_item_voice_time);
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
    }


    @Override
    public void setData(MessageInfo data) {
        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
        Glide.with(getContext()).load(data.getHeader()).apply(GlideUtils.init().options(R.drawable.a2)).into(chatItemHeader);
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick(getDataPosition());
            }
        });
        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, data.getContent(), true);
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
        } else if (data.getImageUrl() != null) {
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(data.getImageUrl()).apply(GlideUtils.init().options(R.drawable.a2)).into(chatItemContentImage);
            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                }
            });
        } else if (data.getFilepath() != null) {
            chatItemVoice.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.VISIBLE);
            chatItemContentImage.setVisibility(View.GONE);
            chatItemVoiceTime.setText(data.getVoiceTime()+"'");
            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                }
            });
        }
        switch (data.getSendState()) {
            case Constants.CHAT_ITEM_SENDING:
                chatItemProgress.setVisibility(View.VISIBLE);
                chatItemFail.setVisibility(View.GONE);
                break;
            case Constants.CHAT_ITEM_SEND_ERROR:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_SUCCESS:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.GONE);
                break;
        }
    }
}
