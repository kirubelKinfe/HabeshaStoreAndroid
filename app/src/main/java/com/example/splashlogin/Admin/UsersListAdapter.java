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

import com.example.splashlogin.R;
import com.example.splashlogin.Models.UserModel;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersListHolder>{

    private final Context context;
    private final List<UserModel> usersList;
    private final UsersListInterface usersListInterface;

    public UsersListAdapter(Context context, List<UserModel> usersList, UsersListInterface usersListInterface) {
        this.context = context;
        this.usersList = usersList;
        this.usersListInterface = usersListInterface;
    }

    @NonNull
    @Override
    public UsersListAdapter.UsersListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UsersListAdapter.UsersListHolder(itemView, usersListInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersListAdapter.UsersListHolder holder, int position) {
        UserModel userModel = usersList.get(position);
        holder.email.setText(userModel.getEmail());
        holder.privilege.setText(userModel.getPrivilege());
        holder.thumbnail.setImageResource(R.drawable.users);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UsersListHolder extends RecyclerView.ViewHolder {
        public TextView email, privilege;
        public ImageView thumbnail;

        public UsersListHolder(@NonNull View itemView, UsersListInterface usersListInterface) {
            super(itemView);

            email = itemView.findViewById(R.id.adminUserEmail);
            privilege = itemView.findViewById(R.id.usersPrivilege);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(view -> {
                if(usersListInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        usersListInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
