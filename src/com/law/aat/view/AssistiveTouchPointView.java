package com.law.aat.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.law.aat.AssistiveTouchHandler;
import com.law.aat.utils.ViewUtils;

/**
 * Created by Jungle on 16/2/8.
 */
public class AssistiveTouchPointView extends ImageView {
    private Handler mHandler;
    private float mLastRawX = -1f, mLastRawY = -1f;
    private long actionDownMillis = 0l;
    private long actionUpMillis = 0l;
    private boolean pressedFlag = false;
    private boolean longPressable = false;
    private boolean movedFlag = false;

    public AssistiveTouchPointView(Context context) {
        this(context, null, -1);
    }

    public AssistiveTouchPointView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AssistiveTouchPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mRawX =;
//        mRawY =;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Toast.makeText(getContext(), "ACTION_DOWN", Toast.LENGTH_SHORT).show();
//                setVisibility(View.INVISIBLE);
                if (!pressedFlag) {
                    actionDownMillis = System.currentTimeMillis();
                    pressedFlag = true;
                    longPressable = true;
                    movedFlag = false;
                    mLastRawX = event.getRawX();
                    mLastRawY = event.getRawY();
                    new Thread(new CheckForLongClickRunnable()).start();
                }

                break;
            case MotionEvent.ACTION_MOVE:
//                Toast.makeText(getContext(), "ACTION_MOVE", Toast.LENGTH_SHORT).show();
                movedFlag = true;
                Message mMessage = new Message();
                mMessage.arg1 = (int) event.getRawX();
                mMessage.arg2 = (int) event.getRawY() - ViewUtils.getStatusBarHeight(getContext());
                mMessage.what = AssistiveTouchHandler.MOVE_FLAG;
                mHandler.sendMessage(mMessage);
                break;
            case MotionEvent.ACTION_UP:
//                Toast.makeText(getContext(), "ACTION_UP", Toast.LENGTH_SHORT).show();
//                setVisibility(View.VISIBLE);
                pressedFlag = false;
                longPressable = false;
                movedFlag = false;
                actionUpMillis = System.currentTimeMillis();
                if (actionUpMillis - actionDownMillis < 250l) {
                    Message mClickMessage = new Message();
                    mClickMessage.what = AssistiveTouchHandler.CLICK_FLAG;
                    mHandler.sendMessage(mClickMessage);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isLongPressable() {
        return longPressable;
    }

    private class CheckForLongClickRunnable implements Runnable {
        @Override
        public void run() {
            boolean checking = true;
            while (checking) {
                if (Math.abs(System.currentTimeMillis() - actionDownMillis) > 500) {
                    if (!movedFlag && longPressable && pressedFlag) {
                        Log.i("TAG", "LongClick");
                        Message mLongMessage = new Message();
                        mLongMessage.what = AssistiveTouchHandler.LONG_CLICK_FLAG;
                        mHandler.sendMessage(mLongMessage);
                        longPressable = false;
                    } else {
                        Log.i("TAG", "unLongClick");
                    }
                    checking = false;
                }
            }
        }
    }

}
