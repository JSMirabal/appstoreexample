package com.jsmirabal.appstoreexample.data;


import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppListData {

    private Bundle idBundle;
    private Bundle nameBundle;
    private Bundle imagePathBundle;
    private Bundle summaryBundle;
    private Bundle priceBundle;
    private Bundle copyrightBundle;
    private Bundle releaseDateBundle;
    private ArrayList<String> categoryList;
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
    public static final String SELECTED_CATEGORY = "selected_category";
    private static final int IMAGE_53P = 0;
    private static final int IMAGE_75P = 1;
    private static final int IMAGE_100P = 2;
    private static final String AMOUNT = "amount";


    public void setData(JSONObject json) throws JSONException {
        initVars();

        JSONArray entry = json.getJSONObject("feed").getJSONArray("entry");

        fillCategoryList(entry);
        fillNameList(entry);
        fillIdList(entry);
        fillImagePathList(entry);
        fillSummaryList(entry);
        fillPriceList(entry);
        fillCopyrightList(entry);
        fillReleaseDateList(entry);

        fillAppDataBundle();
    }

    private void initVars() {
        idBundle = new Bundle();
        nameBundle = new Bundle();
        imagePathBundle = new Bundle();
        categoryList = new ArrayList<>();
        summaryBundle = new Bundle();
        priceBundle = new Bundle();
        copyrightBundle = new Bundle();
        releaseDateBundle = new Bundle();
        appDataBundle = new Bundle();
    }

    private void fillCategoryList(JSONArray entry) throws JSONException {
        Set<String> categoryList = new HashSet<>();
        for (int j = 0; j < entry.length(); j++) {
            categoryList.add(getCategoryFromJson(entry, j));
        }
        this.categoryList.addAll(categoryList);
    }

    private void fillIdList(JSONArray entry) throws JSONException {
        String category, id;
        ArrayList<String> idList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            idList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                id = entry.getJSONObject(j)
                        .getJSONObject("id")
                        .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                        .getString(ID_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    idList.add(id); // Add an id to the list
                }
            }// for j
            // Add the id list of the respective category
            idBundle.putStringArrayList(categorySaved, idList);
        }// foreach
    }// method

    private void fillNameList(JSONArray entry) throws JSONException {
        String category, name;
        ArrayList<String> nameList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            nameList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                name = entry.getJSONObject(j)
                        .getJSONObject(NAME_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    nameList.add(name); // Add a name to the list
                }
            }// for j
            // Add the name list of the respective category
            nameBundle.putStringArrayList(categorySaved, nameList);
        }// foreach
    }// method

    private void fillImagePathList(JSONArray entry) throws JSONException {
        String category, imagePath;
        ArrayList<String> imagePathList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            imagePathList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                imagePath = entry.getJSONObject(j)
                        .getJSONArray(IMAGE_PATH_PARAM)
                        .getJSONObject(IMAGE_100P)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    imagePathList.add(imagePath); // Add a imagePath to the list
                }
            }// for j
            // Add the imagePath list of the respective category
            imagePathBundle.putStringArrayList(categorySaved, imagePathList);
        }// foreach
    }// method

    private void fillSummaryList(JSONArray entry) throws JSONException {
        String category, summary;
        ArrayList<String> summaryList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            summaryList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                summary = entry.getJSONObject(j)
                        .getJSONObject(SUMMARY_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    summaryList.add(summary); // Add a summary to the list
                }
            }// for j
            // Add the summary list of the respective category
            summaryBundle.putStringArrayList(categorySaved, summaryList);
        }// foreach
    }// method

    private void fillPriceList(JSONArray entry) throws JSONException {
        String category, price;
        ArrayList<String> priceList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            priceList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                price = entry.getJSONObject(j)
                        .getJSONObject(PRICE_PARAM)
                        .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                        .getString(AMOUNT).substring(0,3);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    priceList.add(price); // Add a price to the list
                }
            }// for j
            // Add the price list of the respective category
            priceBundle.putStringArrayList(categorySaved, priceList);
        }// foreach
    }// method

    private void fillCopyrightList(JSONArray entry) throws JSONException {
        String category, copyright;
        ArrayList<String> copyrightList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            copyrightList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                copyright = entry.getJSONObject(j)
                        .getJSONObject(COPYRIGHT_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    copyrightList.add(copyright); // Add a copyright to the list
                }
            }// for j
            // Add the copyright list of the respective category
            copyrightBundle.putStringArrayList(categorySaved, copyrightList);
        }// foreach
    }// method

    private void fillReleaseDateList(JSONArray entry) throws JSONException {
        String category, releaseDate;
        ArrayList<String> releaseDateList;
        // For each filtered category
        for (String categorySaved : categoryList) {
            releaseDateList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                releaseDate = entry.getJSONObject(j)
                        .getJSONObject(RELEASE_DATE_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)){
                    releaseDateList.add(releaseDate); // Add a releaseDate to the list
                }
            }// for j
            // Add the releaseDate list of the respective category
            releaseDateBundle.putStringArrayList(categorySaved, releaseDateList);
        }// foreach
    }// method

    private void fillAppDataBundle(){
        appDataBundle.putStringArrayList(CATEGORY_PARAM, categoryList);
        appDataBundle.putBundle(NAME_PARAM, nameBundle);
        appDataBundle.putBundle(ID_PARAM, idBundle);
        appDataBundle.putBundle(IMAGE_PATH_PARAM, imagePathBundle);
        appDataBundle.putBundle(SUMMARY_PARAM, summaryBundle);
        appDataBundle.putBundle(PRICE_PARAM, priceBundle);
        appDataBundle.putBundle(COPYRIGHT_PARAM, copyrightBundle);
        appDataBundle.putBundle(RELEASE_DATE_PARAM, releaseDateBundle);
    }

    private String getCategoryFromJson(JSONArray entry, int position) throws JSONException {
        return entry.getJSONObject(position)
                .getJSONObject(CATEGORY_PARAM)
                .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                .getString(GENERIC_LABEL_PARAM);
    }

    public Bundle getAppDataBundle() {
        return appDataBundle;
    }
}
