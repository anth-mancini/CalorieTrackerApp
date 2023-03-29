package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class HeightActivity extends AppCompatActivity {
    Button save, btn_switch_units;
    EditText et_height_feet;
    EditText et_height_inches;
    EditText et_height_cm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        save = findViewById(R.id.buttonSave);
        et_height_feet = findViewById(R.id.editTextHeightFeet);
        et_height_inches = findViewById(R.id.editTextHeightInches);
        et_height_cm = findViewById(R.id.editTextHeightCm);
        btn_switch_units = findViewById(R.id.buttonSwitchMeasurement);





    }

}