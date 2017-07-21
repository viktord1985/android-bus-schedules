package com.test.smartbus;

import android.app.Application;

import com.test.smartbus.dbase.DatabaseManager;

/**
 * Created by Viktor Derk on 2/18/17.
 */

public class SmartBusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(getApplicationContext());
    }
}
