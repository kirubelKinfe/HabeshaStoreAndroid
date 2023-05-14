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
import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.Models.UserModel;
import com.example.splashlogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsActivity extends AppCompatActivity implements ProductsListInterface{

    private ProductsListAdapter adapter;
    private RecyclerView recyclerView;
    private List<ProductsModel> productsList;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recyclerView);

        productsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        firebaseStorage = FirebaseStorage.getInstance();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("Products").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot product : list) {
                                ProductsModel productsModel = new ProductsModel(
                                        product.get("productName").toString(),
                                        Integer.parseInt(product.get("productPrice").toString()),
                                        product.get("img").toString(),
                                        Integer.parseInt(product.get("quantity").toString()),
                                        product.get("category").toString(),
                                        product.get("type").toString(),
                                        product.get("storageKey").toString());
                                productsModel.setmKey(product.getId());
                                productsList.add(productsModel);
                        }
                        adapter = new ProductsListAdapter(ProductsActivity.this, productsList, ProductsActivity.this);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ProductsActivity.this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> System.out.println(e.getMessage()));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ProductsActivity.this, ProductUpdateActivity.class);

        intent.putExtra("name", productsList.get(position).getProductName());
        intent.putExtra("price", productsList.get(position).getProductPrice());
        intent.putExtra("quantity", productsList.get(position).getQuantity());
        intent.putExtra("category", productsList.get(position).getCategory());
        intent.putExtra("type", productsList.get(position).getType());
        intent.putExtra("imageUrl", productsList.get(position).getImg());
        intent.putExtra("key", productsList.get(position).getmKey());
        intent.putExtra("storageKey", productsList.get(position).getStorageKey());
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        Intent intent = new Intent(ProductsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}