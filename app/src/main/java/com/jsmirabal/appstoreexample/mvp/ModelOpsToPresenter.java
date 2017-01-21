package com.jsmirabal.appstoreexample.mvp;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.database.SQLException;

public interface ModelOpsToPresenter {
    void onDataInsertSuccess(String s);
    void onDataInsertError(SQLException e);
}
