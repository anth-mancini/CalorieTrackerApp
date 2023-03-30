package com.example.calorietrackerapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DBNAME = "Login.db";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREFS_ADDED_FLAG = "addedFlag";

    private SharedPreferences prefs;

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB) {
        //create a table users with two columns: username, password
        myDB.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY, username TEXT, password TEXT, email TEXT, UNIQUE(username, email))");

        //create a table meal_history with columns: user_id (foreign key), meal_name, and calories, other nutrition facts
        myDB.execSQL("CREATE TABLE meal_history(id INTEGER PRIMARY KEY, user_id INTEGER, meal_name TEXT, " +
                "calories INTEGER, fat INTEGER, cholesterol INTEGER, sodium INTEGER, carbs INTEGER, sugar INTEGER, " +
                "protein INTEGER, FOREIGN KEY(user_id) REFERENCES users(id))");

        //create a table food_options with columns: user_id (foreign key), meal_name, and calories, other nutrition facts
        myDB.execSQL("CREATE TABLE food_options(id INTEGER PRIMARY KEY, meal_name TEXT, " +
                "calories INTEGER, fat INTEGER, cholesterol INTEGER, sodium INTEGER, carbs INTEGER, sugar INTEGER, " +
                "protein INTEGER)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Upgrade to version 2
            db.execSQL("ALTER TABLE meal_history RENAME TO temp_meal_history");
            db.execSQL("CREATE TABLE meal_history(id INTEGER PRIMARY KEY, user_id INTEGER, meal_name TEXT, " +
                    "calories INTEGER, fat INTEGER, cholesterol INTEGER, sodium INTEGER, carbs INTEGER, sugar INTEGER, " +
                    "protein INTEGER, FOREIGN KEY(user_id) REFERENCES users(id))");
            db.execSQL("INSERT INTO meal_history(id, user_id, meal_name, calories, fat, cholesterol, sodium, carbs, sugar, protein) " +
                    "SELECT id, user_id, meal_name, calories, fat, cholesterol, sodium, carbs, sugar, protein FROM temp_meal_history");
            db.execSQL("DROP TABLE temp_meal_history");
        }
    }

    public Boolean insertData(String username, String password, String email){
        SQLiteDatabase myDB = this.getWritableDatabase();

        //check if a user with the same username or email already exists
        if (checkUsername(username) || checkEmail(email)) {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);

        //put contentValues inside the DB
        long result = myDB.insert("users", null, contentValues);

        //check if insert failed or not
        if(result==-1)
            return false;
        else {
            if(!prefs.getBoolean(PREFS_ADDED_FLAG, false)){
                insertFoodOptions("Peanut Butter Sandwich", 320, 18, 0, 400, 32, 5, 11);
                insertFoodOptions("Ham and Cheese Omelette", 540, 21, 475, 810, 2, 2, 32);
                insertFoodOptions("Roast Chicken and Potatoes", 600, 5, 90, 160, 110, 0, 30);
                prefs.edit().putBoolean(PREFS_ADDED_FLAG, true).apply();
                return true;
            }
            return true;
        }
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?", new String[]{username});

        //check if username exists
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where email = ?", new String[]{email});

        //check if email exists
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?", new String []{username, password});

        //check for username + password match
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    @SuppressLint("Range")
    public int getUserId(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        try (Cursor cursor = myDB.rawQuery("Select id from users where username = ?", new String[]{username})) {

            //return user id
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex("id"));
            } else {
                return -1; //error
            }
        }
    }

    public Boolean insertMealHistory(int userId, String mealName, int calories, int fat, int cholesterol, int sodium, int carbs, int sugar, int protein){
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("meal_name", mealName);
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("cholesterol", cholesterol);
        contentValues.put("sodium", sodium);
        contentValues.put("carbs", carbs);
        contentValues.put("sugar", sugar);
        contentValues.put("protein", protein);

        //put contentValues inside the DB
        long result = myDB.insert("meal_history", null, contentValues);

        //check if insert failed or not
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getMealHistory(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"id", "user_id", "meal_name", "calories", "fat", "cholesterol", "sodium", "carbs", "sugar", "protein"};
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        String sortOrder = "id DESC";
        return db.query("meal_history", projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Boolean insertFoodOptions(String mealName, int calories, int fat, int cholesterol, int sodium, int carbs, int sugar, int protein){
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("meal_name", mealName);
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("cholesterol", cholesterol);
        contentValues.put("sodium", sodium);
        contentValues.put("carbs", carbs);
        contentValues.put("sugar", sugar);
        contentValues.put("protein", protein);

        //put contentValues inside the DB
        long result = myDB.insert("food_options", null, contentValues);

        //check if insert failed or not
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getFoodOptions() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String[] columns = {"meal_name", "calories", "fat", "cholesterol", "sodium", "carbs", "sugar", "protein"};
        Cursor cursor = myDB.query("food_options", columns, null, null, null, null, null);
        return cursor;
    }

    public Boolean insertMealHistoryFromFoodOptions(int userId, String mealName) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        // query food_options table to get the nutrition information for the meal
        Cursor cursor = myDB.rawQuery("SELECT calories, fat, cholesterol, sodium, carbs, sugar, protein " +
                "FROM food_options WHERE meal_name = ?", new String[]{mealName});

        if (cursor.moveToFirst()) {
            // extract nutrition information from cursor
            @SuppressLint("Range") int calories = cursor.getInt(cursor.getColumnIndex("calories"));
            @SuppressLint("Range") int fat = cursor.getInt(cursor.getColumnIndex("fat"));
            @SuppressLint("Range") int cholesterol = cursor.getInt(cursor.getColumnIndex("cholesterol"));
            @SuppressLint("Range") int sodium = cursor.getInt(cursor.getColumnIndex("sodium"));
            @SuppressLint("Range") int carbs = cursor.getInt(cursor.getColumnIndex("carbs"));
            @SuppressLint("Range") int sugar = cursor.getInt(cursor.getColumnIndex("sugar"));
            @SuppressLint("Range") int protein = cursor.getInt(cursor.getColumnIndex("protein"));

            // insert meal history into the database
            ContentValues contentValues = new ContentValues();
            contentValues.put("user_id", userId);
            contentValues.put("meal_name", mealName);
            contentValues.put("calories", calories);
            contentValues.put("fat", fat);
            contentValues.put("cholesterol", cholesterol);
            contentValues.put("sodium", sodium);
            contentValues.put("carbs", carbs);
            contentValues.put("sugar", sugar);
            contentValues.put("protein", protein);
            long result = myDB.insert("meal_history", null, contentValues);

            return result != -1;
        } else {
            return false;
        }
    }



}
