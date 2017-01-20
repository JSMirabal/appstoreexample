package com.jsmirabal.appstoreexample.activity;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/*
 * Copyright (c) 2017. JSMirabal
 */

@EActivity(R.layout.splash_screen)
public class SplashActivity extends AppCompatActivity {

    @AfterViews
    void init() {
        changeTabletOrientation();
        initMainActivity();
    }

    @UiThread(delay = 1000L)
    void initMainActivity() {
        runEnterAnimation();
    }

    private void runEnterAnimation() {
        TextView text1 = (TextView) findViewById(R.id.splash_text1);
        TextView text2 = (TextView) findViewById(R.id.splash_text2);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        float scaleX = text1.getScaleX();
        float scaleY = text1.getScaleY();
        text1.setAlpha(0.f);
        text1.setScaleX(0.f);
        text1.setScaleY(0.f);
        text1.animate()
                .scaleX(scaleX)
                .scaleY(scaleY)
                .alpha(1.f)
                .setStartDelay(200)
                .setInterpolator(new DecelerateInterpolator(2.f))
                .setDuration(500)
                .start();

        text2.setAlpha(0.f);
        text2.animate()
                .alpha(1.f)
                .setStartDelay(600)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startActivity(new Intent(SplashActivity.this, MainActivity_.class));
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .setInterpolator(new DecelerateInterpolator(2.f))
                .setDuration(1000)
                .start();
    }

    private void changeTabletOrientation() {
        if (Util.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
