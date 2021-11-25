package com.example.sideproject;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.CustomViewHolder> {
    @NonNull


    String nickname;
    Context context;
    ArrayList<ChatDTO> arrayList ;

    public ChattingAdapter(ArrayList<ChatDTO> arrayList,String nickname) {
        this.nickname = nickname;
        this.arrayList = arrayList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat,parent,false);
        this.context = parent.getContext();
        return new CustomViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        ChatDTO chat = arrayList.get(position);

        if(chat != null && chat.getNickName().equals(nickname)) {
            holder.nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        holder.nickname.setText(chat.getNickName());
        holder.msg.setText(chat.getMsg());
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }
 


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView nickname, msg;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.tv_nickname);
            msg = itemView.findViewById(R.id.tv_msg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }
    }

    public void addChat(ChatDTO chatDTO) {
        arrayList.add(chatDTO);
        notifyItemInserted(arrayList.size()-1);
    }


}
