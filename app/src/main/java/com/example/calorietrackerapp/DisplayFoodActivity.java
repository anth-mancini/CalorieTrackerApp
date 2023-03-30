package com.example.calorietrackerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DisplayFoodActivity extends AppCompatActivity {

    private ArrayList<Food> foodList;
    private SharedPreferences prefs;
    Button btnClear;
    private Gson gson;

    private ListView foodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);

        foodListView = findViewById(R.id.foodListView);
        btnClear = findViewById(R.id.btnClear);

        prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        gson = new Gson();

        String json = prefs.getString("foodList", "");
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        foodList = gson.fromJson(json, type);

        if (foodList == null) {
            foodList = new ArrayList<>();
        }

        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        foodListView.setAdapter(adapter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllFoods();
            }
        });
    }

    private void deleteAllFoods() {
        foodList.clear();

        // Save the updated food list to SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(foodList);
        editor.putString("foodList", json);
        editor.apply();

        // Update the ListView to reflect the changes
        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        foodListView.setAdapter(adapter);
    }

}

