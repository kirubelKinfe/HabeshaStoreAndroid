package com.example.splashlogin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashlogin.Activities.OrdersSummaryAdapter;
import com.example.splashlogin.Models.CartDB;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.Models.OrdersModel;
import com.example.splashlogin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private OrdersModel ordersModel;

    TextView orderId, orderUser, orderEmail, orderPhoneNum, orderCity, orderStreet, orderStatus, totalTV;
    Button orderDelivered, orderCanceled;
    RecyclerView ordersRecyclerView;
    private OrdersSummaryAdapter adapter;
    private List<CartModel> ordersList;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderId = findViewById(R.id.orderID);
        orderUser = findViewById(R.id.orderUserTextView);
        orderEmail = findViewById(R.id.orderEmailTextView);
        orderPhoneNum = findViewById(R.id.orderPhoneNumTextView);
        orderCity = findViewById(R.id.orderCityTextView);
        orderStreet = findViewById(R.id.orderStreetTextView);
        orderStatus = findViewById(R.id.orderStatusTextView);
        totalTV = findViewById(R.id.totalTV);

        orderDelivered = findViewById(R.id.orderDelivered);
        orderCanceled = findViewById(R.id.orderCanceled);

        ordersRecyclerView = findViewById(R.id.ordersSummaryRV);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        OrderDetailsActivity orderDetailsActivity = this;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if(Objects.equals(key, postSnapshot.getKey())) {
                        ordersModel = postSnapshot.getValue(OrdersModel.class);
                        ordersModel.setmKey(postSnapshot.getKey());
                        orderId.setText("#" + ordersModel.getmKey());
                        orderUser.setText(ordersModel.getFullName());
                        orderEmail.setText(ordersModel.getUserEmail());
                        orderPhoneNum.setText(ordersModel.getPhoneNum());
                        orderCity.setText(ordersModel.getCity());
                        orderStreet.setText(ordersModel.getStreet());
                        orderStatus.setText(ordersModel.getStatus());
                        totalTV.setText("$" + ordersModel.getTotalPrice());
                        ordersList = new ArrayList<>();
                        adapter = new OrdersSummaryAdapter(orderDetailsActivity,ordersList);
                        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

                        ordersRecyclerView.setLayoutManager(layoutManager);
                        ordersRecyclerView.setAdapter(adapter);

                        ordersList.addAll(ordersModel.getOrders());
                        if(Objects.equals(ordersModel.getStatus(), "delivered")) {
                            orderDelivered.setVisibility(View.INVISIBLE);
                            orderCanceled.setVisibility(View.INVISIBLE);
                        }
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        orderDelivered.setOnClickListener(view -> {
            ordersModel.setStatus("delivered");
            databaseReference.child(key).setValue(ordersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(OrderDetailsActivity.this, OrdersActivity.class));
                }
            }).addOnFailureListener(e -> Toast.makeText(OrderDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        orderCanceled.setOnClickListener(view -> {
            ordersModel.setStatus("canceled");
            databaseReference.child(key).setValue(ordersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(OrderDetailsActivity.this, OrdersActivity.class));
                }
            }).addOnFailureListener(e -> Toast.makeText(OrderDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}