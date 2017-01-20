package com.jsmirabal.appstoreexample.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.view.Window;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.activity.DetailActivity;
import com.jsmirabal.appstoreexample.activity.DetailActivity_;
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
    private MainActivity mMainActivity;
    private DetailActivity mDetailActivity;
    private Context mContext;
    private String mCategory;
    private Bundle mData;
    private AppListRecyclerAdapter mAdapter;
    private int lastPosition;
    public static final String APP_DATA = "app_data";
    public static final String APP_DATA_POSITION = "app_data_position";
    public static final String APP_DATA_CATEGORY = "app_data_category";

    @ViewById(R.id.app_list_recycler_view)
    RecyclerView mRecycler;


    @AfterViews
    void init() {
        initMemberVars();
        setupActionBar((AppCompatActivity) mContext);
        configRecyclerView();
    }

    private void setupActionBar(AppCompatActivity activity) {
        int colorRes = Util.getCategoryColor(mCategory);
        int colorDarkRes = Util.getColorDark(colorRes);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mCategory);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, colorRes)));
            Window window = activity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(activity, colorDarkRes));
            }
        }
    }

    private void initMemberVars() {
        mContext = getActivity();
        if (!Util.isTablet(mContext)) {
            mCategory = getTag();
            mMainActivity = (MainActivity) mContext;
            mData = getArguments();
        } else {
            mDetailActivity = (DetailActivity) mContext;
            mCategory = mDetailActivity.getIntent().getExtras().getString(APP_DATA_CATEGORY);
            mData = mDetailActivity.getIntent().getExtras().getBundle(APP_DATA);
        }
    }

    private void configRecyclerView() {
        GridLayoutManager gridLayoutManager;
        if (!Util.isTablet(mContext)) { // If phone
            gridLayoutManager = new GridLayoutManager(mContext, 1);
        } else { // Is tablet
            gridLayoutManager = new GridLayoutManager(mContext, 2);
        }
        mRecycler.setLayoutManager(gridLayoutManager);
        mAdapter = new AppListRecyclerAdapter(mData, this, mCategory);
        mRecycler.setAdapter(mAdapter);
    }

    public void onAppListItemClick(View view, int position) {
        if (!Util.isTablet(mContext)) {
            Intent intent = new Intent(mContext, DetailActivity_.class);
            intent.putExtra(APP_DATA, mData);
            intent.putExtra(APP_DATA_POSITION, position);
            intent.putExtra(APP_DATA_CATEGORY, mCategory);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(mMainActivity, view.findViewById(R.id.app_list_icon), "app_icon");
            startActivity(intent, options.toBundle());
        } else {
            if (position == lastPosition){
                return;
            }
            Fragment newDetailFragment = DetailFragment_.builder().build();
            Bundle args = new Bundle();
            args.putBundle(APP_DATA, mData);
            args.putInt(APP_DATA_POSITION, position);
            newDetailFragment.setArguments(args);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                newDetailFragment.setEnterTransition(new Slide());
            }
            FragmentManager manager = mDetailActivity.getSupportFragmentManager();
            Fragment oldDetailFragment = manager.findFragmentByTag(Integer.toString(lastPosition));
            manager.beginTransaction()
                    .hide(oldDetailFragment)
                    .add(R.id.tablet_app_list_fragment_container, newDetailFragment, Integer.toString(position))
                    .commit();
            lastPosition = position;
        }
    }

}
