package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoomActivity extends AppCompatActivity {

    EditText chat;
    Button send;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ChattingAdapter adapter;
    ArrayList<ChatDTO> chatlist;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String email, nickname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        if(getIntent() != null ) {
            Intent intent = getIntent();
            nickname = intent.getStringExtra("nickname");
            email = intent.getStringExtra("email");
        }

        setTitle(" 채팅방 ");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Message");
        send = findViewById(R.id.send);
        chat = findViewById(R.id.edit_chat);
        recyclerView = findViewById(R.id.my_recycler_view);
        //recyclerView.scrollToPosition(adapter.getItemCount()-1); // 리사이클러뷰 제일하단 최신 데이터에 스크롤 포커스
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        chatlist = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatDTO c = dataSnapshot.getValue(ChatDTO.class);
                    chatlist.add(c);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ChattingAdapter(chatlist,nickname);

        recyclerView.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatting = chat.getText().toString();
                if(chatting != null) {
                    ChatDTO chatDTO22 = new ChatDTO(FirebaseAuth.getInstance().getCurrentUser().getEmail(),chatting,nickname);
                    myRef.push().setValue(chatDTO22);
                    chat.setText("");
                }

            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatDTO data = snapshot.getValue(ChatDTO.class);
                adapter.addChat(data);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatlist = null;
    }
}



