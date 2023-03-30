package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private UserPreferenceManager prefsmanager;

    Button save_btn;
    RadioButton btn_male, btn_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prefsmanager = new UserPreferenceManager(this);

        save_btn = findViewById(R.id.buttonSave);
        btn_male = findViewById(R.id.radioButtonInpA);
        btn_female = findViewById(R.id.radioButtonInpB);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup genderradiogroup = findViewById(R.id.radioGroupInpGroup);
                int selectedId= genderradiogroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                String selectedGender = selectedRadioButton.getText().toString();
                prefsmanager.saveGender(selectedGender);

                Toast.makeText(ProfileActivity.this, "Gender Saved", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ProfileActivity.this, HeightActivity.class);
                startActivity(intent);
            }
        });


    }
}