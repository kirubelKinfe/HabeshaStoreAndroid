package com.example.splashlogin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.Models.OrdersModel;
import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersActivity extends AppCompatActivity implements OrdersListInterface{

    private OrdersListAdapter adapter;
    private RecyclerView ordersRecyclerview;
    private List<OrdersModel> ordersList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ordersRecyclerview = findViewById(R.id.ordersRecyclerview);

        ordersList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot postSnapshot : snapshot.getChildren()) {
                    OrdersModel ordersModel = postSnapshot.getValue(OrdersModel.class);
                    ordersModel.setmKey(postSnapshot.getKey());
                    if(Objects.equals(ordersModel.getStatus(), "placed")) {
                        ordersList.add(ordersModel);
                    }
                }
                adapter = new OrdersListAdapter(OrdersActivity.this, ordersList, OrdersActivity.this);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrdersActivity.this, 1);
                ordersRecyclerview.setLayoutManager(layoutManager);
                ordersRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrdersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);

        intent.putExtra("key", ordersList.get(position).getmKey());
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        Intent intent = new Intent(OrdersActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}