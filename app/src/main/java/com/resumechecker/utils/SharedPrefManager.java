package com.resumechecker.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "ResumeCheckerPrefs";
    private static final String KEY_TARGET_ROLE = "target_role";
    private static final String KEY_EXPERIENCE_LEVEL = "experience_level";

    private final SharedPreferences prefs;

    private static SharedPrefManager instance;

    private SharedPrefManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public String getTargetRole() {
        return prefs.getString(KEY_TARGET_ROLE, "Android Developer");
    }

    public void setTargetRole(String role) {
        prefs.edit().putString(KEY_TARGET_ROLE, role).apply();
    }

    public String getExperienceLevel() {
        return prefs.getString(KEY_EXPERIENCE_LEVEL, "Fresher");
    }

    public void setExperienceLevel(String level) {
        prefs.edit().putString(KEY_EXPERIENCE_LEVEL, level).apply();
    }
}
