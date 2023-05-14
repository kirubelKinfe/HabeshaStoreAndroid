package com.example.splashlogin.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsHolder> {
    Context context;
    List<CartModel> cartModels;
    private final CartItemsInterface cartItemsInterface;

    public CartItemsAdapter(Context context, List<CartModel> cartModels, CartItemsInterface cartItemsInterface) {
        this.context = context;
        this.cartModels = cartModels;
        this.cartItemsInterface = cartItemsInterface;
    }


    @NonNull
    @Override
    public CartItemsAdapter.CartItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartItemsAdapter.CartItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsAdapter.CartItemsHolder holder, int position) {
        holder.title.setText(cartModels.get(position).getProductName());
        holder.price.setText("$" + cartModels.get(position).getTotalPrice());
        holder.quantityTextview.setText("X" + cartModels.get(position).getQuantity());
        Picasso.get()
                .load(cartModels.get(position).getImg())
                .fit()
                .centerCrop()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    protected class CartItemsHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;
        TextView price, quantityTextview;
        ImageView remove, increaseQuantity, decreaseQuantity;
        EditText quantityField;

        public CartItemsHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.productsImage);
            title = itemView.findViewById(R.id.productsName);
            price = itemView.findViewById(R.id.totalPriceTextView);
            remove = itemView.findViewById(R.id.removeCartItem);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            quantityField = itemView.findViewById(R.id.quantityField);
            quantityTextview = itemView.findViewById(R.id.quantityTextview);

            itemView.setOnClickListener(e -> {
                if(cartItemsInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        cartItemsInterface.onItemClick(position);
                    }
                }
            });
            remove.setOnClickListener(e -> {
                if(cartItemsInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        cartItemsInterface.onRemoveClick(position);
                    }
                }
            });
            increaseQuantity.setOnClickListener(e -> {
                if(cartItemsInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        cartItemsInterface.onIncreaseClick(position, quantityField, quantityTextview, price);
                    }
                }

            });
            decreaseQuantity.setOnClickListener(e -> {
                if(cartItemsInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        cartItemsInterface.onDecreaseClick(position, quantityField, quantityTextview, price);
                    }
                }
            });
        }
    }
}
