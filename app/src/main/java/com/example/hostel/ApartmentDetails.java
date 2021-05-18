package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;


public class ApartmentDetails extends AppCompatActivity {
    private ImageSwitcher sw;
    private int currentImage;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apartment_details);
        sw = findViewById(R.id.imageSwitcher);
        sw.setFactory(() -> {
            ImageView myView = new ImageView(ApartmentDetails.this);
            return myView;
        });
        sw.setImageResource(R.drawable.landlord);
        currentImage = R.drawable.landlord;
        sw.setOnTouchListener(new OnSwipeTouchListener(ApartmentDetails.this) {
            @Override
            public void onSwipeLeft() {
                changeImage(sw);
            }

            @Override
            public void onSwipeRight() {
                changeImage(sw);
            }
        });
    }

    public void changeImage(View view) {
        if (currentImage == R.drawable.landlord) {
            sw.setImageResource(R.drawable.tenant);
            currentImage = R.drawable.tenant;
        } else {
            sw.setImageResource(R.drawable.landlord);
            currentImage = R.drawable.landlord;
        }
    }
}
