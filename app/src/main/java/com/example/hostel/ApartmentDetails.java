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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class ApartmentDetails extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apartment_details);

        TextView addressTV = findViewById(R.id.apartmentDetAddressCon);
        TextView priceTV = findViewById(R.id.apartmentDetPriceCon);
        TextView specificationsTV = findViewById(R.id.apartmentDetSpecificationsCon);
        TextView nameTV = findViewById(R.id.apartmentDetNameCon);
        TextView phoneNoTV = findViewById(R.id.apartmentDetPhoneCon);
        ImageView imageView = findViewById(R.id.imageView);

        String address = getIntent().getStringExtra(Constants.ADDRESS);
        addressTV.setText("Address: " + address);

        double price = getIntent().getDoubleExtra(Constants.PRICE, -1);
        priceTV.setText("Price: " + price);

        String specifications = getIntent().getStringExtra(Constants.SPECIFICATIONS);
        specificationsTV.setText("Specifications: " + specifications);

        String name = getIntent().getStringExtra(Constants.NAME);
        nameTV.setText("Name: " + name);

        String phoneNo = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        phoneNoTV.setText("Mobile: " + phoneNo);

        String picture = getIntent().getStringExtra(Constants.PICTURE);
        Picasso.with(this).load(picture).into(imageView);

    }

    public void callMobile(View view) {
        String phoneNo = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        Uri phoneUri = Uri.parse("tel:" + phoneNo);
        Intent i = new Intent(Intent.ACTION_DIAL, phoneUri);
        startActivity(i);
    }

}