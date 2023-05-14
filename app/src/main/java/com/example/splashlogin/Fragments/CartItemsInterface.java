package com.example.splashlogin.Fragments;

import android.widget.EditText;
import android.widget.TextView;

public interface CartItemsInterface {
    void onItemClick(int pos);
    void onRemoveClick(int pos);
    void onIncreaseClick(int pos, EditText quantityField, TextView quantityText, TextView totalPriceText);
    void onDecreaseClick(int pos, EditText quantityField, TextView quantityText, TextView totalPriceText);
}
