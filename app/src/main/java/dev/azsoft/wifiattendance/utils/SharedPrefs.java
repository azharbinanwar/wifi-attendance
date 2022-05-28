package dev.azsoft.wifiattendance.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {
    private static SharedPrefs mInstance;
    private SharedPreferences prefs;

    public static SharedPrefs getInstance() {
        if (mInstance == null) mInstance = new SharedPrefs();
        return mInstance;
    }

    public void Initialize(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, value);
        e.apply();
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(key, value);
        e.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

}
