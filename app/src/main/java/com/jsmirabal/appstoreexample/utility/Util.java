package com.jsmirabal.appstoreexample.utility;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

import com.jsmirabal.appstoreexample.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.jsmirabal.appstoreexample.data.AppListData.IMAGE_PATH_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.NAME_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.PRICE_PARAM;

public class Util {

    public static boolean isTablet(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        return diagonalInches >= 7.0;
    }

    public static int getCategoryIconRes(String category) {
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
        resMap.put("News", R.drawable.ic_newspaper);
        resMap.put("Education", R.drawable.ic_education);
        resMap.put("Health & Fitness", R.drawable.ic_fitness);

        return resMap.get(category) == null ? R.drawable.ic_no_icon : resMap.get(category);
    }

    public static int getCategoryColor(String category) {
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
        resMap.put("News", R.color.lime500);
        resMap.put("Education", R.color.lightBlue500);
        resMap.put("Health & Fitness", R.color.lightGreen500);

        return resMap.get(category) == null ? R.color.teal500 : resMap.get(category);
    }

    public static int getColorDark(int colorRes) {
        Map<Integer, Integer> resMap = new HashMap<>();
        resMap.put(R.color.amber500, R.color.amber700);
        resMap.put(R.color.blue500, R.color.blue700);
        resMap.put(R.color.blueGrey500, R.color.blueGrey700);
        resMap.put(R.color.brown500, R.color.brown700);
        resMap.put(R.color.cyan500, R.color.cyan700);
        resMap.put(R.color.deepOrange500, R.color.deepOrange700);
        resMap.put(R.color.deepPurple500, R.color.deepPurple700);
        resMap.put(R.color.green500, R.color.green700);
        resMap.put(R.color.grey500, R.color.grey700);
        resMap.put(R.color.indigo500, R.color.indigo700);
        resMap.put(R.color.lime500, R.color.lime700);
        resMap.put(R.color.lightBlue500, R.color.lightBlue700);
        resMap.put(R.color.lightGreen500, R.color.lightGreen700);

        return resMap.get(colorRes) == null ? R.color.teal700 : resMap.get(colorRes);
    }

    public static String formatPrice(String price) {
        return price.equals("0.0") ? "FREE" : price + " USD";
    }

    public static String formatDate(String strDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH).parse(strDate);
            return capitalize(new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String capitalize(String text) {
        return !text.isEmpty() ? text.substring(0, 1).toUpperCase() + text.substring(1) : text;
    }

    public static String getFieldValue(Bundle data, String field, String category, int position) {
        return data.getBundle(field).getStringArrayList(category).get(position);
    }

    public static Bundle getSimilarAppData(Bundle data, String category, int position) {
        data.getBundle(NAME_PARAM).getStringArrayList(category).remove(position);
        data.getBundle(PRICE_PARAM).getStringArrayList(category).remove(position);
        data.getBundle(IMAGE_PATH_PARAM).getStringArrayList(category).remove(position);
        return data;
    }
}
