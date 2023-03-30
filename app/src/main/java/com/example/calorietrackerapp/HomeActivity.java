package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button goToDailyGoal = findViewById(R.id.go_to_daily_goal);
        Button btnAddFood = findViewById(R.id.addfoodbtn);
        Button btnFoodList = findViewById(R.id.btn_displayFood);
        goToDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DailyGoalActivity.class);
                startActivity(intent);
            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });

        btnFoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DisplayFoodActivity.class);
                startActivity(intent);
            }
        });

    }


}