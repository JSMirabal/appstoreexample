package com.jsmirabal.appstoreexample.presenter;

import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.jsmirabal.appstoreexample.data.AppListData;
import com.jsmirabal.appstoreexample.fragment.CategoryFragment;
import com.jsmirabal.appstoreexample.model.MainModel;
import com.jsmirabal.appstoreexample.mvp.ModelOpsToPresenter;
import com.jsmirabal.appstoreexample.mvp.ViewOpsToPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Copyright (c) 2017. JSMirabal
 */

public class MainPresenter implements ViewOpsToPresenter, ModelOpsToPresenter {
    private final static String LOG_TAG = MainPresenter.class.getSimpleName();

    private CategoryFragment mFragment;
    private MainModel mModel;
    private AppListData appListData;

    public MainPresenter(CategoryFragment fragment) {
        mFragment = fragment;
        mModel = new MainModel(this, fragment.getContext());
    }

    private Bundle getAppData(JSONObject json) throws JSONException {
        appListData = new AppListData();
        appListData.setData(json);
        return appListData.getAppDataBundle();
    }

    @Override
    public void requestDataFromServer(String url) {
        OkHttpClient client = new OkHttpClient();
        Bundle data;
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            if (response.isSuccessful()){
                 data = getAppData(new JSONObject(jsonStr));
                mFragment.onRequestDataSuccess(data);
                mModel.setAppListData(appListData);
                if (mModel.isDbEmpty()){
                    mModel.insertData(data);
                } else {
                    mModel.deleteData();
                    mModel.insertData(data);
                }
            } else {
                if (mModel.isDbEmpty()){
                    throw new IOException(response.message());
                } else {
                    data = mModel.selectData();
                    showInternetAlert();
                    mFragment.onRequestDataSuccess(data);
                }
            }
        } catch (IOException | JSONException | IllegalArgumentException e) {
            if (mModel.isDbEmpty()){
                mFragment.onRequestDataError(e);
            } else {
                data = mModel.selectData();
                showInternetAlert();
                mFragment.onRequestDataSuccess(data);
            }
        }
    }

    private void showInternetAlert(){
        Snackbar.make(mFragment.getView(),"There's no internet connection.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDataInsertSuccess(String s) {
        Log.d(LOG_TAG, s);
    }

    @Override
    public void onDataInsertError(SQLException e) {

    }
}
