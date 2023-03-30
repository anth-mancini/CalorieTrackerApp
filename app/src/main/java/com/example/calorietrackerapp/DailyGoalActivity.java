package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DailyGoalActivity extends AppCompatActivity {

    EditText dailyCalorieGoal, userWeight;
    Button saveDailyGoal;
    TextView goalDisplay, goalValue, exerciseInfo, userWeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_goal);

        dailyCalorieGoal = findViewById(R.id.daily_calorie_goal);
        userWeight = findViewById(R.id.user_weight);
        userWeightValue = findViewById(R.id.user_weight_value);
        saveDailyGoal = findViewById(R.id.save_daily_goal);
        goalDisplay = findViewById(R.id.goal_display);
        goalValue = findViewById(R.id.goal_value);
        exerciseInfo = findViewById(R.id.exercise_info);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String storedGoal = prefs.getString("daily_calorie_goal", "");
        if (!storedGoal.isEmpty()) {
            dailyCalorieGoal.setText(storedGoal);
            goalValue.setText(storedGoal);
        }

        saveDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal = dailyCalorieGoal.getText().toString();
                String weight = userWeight.getText().toString();

                if (!goal.isEmpty() && !weight.isEmpty()) {
                    SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();
                    editor.putString("daily_calorie_goal", goal);
                    editor.apply();

                    // Display the calorie goal and weight in formatted strings
                    goalValue.setText(String.format("Calorie Goal: %s", goal));
                    userWeightValue.setText(String.format("Weight: %s", weight));

                    // Calculate exercise needed to lose the calories
                    double weightKg = Double.parseDouble(weight);
                    double caloriesToLose = Double.parseDouble(goal);

                    // Assuming a MET value of 5 for moderate exercise
                    double metValue = 5;

                    // Calculate the duration of the exercise in minutes
                    double exerciseDuration = (caloriesToLose / (metValue * 3.5 * weightKg)) * 200;
                    int roundedExerciseDuration = (int) Math.round(exerciseDuration);

                    exerciseInfo.setText(String.format(Locale.getDefault(),
                            "To lose %s calories, you should do moderate exercise for %d minutes.",
                            goal, roundedExerciseDuration));

                    Toast.makeText(DailyGoalActivity.this, "Daily calorie goal saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DailyGoalActivity.this, "Please enter a daily calorie goal and your weight.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
