package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.splashlogin.R;
import com.example.splashlogin.Models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUserActivity extends AppCompatActivity {

    TextInputEditText adminEmail, adminPassword, adminPrivilege;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        adminPrivilege = findViewById(R.id.adminPrivilege);
    }

    public void addUser(View view) {
        progressDialog.show();
        String Email = adminEmail.getText().toString();
        String Password = adminPassword.getText().toString();
        String Privilege = adminPrivilege.getText().toString();

        progressDialog.show();
        if (Email != null && Password != null && Privilege != null) {
            firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnSuccessListener(authResult -> {

                        firebaseFirestore.collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new UserModel(adminEmail.getText().toString(), adminPassword.getText().toString(), adminPrivilege.getText().toString()));

                        progressDialog.cancel();
                        Intent intent = new Intent(AddUserActivity.this, UsersActivity.class);
                        startActivity(intent);

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    });
        } else {
            Toast.makeText(this, "Please Fill The Required Fields", Toast.LENGTH_SHORT).show();
        }
    }
}