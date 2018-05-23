package com.company.stockmonitor;

import android.content.SharedPreferences;

public class SPHandler {

    private static SPHandler instance = null;
    private SharedPreferences sharedPref;

    protected SPHandler() {

    }

    public static SPHandler getInstance() {
        if (instance == null) {
            instance = new SPHandler();
        }
        return instance;
    }

    public void setSharedPref(SharedPreferences sp) {
        this.sharedPref = sp;
    }
    public SharedPreferences getSharedPref() {
        return sharedPref;
    }
}
