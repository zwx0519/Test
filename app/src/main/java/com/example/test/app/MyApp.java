package com.example.test.app;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

public class MyApp extends Application {

    public static MyApp app;
    public static HashMap<String,Object> map;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        map=new HashMap<>();
    }

    public static MyApp getInstance() {
        return app;
    }

    private static Context mAppContext = null;

    public static HashMap<String, Object> getMap() {
        return map;
    }
}
