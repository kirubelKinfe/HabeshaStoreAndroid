package com.example.splashlogin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Activities.DetailsActivity;
import com.example.splashlogin.Admin.ProductsActivity;
import com.example.splashlogin.Admin.ProductsListAdapter;
import com.example.splashlogin.Models.CartDB;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.Models.CategoryModel;
import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<ProductsModel> productsList;

    private RecyclerView categoryRecyclerview;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoriesList;

    private DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore;

    TextView categoryTextView;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        categoryRecyclerview = rootView.findViewById(R.id.categoryRecyclerView);
        categoryTextView = rootView.findViewById(R.id.textView2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        firebaseFirestore = FirebaseFirestore.getInstance();

        productsList = new ArrayList<>();
        categoriesList = new ArrayList<>();
        CategoryModel categoryModel1 = new CategoryModel("All");
        CategoryModel categoryModel2 = new CategoryModel("Men");
        CategoryModel categoryModel3 = new CategoryModel("Women");
        CategoryModel categoryModel4 = new CategoryModel("Children");
        categoriesList.add(categoryModel1);
        categoriesList.add(categoryModel2);
        categoriesList.add(categoryModel3);
        categoriesList.add(categoryModel4);
        final String[] params = {"All"};
        categoryAdapter = new CategoryAdapter(getActivity(), categoriesList, (position) -> {
            params[0] = categoriesList.get(position).getName();
            categoryTextView.setText(params[0] + " Products");

            firebaseFirestore.collection("Products").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            productsList.clear();
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

                                if(Objects.equals(params[0], "All")) {
                                    productsList.add(productsModel);
                                } else if (Objects.equals(productsModel.getCategory(), params[0])) {
                                    productsList.add(productsModel);
                                }
                            }
                            adapter = new RecyclerViewAdapter(getActivity(), productsList, (pos) -> {
                                Intent intent = new Intent(getContext(), DetailsActivity.class);

                                intent.putExtra("id", productsList.get(pos).getmKey());
                                intent.putExtra("img", productsList.get(pos).getImg());
                                intent.putExtra("title", productsList.get(pos).getProductName());
                                intent.putExtra("price", productsList.get(pos).getProductPrice());
                                startActivity(intent);
                            });

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(e -> System.out.println(e.getMessage()));

        });

        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        categoryRecyclerview.setLayoutManager(categoryLayoutManager);
        categoryRecyclerview.setAdapter(categoryAdapter);

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
                        adapter = new RecyclerViewAdapter(getActivity(), productsList, (position) -> {
                            Intent intent = new Intent(getContext(), DetailsActivity.class);

                            intent.putExtra("id", productsList.get(position).getmKey());
                            intent.putExtra("img", productsList.get(position).getImg());
                            intent.putExtra("title", productsList.get(position).getProductName());
                            intent.putExtra("price", productsList.get(position).getProductPrice());
                            startActivity(intent);
                        });

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> System.out.println(e.getMessage()));

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for( DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    ProductsModel productsModel = postSnapshot.getValue(ProductsModel.class);
//                    productsModel.setmKey(postSnapshot.getKey());
//
//                    productsList.add(productsModel);
//
//                }
//                adapter = new RecyclerViewAdapter(getActivity(), productsList, (position) -> {
//                    Intent intent = new Intent(getContext(), DetailsActivity.class);
//
//                    intent.putExtra("id", productsList.get(position).getmKey());
//                    intent.putExtra("img", productsList.get(position).getImg());
//                    intent.putExtra("title", productsList.get(position).getProductName());
//                    intent.putExtra("price", productsList.get(position).getProductPrice());
//                    startActivity(intent);
//                });
//
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        return rootView;
    }
}