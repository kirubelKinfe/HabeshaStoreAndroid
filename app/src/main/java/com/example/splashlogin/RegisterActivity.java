package com.example.splashlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashlogin.Models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText username, password, confirmPassword, fullname, phoneNo, email;
    Button callSignIn, signUp_btn;
    ImageView image;
    TextView logoText, sloganText;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        callSignIn = findViewById(R.id.callSignIn);
        signUp_btn = findViewById(R.id.check_outBtn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);

        username = findViewById(R.id.username);
        password = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.userConfirmPassword);
        fullname = findViewById(R.id.fullName);
        email = findViewById(R.id.userEmail);
        phoneNo = findViewById(R.id.phoneNo);

        callSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<View, String>(image, "logo_image");
            pairs[1] = new Pair<View, String>(logoText, "logo_text");
            pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
            pairs[3] = new Pair<View, String>(username, "username_tran");
            pairs[4] = new Pair<View, String>(password, "password_tran");
            pairs[4] = new Pair<View, String>(confirmPassword, "password_tran");
            pairs[5] = new Pair<View, String>(signUp_btn, "button_tran");
            pairs[6] = new Pair<View, String>(callSignIn, "login_signup_tran");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
            startActivity(intent, options.toBundle());
        });
    }

    public void registerUser(View view) {
        String FullName = fullname.getText().toString().trim();
        String UserName = username.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String PhoneNo = phoneNo.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ConfirmPassword = confirmPassword.getText().toString().trim();
        String Privilege = "user";

        if(FullName.equals("")) {
            fullname.setError("FullName Field Required");
        }
        if(UserName.equals("")) {
            username.setError("Username Field Required");
        }
        if(Email.equals("")) {
            email.setError("Email Field Required");
        }
        if(PhoneNo.equals("")) {
            phoneNo.setError("PhoneNo Field Required");
        }
        if(Password.equals("")) {
            password.setError("Password Field Required");
        }
        if(ConfirmPassword.equals("")) {
            confirmPassword.setError("Confirm Password Field Required");
        }

        if (!Password.equals(ConfirmPassword)) {
            confirmPassword.setError("Password Doesn't Match!");
        }
        else if (!FullName.equals("") && !UserName.equals("") &&
                !Email.equals("") && !PhoneNo.equals("") && !Password.equals("")) {
            firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnSuccessListener(authResult -> {

                        firebaseFirestore.collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new UserModel(FullName, UserName, Email, Integer.parseInt(PhoneNo), Password, Privilege));

                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                        Pair[] pairs = new Pair[7];
                        pairs[0] = new Pair<View, String>(image, "logo_image");
                        pairs[1] = new Pair<View, String>(logoText, "logo_text");
                        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                        pairs[3] = new Pair<View, String>(username, "username_tran");
                        pairs[4] = new Pair<View, String>(password, "password_tran");
                        pairs[5] = new Pair<View, String>(signUp_btn, "button_tran");
                        pairs[6] = new Pair<View, String>(callSignIn, "login_signup_tran");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                        startActivity(intent, options.toBundle());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}