package com.jsmirabal.appstoreexample.data;


import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (c) 2017. JSMirabal
 */
public class AppListData {

    private Bundle idBundle;
    private Bundle nameBundle;
    private Bundle authorBundle;
    private Bundle imagePathBundle;
    private Bundle summaryBundle;
    private Bundle priceBundle;
    private Bundle copyrightBundle;
    private Bundle releaseDateBundle;
    private Bundle appDataBundle;

    private ArrayList<String> filteredCategoryList;
    private ArrayList<String> categoryList;
    private ArrayList<String> nameList;
    private ArrayList<String> authorList;
    private ArrayList<String> imagePathList;
    private ArrayList<String> idList;
    private ArrayList<String> summaryList;
    private ArrayList<String> priceList;
    private ArrayList<String> copyrightList;
    private ArrayList<String> releaseDateList;

    public static final String ID_PARAM = "im:id";
    public static final String NAME_PARAM = "im:name";
    public static final String AUTHOR_PARAM = "im:artist";
    public static final String IMAGE_PATH_PARAM = "im:image";
    public static final String IMAGE_BLOB_PARAM = "image_blob";
    public static final String CATEGORY_PARAM = "category";
    public static final String SUMMARY_PARAM = "summary";
    public static final String PRICE_PARAM = "im:price";
    public static final String COPYRIGHT_PARAM = "rights";
    public static final String RELEASE_DATE_PARAM = "im:releaseDate";
    public static final String GENERIC_LABEL_PARAM = "label";
    public static final String GENERIC_ATTRIBUTES_PARAM = "attributes";

    private static final int IMAGE_53P = 0;
    private static final int IMAGE_75P = 1;
    private static final int IMAGE_100P = 2;
    private static final String AMOUNT = "amount";


    public void setData(JSONObject json) throws JSONException {
        initVars();

        JSONArray entry = json.getJSONObject("feed").getJSONArray("entry");

        fillFilteredCategoryList(entry);
        fillNameList(entry);
        fillAuthorList(entry);
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
        authorBundle = new Bundle();
        imagePathBundle = new Bundle();
        summaryBundle = new Bundle();
        priceBundle = new Bundle();
        copyrightBundle = new Bundle();
        releaseDateBundle = new Bundle();
        appDataBundle = new Bundle();

        nameList = new ArrayList<>();
        filteredCategoryList = new ArrayList<>();
        categoryList = new ArrayList<>();
        authorList = new ArrayList<>();
        imagePathList = new ArrayList<>();
        idList = new ArrayList<>();
        summaryList = new ArrayList<>();
        priceList = new ArrayList<>();
        copyrightList = new ArrayList<>();
        releaseDateList = new ArrayList<>();
    }

    private void fillFilteredCategoryList(JSONArray entry) throws JSONException {
        Set<String> categoryList = new HashSet<>();
        for (int j = 0; j < entry.length(); j++) {
            categoryList.add(getCategoryFromJson(entry, j));
            this.categoryList.add(getCategoryFromJson(entry, j));
        }
        this.filteredCategoryList.addAll(categoryList);
    }

    private void fillIdList(JSONArray entry) throws JSONException {
        String category, id;
        ArrayList<String> idList;

        // Fill every id
        for (int j = 0; j < entry.length(); j++) {
            id = entry.getJSONObject(j)
                    .getJSONObject("id")
                    .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                    .getString(ID_PARAM);
            this.idList.add(id);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            idList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                id = entry.getJSONObject(j)
                        .getJSONObject("id")
                        .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                        .getString(ID_PARAM);

                // If we are in the same category
                if (categorySaved.equals(category)) {
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

        // fill every name
        for (int j = 0; j < entry.length(); j++) {
            name = entry.getJSONObject(j)
                    .getJSONObject(NAME_PARAM)
                    .getString(GENERIC_LABEL_PARAM);
            this.nameList.add(name);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            nameList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                name = entry.getJSONObject(j)
                        .getJSONObject(NAME_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)) {
                    nameList.add(name); // Add a name to the list
                }
            }// for j
            // Add the name list of the respective category
            nameBundle.putStringArrayList(categorySaved, nameList);
        }// foreach
    }// method

