package com.example.commerceapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.commerceapp.Fragments.CartFragment;
import com.example.commerceapp.Fragments.FavoritesFragment;
import com.example.commerceapp.Fragments.HomeFragment;
import com.example.commerceapp.Fragments.ProfileFragment;
import com.example.commerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    public static int cartNumber = 0;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.getOrCreateBadge(R.id.item2).setNumber(cartNumber);
        bottomNavigationView.getOrCreateBadge(R.id.item2).setVisible(true);
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.item1:
                selectedFragment = new HomeFragment();
                break;
            case R.id.item2:
                selectedFragment = new CartFragment();
                break;
            case R.id.item3:
                selectedFragment = new FavoritesFragment();
                break;
            case R.id.item4:
                selectedFragment = new ProfileFragment();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_layout,
                selectedFragment
        ).commit();

        return true;
    };


}