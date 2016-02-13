package com.law.aat.utils;

import android.content.Context;

/**
 * Created by Law on 2016/2/11.
 */
public class ViewUtils {
    public static int getStatusBarHeight(Context paramContext) {
        int k = 0;
        try {
            Class localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int m = Integer.parseInt(localClass.getField("status_bar_height")
                    .get(localObject).toString());
            int n = paramContext.getResources().getDimensionPixelSize(m);
            k = n;
            return k;
        } catch (Exception localException) {
            while (true)
                localException.printStackTrace();
        }
    }
}
