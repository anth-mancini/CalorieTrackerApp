package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HeightActivity extends AppCompatActivity {
        private EditText feetEditText, inchesEditText;
        private Button saveButton;
        private SharedPreferences sharedPreferences;
        private static final String HEIGHT_PREFS = "height_prefs";
        private static final String FEET_KEY = "feet";
        private static final String INCHES_KEY = "inches";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_height);

            feetEditText = findViewById(R.id.editTextHeightFeet);
            inchesEditText = findViewById(R.id.editTextHeightInches);
            saveButton = findViewById(R.id.buttonSave);

            sharedPreferences = getSharedPreferences(HEIGHT_PREFS, MODE_PRIVATE);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String feetString = feetEditText.getText().toString();
                    String inchesString = inchesEditText.getText().toString();

                    if (feetString.isEmpty() || inchesString.isEmpty()) {
                        Toast.makeText(HeightActivity.this, "Please enter a value for both feet and inches", Toast.LENGTH_SHORT).show();
                    } else {
                        int feet = Integer.parseInt(feetString);
                        int inches = Integer.parseInt(inchesString);
                        int totalInches = feet * 12 + inches;

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(FEET_KEY, feet);
                        editor.putInt(INCHES_KEY, inches);
                        editor.apply();

                        Toast.makeText(HeightActivity.this, "Height saved", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(HeightActivity.this, DateofBirthActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();
            int feet = sharedPreferences.getInt(FEET_KEY, 0);
            int inches = sharedPreferences.getInt(INCHES_KEY, 0);
            feetEditText.setText(String.valueOf(feet));
            inchesEditText.setText(String.valueOf(inches));
        }
    }