package com.law.aat.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Jungle on 16/2/8.
 */
public class AssistiveTouchApplication extends Application {
    private static AssistiveTouchApplication mApplication;
    private Handler mApplicationHandler = new AppHandler(this);

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public Context getApplicationContext() {
        return getInstance().getApplicationContext();
    }

    private static AssistiveTouchApplication getInstance() {
        return mApplication;
    }
}
