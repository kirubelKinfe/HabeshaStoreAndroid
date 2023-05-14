package com.example.commerceapp.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commerceapp.Models.Row;
import com.example.commerceapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> {
    Context context;
    List<Row> list;
    private final RecyclerViewInterface recyclerViewInterface;

    public RecyclerViewAdapter(Context context, List<Row> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.img.setImageResource(list.get(position).getImg());
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("$" + list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener{
        ImageView img;
        TextView title;
        TextView price;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView7);
            title = itemView.findViewById(R.id.textView3);
            price = itemView.findViewById(R.id.textView4);

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
