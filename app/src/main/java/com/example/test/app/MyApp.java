package com.example.test.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.db.DaoMaster;
import com.example.test.db.DaoSession;
import com.live.app.MyApplication;

import java.util.HashMap;

public class MyApp extends Application {
    private static String[] modules = {"com.live.app.MyApplication"};

    public static MyApp app;
    public static HashMap<String, Object> map;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        map = new HashMap<>();
        initDB();

        initMoudles();
    }

    private void initMoudles() {
        for (String moduleImpl : modules){
            try {//通过反射
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof MyApplication){
                    ((MyApplication) obj).initApp(app);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void initDB() {
        // 参数1 上下文 参数2.数据库名字
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "person");
        SQLiteDatabase db = helper.getWritableDatabase();
        mDaoSession = new DaoMaster(db).newSession();
    }

    //对外提供DaoSession对象
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static MyApp getInstance() {
        return app;
    }

    private static Context mAppContext = null;

    public static HashMap<String, Object> getMap() {
        return map;
    }
}
