package com.law.aat;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Law on 2016/2/11.
 */
public class AssistiveTouchHandler extends Handler {
    public static final int MOVE_FLAG = 0x0001;
    public static final int CLICK_FLAG = 0x0002;
    public static final int LONG_CLICK_FLAG = 0x0003;

    private AssistiveTouchService mAssistiveTouchService;

    public AssistiveTouchHandler(AssistiveTouchService mAssistiveTouchService) {
        this.mAssistiveTouchService = mAssistiveTouchService;
    }

    @Override
    public void handleMessage(Message msg) {
//        super.handleMessage(msg);
        switch (msg.what) {
            case MOVE_FLAG:
                mAssistiveTouchService.updateAssistiveTouchPointPosition(msg.arg1, msg.arg2);
                break;
            case CLICK_FLAG:
                Toast.makeText(mAssistiveTouchService, "Click", Toast.LENGTH_SHORT).show();
                break;
            case LONG_CLICK_FLAG:
                Toast.makeText(mAssistiveTouchService, "LongClick", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
