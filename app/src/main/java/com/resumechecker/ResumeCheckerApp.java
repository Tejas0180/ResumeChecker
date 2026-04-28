package com.resumechecker;

import android.app.Application;
import com.resumechecker.db.AppDatabase;

public class ResumeCheckerApp extends Application {

    private static ResumeCheckerApp instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static ResumeCheckerApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
