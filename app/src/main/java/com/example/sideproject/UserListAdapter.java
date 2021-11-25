package com.example.sideproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomViewHolder> {
    @NonNull
    Context context;
    ArrayList<UserDTO> arrayList;

    public UserListAdapter(ArrayList<UserDTO> arrayList) {
        this.arrayList = arrayList;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcylistitem,parent,false);
        context = v.getContext();


        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        UserDTO id = arrayList.get(position);



        holder.id.setText(id.getId());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserProfileShow.class);
                intent.putExtra("id",id.getId());
                intent.putExtra("nickname",id.getNickname());
                v.getContext().startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView id;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.Authid);




        }
    }
}
