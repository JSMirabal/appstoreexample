package com.jsmirabal.appstoreexample.data;


import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppListData {

    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    private ArrayList<String> imagePathList;
    private ArrayList<String> categoryList;
    private ArrayList<String> summaryList;
    private ArrayList<String> priceList;
    private ArrayList<String> copyrightList;
    private ArrayList<String> releaseDateList;
    private Bundle appDataBundle;

    public static final String ID_PARAM = "im:id";
    public static final String NAME_PARAM = "im:name";
    public static final String IMAGE_PATH_PARAM = "im:image";
    public static final String CATEGORY_PARAM = "category";
    public static final String SUMMARY_PARAM = "summary";
    public static final String PRICE_PARAM = "im:price";
    public static final String COPYRIGHT_PARAM = "rights";
    public static final String RELEASE_DATE_PARAM = "im:releaseDate";
    public static final String GENERIC_LABEL_PARAM = "label";
    public static final String GENERIC_ATTRIBUTES_PARAM = "attributes";


    public void setData(JSONObject json) throws JSONException {
        initVars();

        JSONArray entry = json.getJSONObject("feed").getJSONArray("entry");
        fillCategoryList(entry);
        appDataBundle.putStringArrayList(CATEGORY_PARAM, categoryList);
    }

    private void initVars() {
        idList = new ArrayList<>();
        nameList = new ArrayList<>();
        imagePathList = new ArrayList<>();
        categoryList = new ArrayList<>();
        summaryList = new ArrayList<>();
        priceList = new ArrayList<>();
        copyrightList = new ArrayList<>();
        releaseDateList = new ArrayList<>();
        appDataBundle = new Bundle();
    }

    private void fillCategoryList(JSONArray entry) throws JSONException {
        Set<String> set = new HashSet<>();
        for (int j = 0; j < entry.length(); j++) {
            set.add(entry.getJSONObject(j)
                    .getJSONObject(CATEGORY_PARAM)
                    .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                    .getString(GENERIC_LABEL_PARAM));
        }
        categoryList.addAll(set);
    }

    public Bundle getAppDataBundle() {
        return appDataBundle;
    }
}
