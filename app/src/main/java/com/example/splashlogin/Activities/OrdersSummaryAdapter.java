package com.example.splashlogin.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Models.CartModel;
import com.example.splashlogin.R;

import java.util.List;

public class OrdersSummaryAdapter extends RecyclerView.Adapter<OrdersSummaryAdapter.OrdersSummaryListHolder> {
    private final Context context;
    private final List<CartModel> ordersSummaryList;

    public OrdersSummaryAdapter(Context context, List<CartModel> ordersSummaryList) {
        this.context = context;
        this.ordersSummaryList = ordersSummaryList;
    }

    @NonNull
    @Override
    public OrdersSummaryAdapter.OrdersSummaryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_summary_item, parent, false);
        return new OrdersSummaryAdapter.OrdersSummaryListHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersSummaryAdapter.OrdersSummaryListHolder holder, int position) {
        CartModel cartModel = ordersSummaryList.get(position);
        holder.name.setText(cartModel.getProductName());
        holder.totalPrice.setText("$" + cartModel.getTotalPrice());
        holder.quantity.setText("X" + cartModel.getQuantity());
    }

    @Override
    public int getItemCount() {
        return ordersSummaryList.size();
    }

    public static class OrdersSummaryListHolder extends RecyclerView.ViewHolder {
        public TextView name, totalPrice, quantity;

        public OrdersSummaryListHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameText);
            totalPrice = itemView.findViewById(R.id.totalPriceText);
            quantity = itemView.findViewById(R.id.quantityText);
        }
    }
}
