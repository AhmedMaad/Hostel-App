package com.example.hostel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editText;
    private int[] backgrounds = {R.drawable.bg1
            , R.drawable.bg2
            , R.drawable.bg3
            , R.drawable.bg4};
    private ImageView profileIV;
    private Uri imageUri;
    private FirebaseFirestore db;
    private UserModel user;
    private EditText nameET;
    private EditText genderET;
    private EditText numberET;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Random random = new Random();
        int chosenBG = random.nextInt(4);
        ImageView backgroundIV = findViewById(R.id.header_cover_image);
        backgroundIV.setImageResource(backgrounds[chosenBG]);

        profileIV = findViewById(R.id.user_profile_photo);
        nameET = findViewById(R.id.profileName);
        genderET = findViewById(R.id.profileGender);
        numberET = findViewById(R.id.profileNumber);
        progressBar = findViewById(R.id.progress_circular);

        db = FirebaseFirestore.getInstance();
        db
                .collection(Constants.USERS)
                .document(Constants.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(UserModel.class);
                        if (!user.getName().isEmpty())
                            nameET.setText(user.getName());
                        if (user.getGender() != null)
                            genderET.setText(user.getGender());
                        if (!user.getPhoneNumber().isEmpty())
                            numberET.setText(user.getPhoneNumber());
                        if (user.getProfilePicture() != null)
                            Picasso
                                    .with(ProfileActivity.this)
                                    .load(user.getProfilePicture())
                                    .into(profileIV);
                    }
                });
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
            editText = findViewById(R.id.profileNumber);
            editText.setEnabled(true);
            profileIV.setEnabled(true);
        } else if (v.getId() == R.id.done) {
            progressBar.setVisibility(View.VISIBLE);
            button = findViewById(R.id.done);
            button.setVisibility(View.GONE);
            //Update profile to firebase
            if (imageUri != null)
                uploadImage();
            else
                uploadProfile(imageUri); //Image Uri in this case is null
        }
    }

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
            profileIV.setImageURI(imageUri);
        }
    }

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
                    getLinkForUploadedImage(storageRef.getDownloadUrl());
                });
    }

    //Getting a download link after uploading a picture
    private void getLinkForUploadedImage(Task<Uri> task) {
        task.addOnSuccessListener(uri -> {
            uploadProfile(uri);
        });
    }

    private void uploadProfile(Uri imageUri) {
        String writtenName = nameET.getText().toString();
        String writtenGender = genderET.getText().toString();
        String writtenNumber = numberET.getText().toString();

        String imageLink;
        if (imageUri != null)
            imageLink = imageUri.toString();
        else
            imageLink = user.getProfilePicture();

        user = new UserModel(writtenName, user.getEmail(), writtenNumber
                , writtenGender, imageLink);

        db
                .collection(Constants.USERS)
                .document(Constants.USER_ID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, R.string.profile_updated
                                , Toast.LENGTH_SHORT).show();
                        disableViews();
                    }
                });
    }

    private void disableViews(){
        progressBar.setVisibility(View.GONE);
        button = findViewById(R.id.edit);
        button.setVisibility(View.VISIBLE);
        button = findViewById(R.id.myAds);
        button.setVisibility(View.VISIBLE);
/*        button = findViewById(R.id.done);
        button.setVisibility(View.GONE);*/
        editText = findViewById(R.id.profileName);
        editText.setEnabled(false);
        editText = findViewById(R.id.profileGender);
        editText.setEnabled(false);
        editText = findViewById(R.id.profileNumber);
        editText.setEnabled(false);
        profileIV.setEnabled(false);
    }

    public void openAdsActivity(View view) {
        Intent i = new Intent(this, AdsActivity.class);
        startActivity(i);
    }
}