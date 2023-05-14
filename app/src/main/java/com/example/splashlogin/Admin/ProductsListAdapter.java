package com.example.splashlogin.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductListHolder>{

    private final Context context;
    private final List<ProductsModel> productsList;
    private final ProductsListInterface productsListInterface;

    public ProductsListAdapter(Context context, List<ProductsModel> productsList, ProductsListInterface productsListInterface) {
        this.context = context;
        this.productsList = productsList;
        this.productsListInterface = productsListInterface;
    }

    @NonNull
    @Override
    public ProductsListAdapter.ProductListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductListHolder(itemView, productsListInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductsListAdapter.ProductListHolder holder, int position) {
        ProductsModel productsModel = productsList.get(position);
        holder.title.setText(productsModel.getProductName());
        holder.price.setText("$" + productsModel.getProductPrice());
        Picasso.get()
                .load(productsModel.getImg())
                .fit()
                .centerCrop()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class ProductListHolder extends RecyclerView.ViewHolder {
        public TextView title, price;
        public ImageView thumbnail;

        public ProductListHolder(@NonNull View itemView, ProductsListInterface productsListInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            thumbnail.setOnClickListener(view -> {
                if(productsListInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        productsListInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
