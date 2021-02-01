package com.absolutecurepharma;

import android.app.Application;

public  class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PrefManager prefManager=new PrefManager();
    }




}
