package com.jsmirabal.appstoreexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.activity.MainActivity;
import com.jsmirabal.appstoreexample.adapter.CategoryRecyclerAdapter;
import com.jsmirabal.appstoreexample.presenter.MainPresenter;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/*
 * Copyright (c) 2017. JSMirabal
 */

@EFragment(R.layout.fragment_category)
public class CategoryFragment extends Fragment {

    private MainActivity mActivity;
    private Context mContext;
    private CategoryRecyclerAdapter mAdapter;
    private MainPresenter mPresenter;
    private final static String URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";

    @ViewById(R.id.category_recycler_view)
    RecyclerView mCategoryRecyclerView;

    @AfterViews
    void init() {
        setMemberVariables();
        configRecyclerView();
        requestDataFromServer();
    }

    @Background
    void requestDataFromServer() {
        Bundle data = mPresenter.requestData(URL);
        if (data != null){
            onRequestSucceeded(data);
        }

    }

    @UiThread
    void onRequestSucceeded(Bundle data) {
        mAdapter = new CategoryRecyclerAdapter(data, this);
        mCategoryRecyclerView.setAdapter(mAdapter);
    }

    private void setMemberVariables() {
        mContext = getActivity();
        mActivity = (MainActivity) mContext;
        mPresenter = new MainPresenter(mContext);
    }

    private void configRecyclerView() {
        GridLayoutManager gridLayoutManager;
        if (!Util.isTablet(mActivity)) { // If portrait
            gridLayoutManager = new GridLayoutManager(mContext, 2);
        } else { // Is tablet
            gridLayoutManager = new GridLayoutManager(mContext, 4);
        }
        mCategoryRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
