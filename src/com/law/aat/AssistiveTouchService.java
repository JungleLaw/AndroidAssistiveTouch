package com.law.aat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by Jungle on 16/2/8.
 */
public class AssistiveTouchService extends Service {
    private Context mAppContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowManagerLayoutParams;
    private int mMetricsWidth, mMetricsHeight;
    private DisplayMetrics mDisplayMetrics;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "OnCreate");
        onConfigure();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy");
    }

    private void onConfigure() {
        mAppContext = getApplicationContext();
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        mWindowManagerLayoutParams = new WindowManager.LayoutParams();
        mWindowManagerLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWindowManagerLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowManagerLayoutParams.flags = 51;
        configureDisplayMetrics();

    }
    private void configureDisplayMetrics() {
        if(mDisplayMetrics==null){
            mDisplayMetrics = mAppContext.getResources().getDisplayMetrics();
            mMetricsWidth = mDisplayMetrics.widthPixels;
            mMetricsHeight = mDisplayMetrics.heightPixels;
        }

    }
}
