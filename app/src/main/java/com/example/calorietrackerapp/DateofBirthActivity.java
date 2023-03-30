package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class DateofBirthActivity extends AppCompatActivity {
    private Spinner daySpinner, monthSpinner, yearSpinner;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateof_birth);

        daySpinner = findViewById(R.id.daySpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        saveButton = findViewById(R.id.saveButton);


        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        daySpinner.setAdapter(dayAdapter);

        // Populate month spinner
        String[] months = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthAdapter);

        // Populate year spinner
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yearAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Integer.parseInt(daySpinner.getSelectedItem().toString());
                String month = monthSpinner.getSelectedItem().toString();
                int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                Toast.makeText(DateofBirthActivity.this, "Date of birth: " + day + " " + month + " " + year, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DateofBirthActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}