package com.jsmirabal.appstoreexample.mvp;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.os.Bundle;

public interface PresenterOpsToView {
    void onRequestDataSuccess(Bundle data);
    void onRequestDataError(Throwable e);
}
