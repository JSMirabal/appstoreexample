package com.jsmirabal.appstoreexample.presenter;

import android.content.Context;
import android.os.Bundle;

import com.jsmirabal.appstoreexample.data.AppListData;
import com.jsmirabal.appstoreexample.fragment.CategoryFragment;
import com.jsmirabal.appstoreexample.mvp.PresenterOpsToView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Copyright (c) 2017. JSMirabal
 */

public class MainPresenter implements PresenterOpsToView {
    private final static String LOG_TAG = MainPresenter.class.getSimpleName();

    private Context mContext;
    private CategoryFragment mFragment;

    public MainPresenter(Context context, CategoryFragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    private Bundle getAppData(JSONObject json) throws JSONException {
        AppListData appListData = new AppListData();
        appListData.setData(json);
        return appListData.getAppDataBundle();
    }

    @Override
    public void requestDataFromServer(String url) {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            if (response.isSuccessful()){
                mFragment.onRequestDataSuccess(getAppData(new JSONObject(jsonStr)));
            } else {
                throw new IOException(response.message());
            }

        } catch (IOException | JSONException | IllegalArgumentException e) {
            mFragment.onRequestDataError(e);
        }
    }
}
