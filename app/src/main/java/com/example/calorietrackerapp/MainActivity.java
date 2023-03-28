package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin, btnReg;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnLogin = (Button) findViewById(R.id.btnsignin1);
        btnReg = (Button) findViewById(R.id.btnreg);
        db = new DBHelper(this);

        //create account to use for debugging
        db.insertData("admin", "pass", "admin@debug.com");

        btnLogin.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();

            //check for null pointer
            if(user.isEmpty() || pass.isEmpty())
                Toast.makeText(MainActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            else {
                //check database for matching username + password
                Boolean checkUserPass = db.checkUsernamePassword(user, pass);

                //send user to home page
                if(checkUserPass){
                    Toast.makeText(MainActivity.this, "Sign-in successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            }
        });

        //send user to registration page
        btnReg.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        });

    }



}