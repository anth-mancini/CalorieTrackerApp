package com.example.calorietrackerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {

    private ArrayList<Food> foodList;
    private SharedPreferences prefs;
    private Gson gson;

    private EditText foodNameEditText;
    private EditText calorieEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodNameEditText = findViewById(R.id.nameEditText);
        calorieEditText = findViewById(R.id.caloriesEditText);
        saveButton = findViewById(R.id.saveFoodbtn);

        prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        gson = new Gson();

        String json = prefs.getString("foodList", "");
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        foodList = gson.fromJson(json, type);

        if (foodList == null) {
            foodList = new ArrayList<>();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = foodNameEditText.getText().toString().trim();
                String calorieString = calorieEditText.getText().toString().trim();

                if (foodName.isEmpty() || calorieString.isEmpty()) {
                    Toast.makeText(AddFoodActivity.this, "Please enter food name and calorie number", Toast.LENGTH_SHORT).show();
                    return;
                }

                int calorie = Integer.parseInt(calorieString);

                Food newFood = new Food(foodName, calorie);
                foodList.add(newFood);

                String json = gson.toJson(foodList);
                prefs.edit().putString("foodList", json).apply();

                Toast.makeText(AddFoodActivity.this, "Food added successfully", Toast.LENGTH_SHORT).show();

                foodNameEditText.setText("");
                calorieEditText.setText("");
            }
        });
    }
}
