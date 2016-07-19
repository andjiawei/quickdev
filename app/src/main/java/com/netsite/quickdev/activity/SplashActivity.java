package com.netsite.quickdev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.netsite.quickdev.R;
import com.netsite.quickdev.constants.ConstantValues;
import com.netsite.quickdev.core.AppStatusTracker;
import com.netsite.quickdev.core.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpData() {
        //int layoutResID, int titleResId, int mode
        setContentView(R.layout.activity_welcome, -1, MODE_NONE);//splash无title所以为-1和none
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }
}
