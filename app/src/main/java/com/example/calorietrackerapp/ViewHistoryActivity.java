package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ViewHistoryActivity extends AppCompatActivity {

    TextView myView;
    int calCount, fatCount, cholCount, sodCount, carbCount, sugarCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        calCount = 0;
        fatCount = 0;
        cholCount = 0;
        sodCount = 0;
        carbCount = 0;
        sugarCount = 0;

        DBHelper dbHelper = new DBHelper(this);
        int userId = dbHelper.getUserId(HomeActivity.username);
        myView = (TextView) findViewById(R.id.myTextView);
        Cursor cursor = dbHelper.getMealHistory(userId);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String mealName = cursor.getString(cursor.getColumnIndex("meal_name"));
                @SuppressLint("Range") int calories = cursor.getInt(cursor.getColumnIndex("calories"));
                @SuppressLint("Range") int fat = cursor.getInt(cursor.getColumnIndex("fat"));
                @SuppressLint("Range") int cholesterol = cursor.getInt(cursor.getColumnIndex("cholesterol"));
                @SuppressLint("Range") int sodium = cursor.getInt(cursor.getColumnIndex("sodium"));
                @SuppressLint("Range") int carbs = cursor.getInt(cursor.getColumnIndex("carbs"));
                @SuppressLint("Range") int sugar = cursor.getInt(cursor.getColumnIndex("sugar"));

                calCount += calories;
                fatCount += fat;
                cholCount += cholesterol;
                sodCount += sodium;
                carbCount += carbs;
                sugarCount += sugar;

                displayData(mealName, calories, fat, cholesterol, sodium, carbs, sugar);
            } while (cursor.moveToNext());
        }
        finishData();
        cursor.close();
    }

    public void displayData(String m, int cal, int fat, int chol, int sod, int carbs, int sugar){
        myView.append("Meal name: "+m+"\n Calories: "+cal+" Fat: "+fat+" Cholesterol: "+chol+" Sodium: "+sod+" Carbs: "+carbs+" Sugar: "+sugar+"\n\n\n");
    }

    public void finishData(){
        myView.append("\n\n TOTAL COUNT: \nCalories = "+calCount+"\nFat = "+fatCount+"g\nCholesterol = "+cholCount+"g\nSodium = "+sodCount+"mg\nCarbs = "+carbCount+"g\nSugar = "+sugarCount+"g");
    }
}