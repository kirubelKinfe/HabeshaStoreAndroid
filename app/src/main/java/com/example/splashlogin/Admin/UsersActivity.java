package com.example.splashlogin.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.R;
import com.example.splashlogin.Models.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersActivity extends AppCompatActivity implements UsersListInterface {

    private UsersListAdapter adapter;
    private RecyclerView recyclerView;
    private List<UserModel> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        recyclerView = findViewById(R.id.usersRecyclerView);

        usersList = new ArrayList<UserModel>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("Users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot user : list) {
                            if (Objects.requireNonNull(user.get("privilege")).toString().equals("admin")) {
                                UserModel userModel = new UserModel(user.get("email").toString(),
                                       user.get("password").toString(), user.get("privilege").toString());
                                usersList.add(userModel);
                                adapter = new UsersListAdapter(UsersActivity.this, usersList, UsersActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> System.out.println(e.getMessage()));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(UsersActivity.this, UserUpdateActivity.class);


        intent.putExtra("email", usersList.get(position).getEmail());
        intent.putExtra("password", usersList.get(position).getPassword());
        intent.putExtra("privilege", usersList.get(position).getPrivilege());
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}