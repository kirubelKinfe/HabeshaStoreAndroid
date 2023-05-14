package com.example.splashlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.splashlogin.R;

public class SuccessActivity extends AppCompatActivity {

    Button continueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        continueShopping = findViewById(R.id.continueShopping);

        continueShopping.setOnClickListener(view -> {
            startActivity(new Intent(SuccessActivity.this, HomeActivity.class));
        });
    }
}