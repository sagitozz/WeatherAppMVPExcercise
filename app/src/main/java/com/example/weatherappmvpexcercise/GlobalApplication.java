package com.example.weatherappmvpexcercise;

import android.app.Application;
import android.content.Context;

//todo не над такго пафосного названия=) Назови просто "App"
// и вообще перепиши на котлин!!! У меня есть пример в TheMovie
public class GlobalApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
