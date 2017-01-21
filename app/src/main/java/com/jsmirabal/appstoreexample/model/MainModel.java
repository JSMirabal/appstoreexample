package com.jsmirabal.appstoreexample.model;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import com.jsmirabal.appstoreexample.data.AppListData;
import com.jsmirabal.appstoreexample.db.DbContract.AppEntry;
import com.jsmirabal.appstoreexample.db.DbProvider;
import com.jsmirabal.appstoreexample.mvp.PresenterOpsToModel;
import com.jsmirabal.appstoreexample.presenter.MainPresenter;
import com.jsmirabal.appstoreexample.utility.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.jsmirabal.appstoreexample.data.AppListData.AUTHOR_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.CATEGORY_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.COPYRIGHT_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.ID_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.IMAGE_BLOB_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.IMAGE_PATH_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.NAME_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.PRICE_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.RELEASE_DATE_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.SUMMARY_PARAM;
import static com.jsmirabal.appstoreexample.db.DbContract.AppEntry.COLUMN_APP_CATEGORY;

public class MainModel implements PresenterOpsToModel {
    private MainPresenter mPresenter;
    private Context mContext;
    private AppListData appListData;

    public MainModel(MainPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public void insertData(Bundle data) {
        ContentResolver cr = mContext.getContentResolver();
        ContentValues[] appListValues = Util.buildAppListContentValues(mContext, appListData);
        int rowsInserted = cr.bulkInsert(AppEntry.CONTENT_URI, appListValues);

        if (rowsInserted == 0) {
            mPresenter.onDataInsertError(new SQLException("No rows were inserted."));
        } else {
            mPresenter.onDataInsertSuccess("Rows inserted: " + rowsInserted);
        }
    }

    @Override
    public void updateData() {

    }

    @Override
    public void deleteData() {
        ContentResolver cr = mContext.getContentResolver();
        // Delete all
        int rowsDeleted = cr.delete(AppEntry.CONTENT_URI, null, null);
        if (rowsDeleted == 0) {
            throw new SQLException("No rows were deleted.");
        }
    }

    @Override
    public Bundle selectData() {
        Set<String> categoryList = new HashSet<>();
        Bundle idBundle = new Bundle();
        Bundle nameBundle = new Bundle();
        Bundle authorBundle = new Bundle();
        Bundle imagePathBundle = new Bundle();
        Bundle summaryBundle = new Bundle();
        Bundle priceBundle = new Bundle();
        Bundle copyrightBundle = new Bundle();
        Bundle releaseDateBundle = new Bundle();
        Bundle imageBlobBundle = new Bundle();
        Bundle appDataBundle = new Bundle();

        ContentResolver cr = mContext.getContentResolver();

        // fetch category list
        Cursor cursor = cr.query(
                AppEntry.CONTENT_URI,
                new String[]{COLUMN_APP_CATEGORY},
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            for (int j = 0; j < cursor.getCount(); j++, cursor.moveToNext()) {
                categoryList.add(cursor.getString(
                        cursor.getColumnIndex(AppEntry.COLUMN_APP_CATEGORY)));
            }
            cursor.close();
        }

        // fetch app list by category
        for (String category : categoryList) {
            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> authorList = new ArrayList<>();
            ArrayList<String> idList = new ArrayList<>();
            ArrayList<String> imagePathList = new ArrayList<>();
            ArrayList<String> summaryList = new ArrayList<>();
            ArrayList<String> priceList = new ArrayList<>();
            ArrayList<String> copyrightList = new ArrayList<>();
            ArrayList<String> releaseDateList = new ArrayList<>();
            ArrayList<byte[]> imageBlobList = new ArrayList<>();

            cursor = cr.query(
                    AppEntry.CONTENT_URI,
                    null,
                    DbProvider.sTableAppCategorySelection,
                    new String[]{category},
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                for (int j = 0; j < cursor.getCount(); j++, cursor.moveToNext()) {

                    nameList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_NAME)));
                    authorList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_AUTHOR)));
                    idList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_ID)));
                    imagePathList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_IMAGE_PATH)));
                    summaryList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_SUMMARY)));
                    priceList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_PRICE)));
                    copyrightList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_COPYRIGHT)));
                    releaseDateList.add(cursor.getString(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_RELEASE_DATE)));
                    imageBlobList.add(cursor.getBlob(
                            cursor.getColumnIndex(AppEntry.COLUMN_APP_IMAGE_BLOB)));
                }
                nameBundle.putStringArrayList(category, nameList);
                authorBundle.putStringArrayList(category, authorList);
                idBundle.putStringArrayList(category, idList);
                imagePathBundle.putStringArrayList(category, imagePathList);
                summaryBundle.putStringArrayList(category, summaryList);
                priceBundle.putStringArrayList(category, priceList);
                copyrightBundle.putStringArrayList(category, copyrightList);
                releaseDateBundle.putStringArrayList(category, releaseDateList);
                imageBlobBundle.putSerializable(category, imageBlobList);
            }// if cursor
        }// for category

        appDataBundle.putStringArrayList(CATEGORY_PARAM, new ArrayList<>(categoryList));
        appDataBundle.putBundle(NAME_PARAM, nameBundle);
        appDataBundle.putBundle(AUTHOR_PARAM, authorBundle);
        appDataBundle.putBundle(ID_PARAM, idBundle);
        appDataBundle.putBundle(IMAGE_PATH_PARAM, imagePathBundle);
        appDataBundle.putBundle(SUMMARY_PARAM, summaryBundle);
        appDataBundle.putBundle(PRICE_PARAM, priceBundle);
        appDataBundle.putBundle(COPYRIGHT_PARAM, copyrightBundle);
        appDataBundle.putBundle(RELEASE_DATE_PARAM, releaseDateBundle);
        appDataBundle.putBundle(IMAGE_BLOB_PARAM, releaseDateBundle);

        return appDataBundle;
    }

    public boolean isDbEmpty() {
        ContentResolver cr = mContext.getContentResolver();
        Cursor cursor = cr.query(AppEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        boolean isEmpty = cursor == null || !cursor.moveToFirst();
        cursor.close();

        return isEmpty;
    }

    public void setAppListData(AppListData appListData) {
        this.appListData = appListData;
    }


}
