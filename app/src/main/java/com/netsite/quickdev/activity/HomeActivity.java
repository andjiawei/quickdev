package com.netsite.quickdev.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.netsite.quickdev.R;
import com.netsite.quickdev.constants.ConstantValues;
import com.netsite.quickdev.core.BaseActivity;

/**
 * 主Activity  设计为singleTask 方便直接返回 oncreate不会每次都执行 因此在onNewIntent中再次判断
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {


    private Button profileButton;

    @Override
    protected void setUpData() {

        setContentView(R.layout.activity_home,R.string.home, R.menu.menu_home, MODE_HOME);
        profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_BACK_TO_HOME);
        switch (action) {
            case ConstantValues.ACTION_KICK_OUT:
                break;
            case ConstantValues.ACTION_LOGOUT:
                break;
            case ConstantValues.ACTION_RESTART_APP:
                protectApp();
                break;
            case ConstantValues.ACTION_BACK_TO_HOME:
                break;
        }
    }

    //重新启动app时 应从闪屏页开始 但android 会杀死activity保留栈信息 导致应用逻辑混乱。（ios无此问题）
    @Override
    protected void protectApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
