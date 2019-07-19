package com.wmtc.wmt.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wmtc.wmt.R;

/**
 * Created by Obl on 2019/6/20.
 * com.wmtc.wmt.appoint.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class EmptyUiVideoPlayer extends StandardGSYVideoPlayer {

    public EmptyUiVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EmptyUiVideoPlayer(Context context) {
        super(context);
        mBottomContainer.setVisibility(GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.empty_control_video;
    }

    public EmptyUiVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
