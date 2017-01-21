package com.jsmirabal.appstoreexample.db;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.jsmirabal.appstoreexample.db.DbContract.*;

public class DbProvider extends ContentProvider{
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mHelper;

    // Only one type by now
    static final int APP = 100;

    private static final SQLiteQueryBuilder sAppQueryBuilder;

    static {
        sAppQueryBuilder = new SQLiteQueryBuilder();
        sAppQueryBuilder.setTables(AppEntry.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = DbContract.CONTENT_AUTHORITY;
        String pathApp = DbContract.PATH_APP;

        uriMatcher.addURI(authority, pathApp, APP);

        return uriMatcher;
    }

    public static final String sTableAppIdSelection = AppEntry.COLUMN_APP_ID + " = ?" ;
    public static final String sTableAppCategorySelection = AppEntry.COLUMN_APP_CATEGORY + " = ?" ;


    private Cursor getApp(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return sAppQueryBuilder.query(mHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                sortOrder,
                null,
                null
        );
    }

    @Override
    public boolean onCreate() {
        mHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case APP: {
                retCursor = getApp(projection, selection, selectionArgs, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case APP:
                return AppEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case APP: {
                long _id = db.insert(AppEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = AppEntry.buildAppUri(values.getAsLong(AppEntry.COLUMN_APP_ID));
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case APP: {
                return performInsertTransaction(uri, AppEntry.TABLE_NAME, values);
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private int performInsertTransaction (Uri uri, String table, ContentValues[] values){
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(table, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        selection = selection == null ? "1" : selection;
        switch (match) {
            case APP: {
                rowsDeleted = db.delete(AppEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsAffected;
        switch (match) {
            case APP: {
                rowsAffected = db.update(AppEntry.TABLE_NAME, contentValues, sTableAppIdSelection,
                        selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsAffected != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }
}
