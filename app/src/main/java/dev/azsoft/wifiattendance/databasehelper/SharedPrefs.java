package dev.azsoft.wifiattendance.databasehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {

    private static SharedPrefs mInstance;
    private SharedPreferences prefs;


    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }



    public static SharedPrefs getInstance() {
        if (mInstance == null) mInstance = new SharedPrefs();
        return mInstance;
    }

    public void Initialize(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return prefs.getString(key, "");
    }

    public void setBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void clear(){
        prefs.edit().clear().apply();
    }

}
