package com.jsmirabal.appstoreexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.activity.MainActivity;
import com.jsmirabal.appstoreexample.adapter.CategoryRecyclerAdapter;
import com.jsmirabal.appstoreexample.mvp.ViewOpsToPresenter;
import com.jsmirabal.appstoreexample.presenter.MainPresenter;
import com.jsmirabal.appstoreexample.utility.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.UnknownHostException;

/*
 * Copyright (c) 2017. JSMirabal
 */

@EFragment(R.layout.fragment_category)
public class CategoryFragment extends Fragment implements ViewOpsToPresenter {

    private MainActivity mActivity;
    private Context mContext;
    private Bundle mData;
    private CategoryRecyclerAdapter mAdapter;
    private MainPresenter mPresenter;
    private static final String LOG_TAG = CategoryFragment.class.getSimpleName();
    private static final String URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=40/json";

    @ViewById(R.id.category_recycler_view)
    RecyclerView mCategoryRecyclerView;

    @ViewById(R.id.error_layout)
    ViewGroup mErrorLayout;

    @ViewById(R.id.retry_error_button)
    Button mRetryButton;

    @Click(R.id.retry_error_button)
    void retryRequest() {
        mErrorLayout.setVisibility(View.INVISIBLE);
        requestDataFromServer();
    }

    @AfterViews
    void init() {
        initMemberVars();
        configRecyclerView();
        requestDataFromServer();
    }

    @Background
    void requestDataFromServer() {
        mPresenter.requestDataFromServer(URL);
    }

    private void initMemberVars() {
        mContext = getActivity();
        mActivity = (MainActivity) mContext;
        mPresenter = new MainPresenter(mContext, this);
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

    @UiThread
    @Override
    public void onRequestDataSuccess(Bundle data) {
        mAdapter = new CategoryRecyclerAdapter(data, this);
        mCategoryRecyclerView.setAdapter(mAdapter);
        mData = data;
    }

    @UiThread
    @Override
    public void onRequestDataError(Throwable e) {
        e.printStackTrace();
        if (e instanceof UnknownHostException) {
            mErrorLayout.setVisibility(View.VISIBLE);
        }
        Log.e(LOG_TAG, e.getMessage());
    }

    public void onCategoryItemClick(View view) {
        String category = view.getTag().toString();
        Log.d(LOG_TAG, "Item clicked: " + category);
        Fragment fragment = AppListFragment_.builder().build();
        fragment.setArguments(mData);
        FragmentManager manager = mActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragment_category))
                .add(R.id.activity_main, fragment, category)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit();
    }
}
