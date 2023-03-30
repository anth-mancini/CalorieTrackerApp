package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewFoodActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    ListView myListView;
    ArrayList<String> myList;
    DBHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        myListView = (ListView) findViewById(R.id.mealList);
        myListView.setOnItemLongClickListener(this);
        myList = new ArrayList<String>();


        dbHelper = new DBHelper(this);
        userId = dbHelper.getUserId(HomeActivity.username);
        Cursor cursor = dbHelper.getFoodOptions();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String mealName = cursor.getString(cursor.getColumnIndex("meal_name"));
                /*@SuppressLint("Range") int calories = cursor.getInt(cursor.getColumnIndex("calories"));
                @SuppressLint("Range") int fat = cursor.getInt(cursor.getColumnIndex("fat"));
                @SuppressLint("Range") int cholesterol = cursor.getInt(cursor.getColumnIndex("cholesterol"));
                @SuppressLint("Range") int sodium = cursor.getInt(cursor.getColumnIndex("sodium"));
                @SuppressLint("Range") int carbs = cursor.getInt(cursor.getColumnIndex("carbs"));
                @SuppressLint("Range") int sugar = cursor.getInt(cursor.getColumnIndex("sugar"));*/

                myList.add(mealName);

            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.itemTextView, myList);
        myListView.setAdapter(myAdapter);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

        String selection = myList.get(pos);
        dbHelper.insertMealHistoryFromFoodOptions(userId, selection);

        Toast.makeText(ViewFoodActivity.this, "Meal successfully added!", Toast.LENGTH_LONG).show();


        return true;
    }
}