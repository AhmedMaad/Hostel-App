package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
    }

    public void login(View view) {
    }

    public void openSignupActivity(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
        finish();
    }
}