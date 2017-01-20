package com.jsmirabal.appstoreexample.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jsmirabal.appstoreexample.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/*
 * Copyright (c) 2017. JSMirabal
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity {

    @AfterViews
    void init (){
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                supportFinishAfterTransition();
                startActivity(parentIntent);
//                finish();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
