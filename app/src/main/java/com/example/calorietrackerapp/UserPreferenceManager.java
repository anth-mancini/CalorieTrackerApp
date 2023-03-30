package com.example.calorietrackerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferenceManager {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_HEIGHT = "Height";

    private final SharedPreferences prefs;

    public UserPreferenceManager(Context context){
        prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

    }
    public void saveGender(String gender){
        prefs.edit().putString(KEY_GENDER,gender).apply();
    }

    public String getGender(){
        return prefs.getString(KEY_GENDER,"");
    }

    public void saveHeight(int feet, int inches){
        String height = feet + "'" + inches;
        prefs.edit().putString(KEY_HEIGHT,height).apply();

    }

    public String getHeight(){
        return prefs.getString(KEY_HEIGHT,"");
    }


}
