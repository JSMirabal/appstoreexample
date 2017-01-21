package com.jsmirabal.appstoreexample.db;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String CONTENT_AUTHORITY = "com.jsmirabal.appstoreexample";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_APP = "app";

    /* Table contents of the app table */
    public static final class AppEntry implements BaseColumns {
        public static final String TABLE_NAME = "app";
        public static final String COLUMN_APP_ID = "id";
        public static final String COLUMN_APP_NAME = "name";
        public static final String COLUMN_APP_AUTHOR = "author";
        public static final String COLUMN_APP_IMAGE_PATH = "image_path";
        public static final String COLUMN_APP_CATEGORY = "category";
        public static final String COLUMN_APP_SUMMARY = "summary";
        public static final String COLUMN_APP_PRICE= "price";
        public static final String COLUMN_APP_COPYRIGHT= "copyright";
        public static final String COLUMN_APP_RELEASE_DATE= "release_date";
        public static final String COLUMN_APP_IMAGE_BLOB = "image_blob";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_APP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_APP;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_APP;

        public static Uri buildAppUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getAppIdFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
}
