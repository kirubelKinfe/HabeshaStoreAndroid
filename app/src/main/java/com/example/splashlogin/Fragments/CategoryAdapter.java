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

import com.example.splashlogin.Models.CategoryModel;
import com.example.splashlogin.Models.FavoritesDB;
import com.example.splashlogin.Models.ProductsModel;
import com.example.splashlogin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemHolder>{
    Context context;
    List<CategoryModel> categoriesList;
    private final CategoryInterface categoryInterface;

    public CategoryAdapter(Context context, List<CategoryModel> categoriesList, CategoryInterface categoryInterface) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.categoryInterface = categoryInterface;
    }


    @NonNull
    @Override
    public CategoryAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ItemHolder holder, int position) {
        holder.title.setText(categoriesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener{
        TextView title;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.categoryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(categoryInterface != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    categoryInterface.onItemClick(position);
                }
            }
        }
    }
}
