package com.jsmirabal.appstoreexample.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.activity.MainActivity;
import com.jsmirabal.appstoreexample.adapter.AppListRecyclerAdapter;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/*
 * Copyright (c) 2017. JSMirabal
 */
@EFragment(R.layout.fragment_app_list)
public class AppListFragment extends Fragment {
    private MainActivity mActivity;
    private Context mContext;
    private String mCategory;
    private Bundle mData;
    private AppListRecyclerAdapter mAdapter;
    @ViewById(R.id.app_list_recycler_view)
    RecyclerView mRecycler;

    @AfterViews
    void init(){
        initMemberVars();
        setupActionToolbar();
        configRecyclerView();
    }

    private void setupActionToolbar(){
        int colorRes = Util.getCategoryColor(mCategory);
        int colorDarkRes = Util.getColorDark(colorRes);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(mCategory);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mActivity,colorRes)));
            Window window = mActivity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(mActivity,colorDarkRes));
            }
        }
    }

    private void initMemberVars(){
        mCategory = getTag();
        mContext = getActivity();
        mActivity = (MainActivity) mContext;
        mData = getArguments();
    }

    private void configRecyclerView() {
        GridLayoutManager gridLayoutManager;
        if (!Util.isTablet(mActivity)) { // If portrait
            gridLayoutManager = new GridLayoutManager(mContext, 1);
        } else { // Is tablet
            gridLayoutManager = new GridLayoutManager(mContext, 2);
        }
        mRecycler.setLayoutManager(gridLayoutManager);
        mAdapter = new AppListRecyclerAdapter(mData, this);
        mRecycler.setAdapter(mAdapter);
    }

}
