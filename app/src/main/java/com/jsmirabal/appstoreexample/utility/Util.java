package com.jsmirabal.appstoreexample.utility;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.jsmirabal.appstoreexample.R;

import java.util.HashMap;
import java.util.Map;

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

    public static int getCategoryIconRes(String category){
        Map<String, Integer> resMap = new HashMap<>();
        resMap.put("Shopping", R.drawable.ic_shopping);
        resMap.put("Navigation", R.drawable.ic_navigation);
        resMap.put("Social Networking", R.drawable.ic_networking);
        resMap.put("Productivity", R.drawable.ic_productivity);
        resMap.put("Entertainment", R.drawable.ic_entertaiment);
        resMap.put("Music", R.drawable.ic_music);
        resMap.put("Travel", R.drawable.ic_travel);
        resMap.put("Photo & Video", R.drawable.ic_photo_video);
        resMap.put("Games", R.drawable.ic_games);
        resMap.put("Utilities", R.drawable.ic_utility);

        return resMap.get(category) == null ? R.drawable.ic_no_icon : resMap.get(category);
    }

    public static int getCategoryColor(String category){
        Map<String, Integer> resMap = new HashMap<>();
        resMap.put("Shopping", R.color.amber500);
        resMap.put("Navigation", R.color.blue500);
        resMap.put("Social Networking", R.color.blueGrey500);
        resMap.put("Productivity", R.color.brown500);
        resMap.put("Entertainment", R.color.cyan500);
        resMap.put("Music", R.color.deepOrange500);
        resMap.put("Travel", R.color.deepPurple500);
        resMap.put("Photo & Video", R.color.green500);
        resMap.put("Games", R.color.grey500);
        resMap.put("Utilities", R.color.indigo500);

        return resMap.get(category) == null ? R.color.teal500 : resMap.get(category);
    }

}
