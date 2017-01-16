/*
 * Copyright (c) 2017. JSMirabal
 */

package com.jsmirabal.appstoreexample.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById (R.id.textView)
    TextView textView;

    @Click (R.id.textView)
    void textViewClicked(){
        Toast.makeText(this,"Text View Clicked", Toast.LENGTH_SHORT).show();
    }

    @AfterViews
    void changeTabletOrientation(){
        if (Util.isTablet(this)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
