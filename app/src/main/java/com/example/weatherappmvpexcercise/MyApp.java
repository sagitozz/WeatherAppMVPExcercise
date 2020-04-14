package com.example.weatherappmvpexcercise;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public void OnCreate () {
        instance = this;
        super.onCreate();
    }
}
