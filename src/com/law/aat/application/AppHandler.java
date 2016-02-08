package com.law.aat.application;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Jungle on 16/2/8.
 */
public class AppHandler extends Handler {
    private Context mContext;
    public AppHandler(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
