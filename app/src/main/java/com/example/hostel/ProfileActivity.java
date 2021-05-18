package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            button = findViewById(R.id.edit);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.myAds);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.done);
            button.setVisibility(View.VISIBLE);
            editText = findViewById(R.id.profileName);
            editText.setEnabled(true);
            editText = findViewById(R.id.profileGender);
            editText.setEnabled(true);
            editText = findViewById(R.id.profileAddress);
            editText.setEnabled(true);
            editText = findViewById(R.id.profileNumber);
            editText.setEnabled(true);
        } else if (v.getId() == R.id.done) {
            button = findViewById(R.id.edit);
            button.setVisibility(View.VISIBLE);
            button = findViewById(R.id.myAds);
            button.setVisibility(View.VISIBLE);
            button = findViewById(R.id.done);
            button.setVisibility(View.GONE);
            editText = findViewById(R.id.profileName);
            editText.setEnabled(false);
            editText = findViewById(R.id.profileGender);
            editText.setEnabled(false);
            editText = findViewById(R.id.profileAddress);
            editText.setEnabled(false);
            editText = findViewById(R.id.profileNumber);
            editText.setEnabled(false);
        }
    }
}