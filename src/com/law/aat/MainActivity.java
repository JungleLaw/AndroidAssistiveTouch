package com.law.aat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mEnableButton, mDisableButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mEnableButton = (Button) findViewById(R.id.enable_btn);
        mDisableButton = (Button) findViewById(R.id.disable_btn);
        mEnableButton.setOnClickListener(this);
        mDisableButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enable_btn:
                launchService();
                break;
            case R.id.disable_btn:
                shutdownService();
                break;
            default:
        }
    }
    private void launchService() {
        Intent mIntent = new Intent(this,AssistiveTouchService.class);
        startService(mIntent);
    }
    private void shutdownService() {
        Intent mIntent = new Intent(this,AssistiveTouchService.class);
        stopService(mIntent);
    }
}
