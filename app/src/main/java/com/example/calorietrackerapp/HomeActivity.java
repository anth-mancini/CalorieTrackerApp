package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button foodDBbtn, historyBtn;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        username = i.getExtras().getString("username");

        foodDBbtn = (Button) findViewById(R.id.foodDBbtn);

        foodDBbtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ViewFoodActivity.class);
            startActivity(intent);
        });

        historyBtn = (Button) findViewById(R.id.viewHistoryBtn);

        historyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ViewHistoryActivity.class);
            startActivity(intent);
        });

        Button goToDailyGoal = findViewById(R.id.go_to_daily_goal);
        goToDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DailyGoalActivity.class);
                startActivity(intent);
            }
        });

    }


}