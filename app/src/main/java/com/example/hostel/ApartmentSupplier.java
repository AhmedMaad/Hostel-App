package com.example.hostel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import static com.example.hostel.Constants.USER_ID;

public class ApartmentSupplier extends AppCompatActivity {

    private Uri imageUri;
    private ImageButton productIB;
    private Button doneBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apartment_details_supplier);
        productIB = findViewById(R.id.addPhoto);
        doneBtn = findViewById(R.id.btn_done);
        progressBar = findViewById(R.id.progress_circular);
    }

    //TODO: add to firebase with (apartment or room) they are sent from the prev activity
    public void choosePicture(View view) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
            productIB.setImageURI(imageUri);
        }
    }

    public void addProduct(View view) {
        doneBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        uploadImage();
    }

    //Upload product image to firebase
    private void uploadImage() {
        //Accessing Cloud Storage bucket by creating an instance of FirebaseStorage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Create a reference to upload, download, or delete a file

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String imageName = "image: " + day + '-' + month + '-' + year + ' ' + hour + ':' + minute
                + ':' + second + '.' + millis;

        StorageReference storageRef = storage.getReference().child(imageName);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.d("trace", "Image uploaded");
                    getLinkForUploadedImage(storageRef.getDownloadUrl());
                });
    }

    //Getting a download link after uploading a picture
    private void getLinkForUploadedImage(Task<Uri> task) {
        Log.d("trace", "Getting image download link");
        task.addOnSuccessListener(uri -> {
            Log.d("trace", "Image Link: " + uri);
            uploadProduct(uri);
        });
    }

    private void uploadProduct(Uri imageUri) {
        Log.d("trace", "Uploading product...");
        EditText addressET = findViewById(R.id.apartmentDetAddressSup);
        EditText priceET = findViewById(R.id.apartmentDetPriceSup);
        EditText specificationsET = findViewById(R.id.apartmentDetSepcificationsSup);
        EditText nameET = findViewById(R.id.apartmentDetNameSup);
        EditText phoneET = findViewById(R.id.apartmentDetPhoneSup);

        String address = addressET.getText().toString();
        String price = priceET.getText().toString();
        String specifications = specificationsET.getText().toString();
        String name = nameET.getText().toString();
        String phone = phoneET.getText().toString();

        //Retrieving product type from main activity
        String productType = getIntent().getStringExtra(Constants.PRODUCT);

        ProductModel product =
                new ProductModel(imageUri.toString(), address, Double.parseDouble(price)
                        , specifications, name, phone, productType, USER_ID);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection(Constants.PRODUCTS)
                .add(product)
                .addOnSuccessListener(documentReference -> {
                    documentReference.update("id", documentReference.getId());
                    Toast.makeText(this, R.string.product_added, Toast.LENGTH_SHORT).show();
                    finish();
                });

    }

}