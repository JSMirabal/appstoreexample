/*
 * Copyright (c) 2017. JSMirabal
 */

package com.jsmirabal.appstoreexample.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @AfterViews
    void init() {
        changeTabletOrientation();
    }

    private void changeTabletOrientation() {
        if (Util.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
