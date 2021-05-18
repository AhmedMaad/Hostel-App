package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String user;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.nav_home);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        Intent a = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_settings:
                        Intent b = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }

    private void changeActivity() {
        if (user.equals("supplier")) {
            Intent intent = new Intent(MainActivity.this, ApartmentSupplier.class);
            startActivity(intent);
        } else if (user.equals("consumer")) {
            Intent intent = new Intent(MainActivity.this, AvailableApartments.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.supplier) {
            button = findViewById(R.id.supplier);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.consumer);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.apartment);
            button.setVisibility(View.VISIBLE);
            button = findViewById(R.id.room);
            button.setVisibility(View.VISIBLE);
            user = "supplier";
        } else if (v.getId() == R.id.consumer) {
            button = findViewById(R.id.supplier);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.consumer);
            button.setVisibility(View.GONE);
            button = findViewById(R.id.apartment);
            button.setVisibility(View.VISIBLE);
            button = findViewById(R.id.room);
            button.setVisibility(View.VISIBLE);
            user = "consumer";
        } else if (v.getId() == R.id.apartment) {
            changeActivity();
        } else if (v.getId() == R.id.room) {
            changeActivity();
        }
    }

    @Override
    public void onBackPressed() {
        button = findViewById(R.id.apartment);
        button.setVisibility(View.GONE);
        button = findViewById(R.id.room);
        button.setVisibility(View.GONE);
        button = findViewById(R.id.supplier);
        button.setVisibility(View.VISIBLE);
        button = findViewById(R.id.consumer);
        button.setVisibility(View.VISIBLE);
    }

}
