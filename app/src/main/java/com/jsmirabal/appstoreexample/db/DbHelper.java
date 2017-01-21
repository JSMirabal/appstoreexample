package com.jsmirabal.appstoreexample.db;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.jsmirabal.appstoreexample.db.DbContract.*;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "app_store.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_APP_TABLE = "CREATE TABLE " + AppEntry.TABLE_NAME + " (" +

                AppEntry._ID + " INTEGER AUTO_INCREMENT," +

                AppEntry.COLUMN_APP_ID + " INTEGER NOT NULL, " +
                AppEntry.COLUMN_APP_AUTHOR + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_CATEGORY + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_COPYRIGHT + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_IMAGE_BLOB + " BLOB NULL, " +
                AppEntry.COLUMN_APP_IMAGE_PATH + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_NAME + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_PRICE + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_RELEASE_DATE + " TEXT NOT NULL, " +
                AppEntry.COLUMN_APP_SUMMARY + " TEXT NOT NULL, " +

                " PRIMARY KEY (" + AppEntry._ID + "," + AppEntry.COLUMN_APP_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_APP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Recreate database each time DATABASE_VERSION changes
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AppEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
