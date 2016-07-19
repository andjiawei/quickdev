package com.netsite.quickdev.core;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.netsite.quickdev.R;
import com.netsite.quickdev.activity.HomeActivity;
import com.netsite.quickdev.constants.ConstantValues;

/**
 * 有3类信息
 *
 * 1 setcontentview 和toolbar
 *
 * 2 判断activity状态
 *
 * 3 setupdata 设置数据留给子类实现
 *
 */


public class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;
    private Toolbar toolbar;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (AppStatusTracker.getInstance().getAppStatus()) {
            case ConstantValues.STATUS_FORCE_KILLED:
                protectApp();//强杀后 重新走一遍逻辑
                break;
            case ConstantValues.STATUS_KICK_OUT:
                kickOut();//踢人
                break;
            case ConstantValues.STATUS_LOGOUT:
            case ConstantValues.STATUS_OFFLINE:
            case ConstantValues.STATUS_ONLINE:
                setUpData();
                break;
        }

    }

    protected void protectApp() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_RESTART_APP);
        startActivity(intent);
    }

    protected void kickOut() {
        //TODO show dialog to confirm
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_KICK_OUT);
        startActivity(intent);
    }

    protected void setUpData() {}

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(layoutResID, -1, -1, MODE_BACK);
    }
    //login页面调用
    public void setContentView(int layoutResID, int titleResId) {
        setContentView(layoutResID, titleResId, -1, MODE_BACK);
    }

    //splash子类调用
    public void setContentView(int layoutResID, int titleResId, int mode) {
        setContentView(layoutResID, titleResId, -1, mode);
    }

    //最终都调用这个方法
    public void setContentView(int layoutResID, int titleResId, int menuId, int mode) {
        super.setContentView(layoutResID);
        setUpToolbar(titleResId, menuId, mode);
    }

    private void setUpToolbar(int titleResId, int menuId, int mode) {

        if (mode != MODE_NONE) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("");
            toolbar.setBackgroundColor(Color.BLACK);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            if (mode == MODE_BACK) {
                toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationBtnClicked();
                }
            });
            setUpTitle(titleResId);
            setUpMenu(menuId);
        }
    }
    protected void onNavigationBtnClicked() {
        finish();
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && toolbar_title != null) {
            toolbar_title.setText(titleResId);
        }
    }

    protected void setUpMenu(int menuId) {
        if (toolbar != null){
            toolbar.getMenu().clear();
            if (menuId > 0) {
                toolbar.inflateMenu(menuId);
                toolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
