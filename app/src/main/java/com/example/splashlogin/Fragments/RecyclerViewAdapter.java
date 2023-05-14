package com.example.splashlogin.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.Models.FavoritesDB;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> {
    Context context;
    List<ProductsModel> productsList;
    private final RecyclerViewInterface recyclerViewInterface;
    FavoritesDB favoritesDB;

    public RecyclerViewAdapter(Context context, List<ProductsModel> productsList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productsList = productsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ItemHolder holder, int position) {
        ProductsModel productsModel = productsList.get(position);
        favoritesDB = new FavoritesDB(context);
        Picasso.get()
                .load(productsModel.getImg())
                .fit()
                .centerCrop()
                .into(holder.img);
        holder.title.setText(productsList.get(position).getProductName());
        holder.price.setText("$" + productsList.get(position).getProductPrice());

        if(favoritesDB.isFavorites(productsModel.getmKey())) {
            holder.fav_icon.setImageResource(R.drawable.red_fav_icon);
        }

        holder.fav_icon.setOnClickListener(view -> {
            if(favoritesDB.isFavorites(productsModel.getmKey())) {
                favoritesDB.removeFromFavorites(productsModel.getmKey());
                holder.fav_icon.setImageResource(R.drawable.gray_fav_icon);
            }
            else
            {
                favoritesDB.addToFavorite(productsModel.getmKey());
                holder.fav_icon.setImageResource(R.drawable.red_fav_icon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener{
        ImageView img, fav_icon;
        TextView title;
        TextView price;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView7);
            title = itemView.findViewById(R.id.textView3);
            price = itemView.findViewById(R.id.textView4);
            fav_icon = itemView.findViewById(R.id.fav_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(recyclerViewInterface != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onItemClick(position);
                }
            }
        }
    }
}
