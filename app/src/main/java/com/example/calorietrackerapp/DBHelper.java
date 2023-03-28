package com.example.calorietrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB) {
        //create a table users with two columns: username, password
        myDB.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY, username TEXT, password TEXT, email TEXT, UNIQUE(username, email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {

        myDB.execSQL("drop Table if exists users");

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
        else
            return true;

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
}
