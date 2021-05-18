package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_circular);
    }

    //TODO: add reuired field when ETs are empty
    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        EditText emailET = findViewById(R.id.logInEmail);
        EditText passwordET = findViewById(R.id.logInPassword);
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.missing_fields, Toast.LENGTH_SHORT).show();
            emailET.setError(getString(R.string.email_required));
            passwordET.setError(getString(R.string.password_required));
        } else
            signInUsingFirebase(email, password);

    }

    private void signInUsingFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            //navigate to main activity
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else
                            Log.d("trace", "Login Failed: " + task.getException());
                    }
                });
    }

    public void openSignupActivity(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
        finish();
    }
}