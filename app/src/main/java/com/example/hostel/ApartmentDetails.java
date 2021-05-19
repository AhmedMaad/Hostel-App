package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


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

        TextView addressTV = findViewById(R.id.apartmentDetAddressCon);
        TextView priceTV = findViewById(R.id.apartmentDetPriceCon);
        TextView specificationsTV = findViewById(R.id.apartmentDetSpecificationsCon);
        TextView nameTV = findViewById(R.id.apartmentDetNameCon);
        TextView phoneNoTV = findViewById(R.id.apartmentDetPhoneCon);

        String address = getIntent().getStringExtra(Constants.ADDRESS);
        addressTV.setText(address);

        double price = getIntent().getDoubleExtra(Constants.PRICE, -1);
        priceTV.setText(String.valueOf(price));

        String specifications = getIntent().getStringExtra(Constants.SPECIFICATIONS);
        specificationsTV.setText(specifications);

        String name = getIntent().getStringExtra(Constants.NAME);
        nameTV.setText(name);

        String phoneNo = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        phoneNoTV.setText(phoneNo);

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

    public void callMobile(View view) {
        String phoneNo = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        Uri phoneUri = Uri.parse("tel:" + phoneNo);
        Intent i = new Intent(Intent.ACTION_DIAL, phoneUri);
        startActivity(i);
    }

}