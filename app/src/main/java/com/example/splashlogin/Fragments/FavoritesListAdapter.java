package com.example.splashlogin.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.Models.FavoritesDB;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ItemHolder>{
    Context context;
    List<ProductsModel> favoritesList;
    private final FavoritesListInterface favoritesListInterface;

    public FavoritesListAdapter(Context context, List<ProductsModel> favoritesList, FavoritesListInterface favoritesListInterface) {
        this.context = context;
        this.favoritesList = favoritesList;
        this.favoritesListInterface = favoritesListInterface;
    }


    @NonNull
    @Override
    public FavoritesListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesListAdapter.ItemHolder holder, int position) {
        ProductsModel productsModel = favoritesList.get(position);
        FavoritesDB favoritesDB = new FavoritesDB(context);
        Picasso.get()
                    .load(productsModel.getImg())
                    .fit()
                    .centerCrop()
                    .into(holder.img);
        holder.title.setText(favoritesList.get(position).getProductName());
        holder.price.setText("$" + productsModel.getProductPrice());
        if(favoritesDB.isFavorites(productsModel.getmKey())) {
            holder.fav_icon.setImageResource(R.drawable.red_fav_icon);
        }
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener{
        ImageView img, fav_icon;
        TextView title;
        TextView price;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView7);
            fav_icon = itemView.findViewById(R.id.fav_icon);
            title = itemView.findViewById(R.id.textView3);
            price = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(this);
            fav_icon.setOnClickListener(e -> {
                if(favoritesListInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        favoritesListInterface.onFavIconClick(position);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if(favoritesListInterface != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    favoritesListInterface.onItemClick(position);
                }
            }
        }
    }
}
