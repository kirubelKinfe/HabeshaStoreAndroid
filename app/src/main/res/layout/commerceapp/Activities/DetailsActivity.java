package com.example.commerceapp.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.commerceapp.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int img = getIntent().getIntExtra("img", 0);
        String title = getIntent().getStringExtra("title");
        String price = "$" + getIntent().getIntExtra("price",0);
        String description = getIntent().getStringExtra("description");

        ImageView imageView = findViewById(R.id.details_img);
        TextView details_title = findViewById(R.id.details_title);
        TextView details_price = findViewById(R.id.details_price);
        TextView details_description = findViewById(R.id.details_description);

        ConstraintLayout addBtn= findViewById(R.id.addToCartBtn);
        ConstraintLayout buyBtn= findViewById(R.id.buyNowBtn);
        Button cartBtn= findViewById(R.id.cartBtn);
        Button backBtn= findViewById(R.id.backBtn);

        addBtn.setOnClickListener(view -> MainActivity.cartNumber += 1);

        backBtn.setOnClickListener(view -> finish());

        cartBtn.setOnClickListener(view -> {
            finish();
        });


        imageView.setImageResource(img);
        details_title.setText(title);
        details_price.setText(price);
        details_description.setText(description);
    }
}