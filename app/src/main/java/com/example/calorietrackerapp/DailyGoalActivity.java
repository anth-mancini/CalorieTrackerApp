package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DailyGoalActivity extends AppCompatActivity {

    EditText dailyCalorieGoal;
    Button saveDailyGoal;
    TextView goalDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_goal);

        dailyCalorieGoal = findViewById(R.id.daily_calorie_goal);
        saveDailyGoal = findViewById(R.id.save_daily_goal);
        goalDisplay = findViewById(R.id.goal_display);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String storedGoal = prefs.getString("daily_calorie_goal", "");
        if (!storedGoal.isEmpty()) {
            dailyCalorieGoal.setText(storedGoal);
            goalDisplay.setText(String.format("Current daily calorie goal: %s", storedGoal));
        }

        saveDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal = dailyCalorieGoal.getText().toString();

                if (!goal.isEmpty()) {
                    SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();
                    editor.putString("daily_calorie_goal", goal);
                    editor.apply();

                    goalDisplay.setText(String.format("Current daily calorie goal: %s", goal));

                    Toast.makeText(DailyGoalActivity.this, "Daily calorie goal saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DailyGoalActivity.this, "Please enter a daily calorie goal.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
