package com.example.splashlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.Models.CartDB;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.Models.OrdersModel;
import com.example.splashlogin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    TextInputEditText city, street, phoneNum;
    TextView userName, email, total;
    Button placeOrder;

    private OrdersSummaryAdapter adapter;
    private RecyclerView recyclerView;
    private List<CartModel> ordersSummaryList;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;

    int totalPurchasePrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Orders");
        firebaseFirestore = FirebaseFirestore.getInstance();

        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        phoneNum = findViewById(R.id.phoneNum);
        userName = findViewById(R.id.orderUserTextView);
        email = findViewById(R.id.orderEmailTextView);
        total = findViewById(R.id.totalTV);
        placeOrder = findViewById(R.id.placeOrder);
        recyclerView = findViewById(R.id.ordersSummaryRV);



        ordersSummaryList = new ArrayList<>();
        adapter = new OrdersSummaryAdapter(this,ordersSummaryList);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        CartDB cartDB = new CartDB(this);
        List<CartModel> cartItems = cartDB.getAllCartItems();
        ordersSummaryList.addAll(cartItems);

        for (CartModel cartItem : cartItems) {
            totalPurchasePrice += cartItem.getTotalPrice();
        }

        userName.setText(LoginActivity.currentUserFullName);
        email.setText(LoginActivity.currentUserEmail);
        total.setText("$" + totalPurchasePrice);


        placeOrder.setOnClickListener(view -> {
            if(phoneNum.getText().toString() != null && city.getText().toString() != null && street.getText().toString() != null) {
                OrdersModel ordersModel = new OrdersModel(
                        phoneNum.getText().toString().trim(),
                        LoginActivity.currentUserFullName,
                        LoginActivity.currentUserEmail,
                        city.getText().toString().trim(),
                        street.getText().toString().trim(),
                        cartItems,
                        totalPurchasePrice,
                        "placed"
                );
                databaseReference.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(ordersModel);
                firebaseFirestore.collection("Orders")
                        .add(ordersModel);
                Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
                cartDB.removeAllCartItems();
                startActivity(new Intent(CheckoutActivity.this, SuccessActivity.class));
            } else {
                Toast.makeText(this, "Please Fill The Required Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}