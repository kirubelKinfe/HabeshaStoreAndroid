package com.example.splashlogin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.splashlogin.Activities.DetailsActivity;
import com.example.splashlogin.Admin.ProductsActivity;
import com.example.splashlogin.Admin.ProductsListAdapter;
import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.Models.FavoritesDB;
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


public class FavoritesFragment extends Fragment implements FavoritesListInterface{

    private RecyclerView favoritesRecyclerView;
    private FavoritesListAdapter adapter;
    private List<ProductsModel> favoritesList;
    private DatabaseReference databaseReference;
    private FavoritesDB favoritesDB;
    FavoritesListInterface favoritesListInterface;
    FirebaseFirestore firebaseFirestore;


    public FavoritesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesRecyclerView = rootView.findViewById(R.id.favoritesRecyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();


        favoritesList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        favoritesDB = new FavoritesDB(getContext());

        favoritesListInterface = this;

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
                            if(favoritesDB.isFavorites(product.getId())) {
                                favoritesList.add(productsModel);
                            }
                        }
                        adapter = new FavoritesListAdapter(getActivity(), favoritesList, favoritesListInterface);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                        favoritesRecyclerView.setLayoutManager(layoutManager);
                        favoritesRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> System.out.println(e.getMessage()));

        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);

        intent.putExtra("id", favoritesList.get(position).getmKey());
        intent.putExtra("img", favoritesList.get(position).getImg());
        intent.putExtra("title", favoritesList.get(position).getProductName());
        intent.putExtra("price", favoritesList.get(position).getProductPrice());
        startActivity(intent);
    }

    @Override
    public void onFavIconClick(int position) {
        if(favoritesDB.isFavorites(favoritesList.get(position).getmKey())) {
            favoritesDB.removeFromFavorites(favoritesList.get(position).getmKey());
            favoritesList.clear();

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
                                if(favoritesDB.isFavorites(product.getId())) {
                                    favoritesList.add(productsModel);
                                }
                            }
                            adapter = new FavoritesListAdapter(getActivity(), favoritesList, favoritesListInterface);

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                            favoritesRecyclerView.setLayoutManager(layoutManager);
                            favoritesRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(e -> System.out.println(e.getMessage()));
        }
    }
}