    private void fillAuthorList(JSONArray entry) throws JSONException {
        String category, author;
        ArrayList<String> authorList;

        // fill every author
        for (int j = 0; j < entry.length(); j++) {
            author = entry.getJSONObject(j)
                    .getJSONObject(AUTHOR_PARAM)
                    .getString(GENERIC_LABEL_PARAM);
            this.authorList.add(author);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            authorList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                author = entry.getJSONObject(j)
                        .getJSONObject(AUTHOR_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)) {
                    authorList.add(author); // Add a author to the list
                }
            }// for j
            // Add the author list of the respective category
            authorBundle.putStringArrayList(categorySaved, authorList);
        }// foreach
    }// method

    private void fillImagePathList(JSONArray entry) throws JSONException {
        String category, imagePath;
        ArrayList<String> imagePathList;

        // fill ever imagePath
        for (int j = 0; j < entry.length(); j++) {
            imagePath = entry.getJSONObject(j)
                    .getJSONArray(IMAGE_PATH_PARAM)
                    .getJSONObject(IMAGE_100P)
                    .getString(GENERIC_LABEL_PARAM);
            this.imagePathList.add(imagePath);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            imagePathList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                imagePath = entry.getJSONObject(j)
                        .getJSONArray(IMAGE_PATH_PARAM)
                        .getJSONObject(IMAGE_100P)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)) {
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

        // fill every summary
        for (int j = 0; j < entry.length(); j++) {
            summary = entry.getJSONObject(j)
                    .getJSONObject(SUMMARY_PARAM)
                    .getString(GENERIC_LABEL_PARAM);
            this.summaryList.add(summary);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            summaryList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                summary = entry.getJSONObject(j)
                        .getJSONObject(SUMMARY_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)) {
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

        for (int j = 0; j < entry.length(); j++) {
            price = entry.getJSONObject(j)
                    .getJSONObject(PRICE_PARAM)
                    .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                    .getString(AMOUNT).substring(0, 3);
            this.priceList.add(price);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            priceList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                price = entry.getJSONObject(j)
                        .getJSONObject(PRICE_PARAM)
                        .getJSONObject(GENERIC_ATTRIBUTES_PARAM)
                        .getString(AMOUNT).substring(0, 3);
                // If we are in the same category
                if (categorySaved.equals(category)) {
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

        for (int j = 0; j < entry.length(); j++) {
            copyright = entry.getJSONObject(j)
                    .getJSONObject(COPYRIGHT_PARAM)
                    .getString(GENERIC_LABEL_PARAM);
            this.copyrightList.add(copyright);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            copyrightList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                copyright = entry.getJSONObject(j)
                        .getJSONObject(COPYRIGHT_PARAM)
                        .getString(GENERIC_LABEL_PARAM);
                // If we are in the same category
                if (categorySaved.equals(category)) {
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
        for (int j = 0; j < entry.length(); j++) {
            releaseDate = entry.getJSONObject(j)
                    .getJSONObject(RELEASE_DATE_PARAM)
                    .getString(GENERIC_LABEL_PARAM).substring(0, 7);
            this.releaseDateList.add(releaseDate);
        }

        // For each filtered category
        for (String categorySaved : filteredCategoryList) {
            releaseDateList = new ArrayList<>();
            for (int j = 0; j < entry.length(); j++) {
                category = getCategoryFromJson(entry, j);
                releaseDate = entry.getJSONObject(j)
                        .getJSONObject(RELEASE_DATE_PARAM)
                        .getString(GENERIC_LABEL_PARAM).substring(0, 7);
                // If we are in the same category
                if (categorySaved.equals(category)) {
                    releaseDateList.add(releaseDate); // Add a releaseDate to the list
                }
            }// for j
            // Add the releaseDate list of the respective category
            releaseDateBundle.putStringArrayList(categorySaved, releaseDateList);
        }// foreach
    }// method

    private void fillAppDataBundle() {
        appDataBundle.putStringArrayList(CATEGORY_PARAM, filteredCategoryList);
        appDataBundle.putBundle(NAME_PARAM, nameBundle);
        appDataBundle.putBundle(AUTHOR_PARAM, authorBundle);
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

    public ArrayList<String> getFilteredCategoryList() {
        return filteredCategoryList;
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public ArrayList<String> getAuthorList() {
        return authorList;
    }

    public ArrayList<String> getImagePathList() {
        return imagePathList;
    }

    public ArrayList<String> getIdList() {
        return idList;
    }

    public ArrayList<String> getSummaryList() {
        return summaryList;
    }

    public ArrayList<String> getPriceList() {
        return priceList;
    }

    public ArrayList<String> getCopyrightList() {
        return copyrightList;
    }

    public ArrayList<String> getReleaseDateList() {
        return releaseDateList;
    }
}
