package com.netsite.quickdev.activity;

import android.widget.TextView;

import com.netsite.quickdev.R;
import com.netsite.quickdev.core.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private TextView mProfileLabel;

    @Override
    protected void setUpData() {

        setContentView(R.layout.activity_profile,R.string.profile);

        mProfileLabel = (TextView) findViewById(R.id.mProfileLabel);
        mProfileLabel.setText(R.string.profile);

    }


}
