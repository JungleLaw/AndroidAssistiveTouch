package com.law.aat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.law.aat.view.AssistiveTouchPointView;

/**
 * Created by Jungle on 16/2/8.
 */
public class AssistiveTouchService extends Service {
    private Context mAppContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mAssistiveTouchPointViewLayoutParams;
    private int mMetricsWidth, mMetricsHeight;
    private DisplayMetrics mDisplayMetrics;
    private LayoutInflater mLayoutInflater;
    private AssistiveTouchPointView mAssistiveTouchPointView;
    private AssistiveTouchHandler mAssistiveTouchHandler;

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
        if (mAssistiveTouchPointView != null) {
            mWindowManager.removeView(mAssistiveTouchPointView);
        }
    }

    private void onConfigure() {
        mAppContext = getApplicationContext();
        mWindowManager = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        configureDisplayMetrics();
        configureAssistiveTouchPointView();

//        mAssistiveTouchPointView.setVisibility(View.INVISIBLE);
    }

    private void configureAssistiveTouchPointView() {
        mAssistiveTouchPointViewLayoutParams = new WindowManager.LayoutParams();
        mAssistiveTouchPointViewLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mAssistiveTouchPointViewLayoutParams.format = PixelFormat.RGBA_8888;
//        mWindowManagerLayoutParams.flags = 51;
        mAssistiveTouchPointViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mAssistiveTouchPointViewLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mAssistiveTouchPointViewLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mAssistiveTouchPointViewLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mAssistiveTouchPointViewLayoutParams.x = 0;
        mAssistiveTouchPointViewLayoutParams.y = 0;

        mAssistiveTouchHandler = new AssistiveTouchHandler(this);
        mAssistiveTouchPointView = new AssistiveTouchPointView(mAppContext);
        mAssistiveTouchPointView.setHandler(mAssistiveTouchHandler);
        mAssistiveTouchPointView.setImageResource(R.drawable.touch_point_view_selector);
        mWindowManager.addView(mAssistiveTouchPointView, mAssistiveTouchPointViewLayoutParams);
    }

    private void configureDisplayMetrics() {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = mAppContext.getResources().getDisplayMetrics();
            mMetricsWidth = mDisplayMetrics.widthPixels;
            mMetricsHeight = mDisplayMetrics.heightPixels;
        }
    }

    public void updateAssistiveTouchPointPosition(int x, int y) {
        mAssistiveTouchPointViewLayoutParams.x = x;
        mAssistiveTouchPointViewLayoutParams.y = y;

        mWindowManager.updateViewLayout(mAssistiveTouchPointView, mAssistiveTouchPointViewLayoutParams);
    }
}
