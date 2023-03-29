package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  RegistrationActivity extends AppCompatActivity {

    EditText username, password, repassword, email;
    Button signup;

    DBHelper db;

    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        email = (EditText) findViewById(R.id.email);
        signup = (Button) findViewById(R.id.btnsignup);

        db = new DBHelper(this);

        signup.setOnClickListener(view -> {

            if(username != null && password != null && repassword != null && email != null){
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String em = email.getText().toString();

                //if entries pass all checks, insert into database. each function displays the appropriate error toast messages
                if(validateUsername(user) && validatePassword(pass, repass) && validateEmail(em)){
                    if(db.insertData(user, pass, em)){
                        Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Registration Failed. Please contact System Administrator.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
                Toast.makeText(RegistrationActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();


        });
        Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
        startActivity(intent);

    }


    /* Function to check if username is valid
     * Takes String as input
     * Returns true if username passes valid parameters, otherwise returns false
     */
    public boolean validateUsername(String u) {
        //check length
        if(u.length()<4 || u.length()>15) {
            Toast.makeText(RegistrationActivity.this, "Your username must be between 4-15 characters.", Toast.LENGTH_LONG).show();
            return false;
        }

        //check for valid characters
        this.pattern = Pattern.compile("^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
        this.matcher = this.pattern.matcher(u);
        if(this.matcher.find() == false) {
            Toast.makeText(RegistrationActivity.this, "Your username can only contain: A-Z, 0-9, with '_' and '.' as separators.", Toast.LENGTH_LONG).show();
            return false;
        }

        //check if username exists in database
        if(db.checkUsername(u)){
            Toast.makeText(RegistrationActivity.this, "Username already exists.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /* Function to check if password is valid
     * Takes String as input
     * Returns true if password passes valid parameters, otherwise returns false
     */
    public boolean validatePassword(String p, String rp) {
        //check length
        if (p.length() < 4 || p.length() > 15) {
            Toast.makeText(RegistrationActivity.this, "Your password must be between 4-15 characters.", Toast.LENGTH_LONG).show();
            return false;
        }

        //check if password meets security requirements
        this.pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{7,}$");
        this.matcher = this.pattern.matcher(p);
        if (!this.matcher.find()) {
            Toast.makeText(RegistrationActivity.this, "Your password must contain 7 characters, one uppercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show();
            return false;
        }

        //check if password + repassword are equal
        if(!p.equals(rp)){
            Toast.makeText(RegistrationActivity.this, "Your passwords must match.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /* Function to check for valid email address
     * Takes String as input
     * Returns true if email matches pattern (is valid), otherwise returns false
     */
    public boolean validateEmail(String e) {
        //check for valid email address sequence
        this.pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        this.matcher = this.pattern.matcher(e);
        if(!this.matcher.find()){
            Toast.makeText(RegistrationActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
            return false;
        }

        //check if email already exists in database
        if(db.checkEmail(e)){
            Toast.makeText(RegistrationActivity.this, "Email is already associated with another account.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}