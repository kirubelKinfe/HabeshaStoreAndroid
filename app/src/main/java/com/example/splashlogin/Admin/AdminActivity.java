package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.R;

public class AdminActivity extends AppCompatActivity {

    ImageView users, products, orders, deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        users = findViewById(R.id.users);
        products = findViewById(R.id.products);
        orders = findViewById(R.id.orders);
        deliveries = findViewById(R.id.deliveries);

        products.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        });

        users.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, UsersActivity.class);
            startActivity(intent);
        });

        orders.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, OrdersActivity.class);
            startActivity(intent);
        });

        deliveries.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, OrdersHistoryActivity.class);
            startActivity(intent);
        });
    }

    public void onLogoutClick(View view) {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}