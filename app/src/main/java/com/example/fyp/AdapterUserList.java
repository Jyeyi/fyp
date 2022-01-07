package com.example.fyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.UserViewList> {

    Context context;
    ArrayList<user> list;

    public AdapterUserList(Context context, ArrayList<user> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_list,parent,false);
        return new UserViewList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewList holder, int position) {

        user user = list.get(position);
        holder.u_name.setText(user.getUser_name());
        holder.u_email.setText(user.getUser_email());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserViewList extends RecyclerView.ViewHolder{

        TextView u_name;
        TextView u_email;

        public UserViewList(@NonNull View itemView) {
            super(itemView);
            u_name = itemView.findViewById(R.id.u_name);
            u_email = itemView.findViewById(R.id.u_email);
        }
    }
}
