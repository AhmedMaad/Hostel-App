package com.example.hostel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void changeEmail(View view) {

        LayoutInflater inflater = getLayoutInflater();
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.email_alert_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setView(v)
                .setPositiveButton(R.string.update, (dialog, which) -> {
                    EditText emailET = v.findViewById(R.id.et_email);
                    String updatedEmail = emailET.getText().toString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateEmail(updatedEmail)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful())
                                    changeEmailInFirestore(updatedEmail);
                                else
                                    Toast.makeText(this, R.string.email_update_error, Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void changeEmailInFirestore(String email) {

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection(Constants.USERS)
                .document(Constants.USER_ID)
                .update("email", email)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, R.string.email_updated, Toast.LENGTH_SHORT).show();
                });

    }

    public void changePassword(View view) {

        LayoutInflater inflater = getLayoutInflater();
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.password_alert_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setView(v)
                .setPositiveButton(R.string.update, (dialog, which) -> {
                    EditText passwordET = v.findViewById(R.id.et_password);
                    String updatedPassword = passwordET.getText().toString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(updatedPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful())
                                    Toast.makeText(this, R.string.password_updated, Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    public void deleteAccount(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Delete Account!")
                .setMessage("Are you sure you want to delete your account, this cannot be undone?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("delete", (dialog, which) -> {
                    Toast.makeText(SettingsActivity.this, "Deleting Account", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.delete()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this, R.string.account_deleted, Toast.LENGTH_SHORT).show();
                                    Intent i =
                                            new Intent(SettingsActivity.this
                                                    , LoginActivity.class);
                                    startActivity(i);
                                    finishAffinity();
                                }

                            });
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();

    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("ok", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent i =
                            new Intent(SettingsActivity.this
                                    , LoginActivity.class);
                    startActivity(i);
                    finishAffinity();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}