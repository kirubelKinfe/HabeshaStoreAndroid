package com.example.splashlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.splashlogin.Admin.UsersActivity;
import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {

    TextInputEditText userProfileUsername, userProfileFullName, userProfileEmail, userProfilePhoneNum, userProfilePassword;
    Button updateProfile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userProfileFullName = findViewById(R.id.userProfileFullName);
        userProfileUsername = findViewById(R.id.userProfileUsername);
        userProfileEmail = findViewById(R.id.userProfileEmail);
        userProfilePhoneNum = findViewById(R.id.userProfilePhoneNo);
        userProfilePassword = findViewById(R.id.userProfilePassword);
        updateProfile = findViewById(R.id.updateProfileBtn);

        userProfileEmail.setEnabled(false);

        userProfileUsername.setText(LoginActivity.currentUserUserName);
        userProfileEmail.setText(LoginActivity.currentUserEmail);
        userProfileFullName.setText(LoginActivity.currentUserFullName);
        userProfilePhoneNum.setText(LoginActivity.currentUserPhoneNum);
        userProfilePassword.setText(LoginActivity.currentUserPassword);

        updateProfile.setOnClickListener(view -> {
            String fullName = userProfileFullName.getText().toString().trim();
            String username = userProfileUsername.getText().toString().trim();
            String email = userProfileEmail.getText().toString().trim();
            String phoneNum = userProfilePhoneNum.getText().toString().trim();
            String password = userProfilePassword.getText().toString().trim();

            Map<String, Object> userDetail = new HashMap<>();
            userDetail.put("fullname", fullName);
            userDetail.put("username", username);
            userDetail.put("email", email);
            userDetail.put("phoneNumber", phoneNum);
            userDetail.put("password", password);

            firebaseFirestore.collection("Users")
                    .whereEqualTo("email", email)
                    .get().addOnCompleteListener(task -> {
                        if(task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            firebaseFirestore.collection("Users")
                                    .document(documentID)
                                    .update(userDetail)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(ProfileUpdateActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfileUpdateActivity.this, UsersActivity.class));
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(ProfileUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
        });
    }
}