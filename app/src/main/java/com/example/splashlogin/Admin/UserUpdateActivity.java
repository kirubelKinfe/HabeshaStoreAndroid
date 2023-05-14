package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.splashlogin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateActivity extends AppCompatActivity {

    TextInputEditText Email, Password, Privilege;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String email;
    String password;
    String privilege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        privilege = getIntent().getStringExtra("privilege");


        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Privilege = findViewById(R.id.Privilege);


        Email.setText(email);
        Password.setText(password);
        Privilege.setText(privilege);

    }


    public void updateUser(View view) {
        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("password", password);
        userDetail.put("privilege", privilege);

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
                                    Toast.makeText(UserUpdateActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UserUpdateActivity.this, UsersActivity.class));
                                })
                                .addOnFailureListener(e -> Toast.makeText(UserUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
    }
}