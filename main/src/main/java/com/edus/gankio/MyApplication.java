package com.edus.gankio;

import android.app.Application;

import apollo.edus.com.share.ShareContext;

/**
 * Description.
 *
 * @author panda
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareContext.getInstance().init(getApplicationContext());
    }
}
