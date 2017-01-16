package com.jsmirabal.appstoreexample.utility;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

public class Util {

    public static boolean isTablet (Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        return diagonalInches >= 7.0;
    }
}
