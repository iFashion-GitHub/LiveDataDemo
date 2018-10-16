package com.fqxyi;

import android.app.Application;

import com.fqxyi.network.RequestManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.get().init(this, "http://104.224.173.101:8080/");
    }
}
