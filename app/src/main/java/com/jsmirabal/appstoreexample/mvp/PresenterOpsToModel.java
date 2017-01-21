package com.jsmirabal.appstoreexample.mvp;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.os.Bundle;

public interface PresenterOpsToModel {
    void insertData(Bundle data);
    void updateData();
    void deleteData();
    Bundle selectData();
}
