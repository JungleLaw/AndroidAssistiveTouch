package com.law.aat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Jungle on 16/2/8.
 */
public class AssistiveTouchPointView extends ImageView {
    public AssistiveTouchPointView(Context context) {
        this(context, null, -1);
    }

    public AssistiveTouchPointView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AssistiveTouchPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AssistiveTouchPointView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
