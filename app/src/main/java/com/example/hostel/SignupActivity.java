package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_circular);
    }

    public void openLoginActivity(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void signupNewUser(View view) {
        EditText emailET = findViewById(R.id.signUpEmail);
        EditText passwordET = findViewById(R.id.signUpPassword);

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.missing_fields, Toast.LENGTH_SHORT).show();
            emailET.setError(getString(R.string.email_required));
            passwordET.setError(getString(R.string.password_required));
        } else {
            progressBar.setVisibility(View.VISIBLE);
            addNewUserToAuth(email, password);
        }
    }

    //Adding new user in firebase authentication
    private void addNewUserToAuth(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Constants.USER_ID = task.getResult().getUser().getUid();
                            //Create new user in firestore
                            createUserInFirestore(email, Constants.USER_ID);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, "Creating user Failed", Toast.LENGTH_SHORT).show();
                            Log.d("trace", "Error: " + task.getException());
                        }
                    }
                });
    }

    private void createUserInFirestore(String email, String userId) {
        EditText phoneNoET = findViewById(R.id.signUpPhone);
        EditText nameET = findViewById(R.id.signUpName);
        RadioGroup rg = findViewById(R.id.radioGroup);
        String name = nameET.getText().toString();
        String phoneNo = phoneNoET.getText().toString();
        RadioButton radioButton = findViewById(rg.getCheckedRadioButtonId());
        String gender = radioButton.getText().toString();

        UserModel user = new UserModel(name, email, phoneNo, gender);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(documentReference -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    //Updating id to make delete and update functionality later

                    //documentReference.update("id", documentReference.getId());
                    //Constants.USER_ID = documentReference.getId();

                    Toast.makeText(SignupActivity.this, R.string.user_added, Toast.LENGTH_SHORT).show();
                    //navigate to main activity
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                });
    }

}