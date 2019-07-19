package com.wmtc.chatmodule.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wmtc.chatmodule.R;
import com.wmtc.chatmodule.enity.ImageInfo;

/**
 * Created by Obl on 2019/7/4.
 * com.wmtc.chatmodule.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ScanImageActivity extends AppCompatActivity {

    ImageView scanImage;
    LinearLayout scanLay;
    private int mLeft;
    private int mTop;
    private float mScaleX;
    private float mScaleY;
    private Drawable mBackground;
    private ImageInfo mImageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan_image);
        scanImage = findViewById(R.id.scan_image);
        scanLay = findViewById(R.id.scan_lay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mImageInfo = getIntent().getParcelableExtra("imageInfo");
        final int left = mImageInfo.getLocationX();
        final int top = mImageInfo.getLocationY();
        final int width = mImageInfo.getWidth();
        final int height = mImageInfo.getHeight();
        mBackground = new ColorDrawable(Color.BLACK);
        scanLay.setBackground(mBackground);
        scanImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                scanImage.getViewTreeObserver().removeOnPreDrawListener(this);
                int location[] = new int[2];
                scanImage.getLocationOnScreen(location);
                mLeft = left - location[0];
                mTop = top - location[1];
                mScaleX = width * 1.0f / scanImage.getWidth();
                mScaleY = height * 1.0f / scanImage.getHeight();
                activityEnterAnim();
                return true;
            }
        });
        scanImage.setOnClickListener(v -> {
            activityExitAnim(this::finish);
        });
        Glide.with(this).load(mImageInfo.getImageUrl()).into(scanImage);
    }

    private void activityEnterAnim() {
        scanImage.setPivotX(0);
        scanImage.setPivotY(0);
        scanImage.setScaleX(mScaleX);
        scanImage.setScaleY(mScaleY);
        scanImage.setTranslationX(mLeft);
        scanImage.setTranslationY(mTop);
        scanImage.animate()
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void activityExitAnim(Runnable runnable) {
        scanImage.setPivotX(0);
        scanImage.setPivotY(0);
        int halfWidth = mImageInfo.getWidth() / 2;
        int halfHeight = mImageInfo.getHeight() / 2;
        scanImage.animate()
                .scaleX(0)
                .scaleY(0)
                .translationX(mLeft + halfWidth)
                .translationY(mTop + halfHeight)
                .withEndAction(runnable)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 255, 0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    @Override
    public void onBackPressed() {
        activityExitAnim(this::finish);
    }

}
