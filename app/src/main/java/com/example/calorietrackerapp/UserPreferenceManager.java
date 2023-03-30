package com.example.calorietrackerapp;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserPreferenceManager {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_HEIGHT = "Height";
    private static final String FOOD_LIST_KEY = "FoodList";

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

    public ArrayList<Food> getFoodList() {
        Gson gson = new Gson();
        String json = prefs.getString(FOOD_LIST_KEY, "");
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addFoodToList(Food food) {
        ArrayList<Food> foodList = getFoodList();
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        foodList.add(food);
        Gson gson = new Gson();
        String json = gson.toJson(foodList);
        prefs.edit().putString(FOOD_LIST_KEY, json).apply();
    }


}
