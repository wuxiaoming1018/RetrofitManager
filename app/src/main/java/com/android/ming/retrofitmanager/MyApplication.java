package com.android.ming.retrofitmanager;

import android.app.Application;

import com.android.ming.retrofitmanager.config.ProjectInit;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProjectInit.init(this)
                .withApiHost("http://www.kuaidi100.com/")
                .configure();
    }
}
