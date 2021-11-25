package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Chattinglist extends Fragment {

    Context context;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String email,nickname;


    Fragment_Chattinglist(Context c) {
        this.context = c;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragmentmenu2,container,false);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserDTO data = dataSnapshot.getValue(UserDTO.class);

                    if(data.getId().equals(email))
                        nickname = data.getNickname();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserBangList").child("user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChattingBangListDTO data = dataSnapshot.getValue(ChattingBangListDTO.class);
                    list.add(data.getUsername());
                }
                // listview 갱신
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
        listView = v.findViewById(R.id.frag2istview);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ChatRoomActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });




        return v;
}



}