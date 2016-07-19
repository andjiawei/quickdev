package com.netsite.quickdev.core;


import com.netsite.quickdev.constants.ConstantValues;

public class AppStatusTracker {
    private static AppStatusTracker tracker;
    private int mAppStatus = ConstantValues.STATUS_FORCE_KILLED;

    private AppStatusTracker() {

    }

    public static AppStatusTracker getInstance() {
        if (tracker == null) {
            tracker = new AppStatusTracker();
        }
        return tracker;
    }

    public void setAppStatus(int status) {
        this.mAppStatus = status;
    }

    public int getAppStatus() {
        return this.mAppStatus;
    }

}
