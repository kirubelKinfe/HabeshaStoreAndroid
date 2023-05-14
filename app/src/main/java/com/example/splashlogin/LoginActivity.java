package com.example.splashlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashlogin.Activities.HomeActivity;
import com.example.splashlogin.Admin.AdminActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputEditText userEmail, userPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    public static String currentUserFullName = "";
    public static String currentUserUserName = "";
    public static String currentUserEmail = "";
    public static String currentUserPhoneNum = "";
    public static String currentUserPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        callSignUp = findViewById(R.id.callSignUp);
        login_btn = findViewById(R.id.login_btn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);



        login_btn.setOnClickListener(view -> {
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if(email.equals("")) {
                userEmail.setError("Email Field Required");
            }
            if(password.equals("")) {
                userPassword.setError("Password Field Required");
            }

            if (!email.equals("") && !password.equals("")) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult ->
                                firebaseFirestore.collection("Users").get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    if(!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                        for(DocumentSnapshot document : list) {
                                            if(Objects.requireNonNull(document.get("email")).toString().equals(userEmail.getText().toString())) {
                                                if(Objects.requireNonNull(document.get("privilege")).toString().equals("user")) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                    currentUserFullName = document.get("fullName").toString();
                                                    currentUserUserName = document.get("username").toString();
                                                    currentUserEmail = document.get("email").toString();
                                                    currentUserPhoneNum = document.get("phoneNumber").toString();
                                                    currentUserPassword = document.get("password").toString();

                                                    startActivity(intent);
                                                    break;
                                                } else if(Objects.requireNonNull(document.get("privilege")).toString().equals("admin")) {
                                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }))
                        .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }

        });

        callSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<View, String>(image, "logo_image");
            pairs[1] = new Pair<View, String>(logoText, "logo_text");
            pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
            pairs[3] = new Pair<View, String>(userEmail, "username_tran");
            pairs[4] = new Pair<View, String>(userPassword, "password_tran");
            pairs[5] = new Pair<View, String>(login_btn, "button_tran");
            pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        });
    }
}