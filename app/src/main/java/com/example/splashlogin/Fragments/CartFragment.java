package com.example.splashlogin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.splashlogin.Activities.CheckoutActivity;
import com.example.splashlogin.Models.CartDB;
import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.R;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements CartItemsInterface{

    private RecyclerView cartRecyclerView;
    private CartItemsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<CartModel> cartsList;
    TextView totalTxt, totalFeeTxt;
    Button check_outBtn;

    int quantity = 1;
    int totalPrice = 0;
    int totalPurchasePrice = 0;

    CartDB cartDB;


    public CartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);


        check_outBtn = rootView.findViewById(R.id.check_outBtn);
        totalTxt = rootView.findViewById(R.id.totalTV);
        totalFeeTxt = rootView.findViewById(R.id.totalFeeTxt);


        cartsList = new ArrayList<>();
        adapter = new CartItemsAdapter(getContext(),cartsList, this);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(adapter);

        cartDB = new CartDB(getActivity());
        List<CartModel> cartItems = cartDB.getAllCartItems();
        cartsList.addAll(cartItems);

        for (CartModel cartItem : cartItems) {
            totalPurchasePrice += cartItem.getTotalPrice();
        }
        cartDB.close();

        totalTxt.setText("$" + totalPurchasePrice);
        totalFeeTxt.setText("$" + totalPurchasePrice);

        check_outBtn.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onItemClick(int pos) {

    }

    @Override
    public void onRemoveClick(int pos) {
        CartDB cartDB = new CartDB(getActivity());
        cartDB.removeCartItem(cartsList.get(pos).getID());
        Toast.makeText(getActivity(), "Successfully Removed", Toast.LENGTH_SHORT).show();
        cartsList.clear();
        totalPurchasePrice = 0;

        List<CartModel> cartItems = cartDB.getAllCartItems();
        cartsList.addAll(cartItems);

        adapter = new CartItemsAdapter(getContext(),cartsList, this);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(adapter);

        for (CartModel cartItem : cartItems) {
            totalPurchasePrice += cartItem.getTotalPrice();
        }
        cartDB.close();

        totalTxt.setText("$" + totalPurchasePrice);
        totalFeeTxt.setText("$" + totalPurchasePrice);

    }

    @Override
    public void onIncreaseClick(int pos, EditText quantityField, TextView quantityText, TextView totalPriceText) {
        quantity = cartsList.get(pos).getQuantity();
        quantity += Integer.parseInt(quantityField.getText().toString());
        totalPrice = cartsList.get(pos).getProductPrice() * quantity;

        CartModel cartModel = new CartModel(
                cartsList.get(pos).getID(),
                cartsList.get(pos).getProductName(),
                cartsList.get(pos).getProductPrice(),
                cartsList.get(pos).getImg(),
                quantity,
                totalPrice);

        CartDB cartDB = new CartDB(getActivity());
        cartDB.updateCartItem(cartModel);

        cartsList.clear();
        totalPurchasePrice = 0;

        List<CartModel> cartItems = cartDB.getAllCartItems();
        cartsList.addAll(cartItems);

        adapter = new CartItemsAdapter(getContext(),cartsList, this);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(adapter);

        for (CartModel cartItem : cartItems) {
            totalPurchasePrice += cartItem.getTotalPrice();
        }
        cartDB.close();

        totalTxt.setText("$" + totalPurchasePrice);
        totalFeeTxt.setText("$" + totalPurchasePrice);
    }

    @Override
    public void onDecreaseClick(int pos, EditText quantityField, TextView quantityText, TextView totalPriceText) {
        quantity = cartsList.get(pos).getQuantity();
        if(quantity > 1) {
            quantity -= Integer.parseInt(quantityField.getText().toString());
        }
        totalPrice = cartsList.get(pos).getProductPrice() * quantity;

        CartModel cartModel = new CartModel(
                cartsList.get(pos).getID(),
                cartsList.get(pos).getProductName(),
                cartsList.get(pos).getProductPrice(),
                cartsList.get(pos).getImg(),
                quantity,
                totalPrice);

        CartDB cartDB = new CartDB(getActivity());
        cartDB.updateCartItem(cartModel);
        cartsList.clear();
        totalPurchasePrice = 0;

        List<CartModel> cartItems = cartDB.getAllCartItems();
        cartsList.addAll(cartItems);

        adapter = new CartItemsAdapter(getContext(),cartsList, this);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(adapter);

        for (CartModel cartItem : cartItems) {
            totalPurchasePrice += cartItem.getTotalPrice();
        }
        cartDB.close();

        totalTxt.setText("$" + totalPurchasePrice);
        totalFeeTxt.setText("$" + totalPurchasePrice);
    }
}