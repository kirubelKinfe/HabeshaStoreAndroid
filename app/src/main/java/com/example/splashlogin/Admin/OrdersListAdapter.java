package com.example.splashlogin.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashlogin.Models.OrdersModel;
import com.example.splashlogin.R;

import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersListHolder>{
    private final Context context;
    private final List<OrdersModel> ordersList;
    private final OrdersListInterface ordersListInterface;

    public OrdersListAdapter(Context context,List<OrdersModel> ordersList ,OrdersListInterface ordersListInterface) {
        this.context = context;
        this.ordersList = ordersList;
        this.ordersListInterface = ordersListInterface;
    }

    @NonNull
    @Override
    public OrdersListAdapter.OrdersListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_item, parent, false);
        return new OrdersListAdapter.OrdersListHolder(itemView, ordersListInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersListAdapter.OrdersListHolder holder, int position) {
        OrdersModel ordersModel = ordersList.get(position);
        holder.orderId.setText("#" + ordersModel.getmKey());
        holder.orderPhoneNum.setText(ordersModel.getPhoneNum());
        holder.orderCity.setText(ordersModel.getCity() + " " + ordersModel.getStreet());
        holder.orderStatus.setText(ordersModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrdersListHolder extends RecyclerView.ViewHolder {
        public TextView orderId, orderCity;
        public TextView orderStatus, orderPhoneNum;

        public OrdersListHolder(@NonNull View itemView, OrdersListInterface ordersListInterface) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            orderPhoneNum = itemView.findViewById(R.id.orderPhoneNum);
            orderCity = itemView.findViewById(R.id.orderCity);
            orderStatus = itemView.findViewById(R.id.orderStatus);

            itemView.setOnClickListener(view -> {
                if(ordersListInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        ordersListInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
