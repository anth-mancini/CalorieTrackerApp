package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

public class ProfileActivity extends AppCompatActivity {
    Button save_btn;
    RadioButton btn_male, btn_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        save_btn = findViewById(R.id.buttonSave);
        btn_male = findViewById(R.id.radioButtonInpA);
        btn_female = findViewById(R.id.radioButtonInpB);



    }
}