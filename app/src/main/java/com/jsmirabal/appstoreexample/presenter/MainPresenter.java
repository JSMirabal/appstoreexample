package com.jsmirabal.appstoreexample.presenter;

import android.content.Context;
import android.os.Bundle;

import com.jsmirabal.appstoreexample.data.AppListData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Copyright (c) 2017. JSMirabal
 */

public class MainPresenter {
    private final static String LOG_TAG = MainPresenter.class.getSimpleName();

    Context mContext;

    public MainPresenter(Context context){
        mContext = context;
    }

    private Bundle mData;

    public Bundle requestData(String url) {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            mData = getAppData(new JSONObject(jsonStr));
            return mData;
        } catch (IOException | JSONException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bundle getAppData(JSONObject json) {
        AppListData appListData = new AppListData();
        try {
            appListData.setData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appListData.getAppDataBundle();
    }
}
