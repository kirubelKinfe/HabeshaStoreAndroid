package com.example.splashlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashlogin.Models.CartDB;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String img = extras.getString("img");
        String title = extras.getString("title");
        int price =  extras.getInt("price",0);

        ImageView details_img = findViewById(R.id.details_img);
        TextView details_title = findViewById(R.id.details_title);
        TextView details_price = findViewById(R.id.details_price);
        TextView details_description = findViewById(R.id.details_description);

        Button addBtn= findViewById(R.id.addToCart);
        Button buyBtn= findViewById(R.id.buyNow);
        ImageView cartBtn= findViewById(R.id.back);
        ImageView backBtn= findViewById(R.id.cart);

        Picasso.get()
                .load(img)
                .fit()
                .centerCrop()
                .into(details_img);
        details_title.setText(title);
        details_price.setText("$" + price);
//        details_description.setText(description);

        addBtn.setOnClickListener(view -> {
            CartModel cartModel = new CartModel(
                    id,
                    title,
                    price,
                    img,
                    1,
                    price);
            CartDB cartDB = new CartDB(this);
            boolean status = cartDB.addCartItem(cartModel);
            if(status) {
                Toast.makeText(this, cartModel.getProductName() + " added to cart" , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item already in cart" , Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(view -> finish());

        cartBtn.setOnClickListener(view -> {
            finish();
        });
    }
}