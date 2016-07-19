package com.netsite.quickdev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.netsite.quickdev.R;
import com.netsite.quickdev.constants.ConstantValues;
import com.netsite.quickdev.core.AppStatusTracker;
import com.netsite.quickdev.core.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mLoginSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login, R.string.login_title);
        mLoginSubmitBtn = (Button) findViewById(R.id.loginButton);
        mLoginSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_ONLINE);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
