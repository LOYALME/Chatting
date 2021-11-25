package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class Fragment_UserList extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    UserListAdapter userListAdapter;
    ArrayList<UserDTO> arrayList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String email;


    Fragment_UserList(Context c) {
        this.context = c;
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragmentmenu1,container,false);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        recyclerView = v.findViewById(R.id.fragrecy);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //데이터가 쌓이지 않게 프래그먼트1 호출할때마다 clear로 비운뒤 데이터가져오기
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //DTO class 에 기본생성자가 존재하여야됨 (**** 무조건****)
                    UserDTO d = snapshot.getValue(UserDTO.class);
                    if(!d.getId().equals(email))
                        arrayList.add(d);
                }
                userListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        userListAdapter = new UserListAdapter(arrayList);
        recyclerView.setAdapter(userListAdapter);
        return v;
    }




    @Override
    public void onStart() {
        super.onStart();
        Log.d("시작","시작");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("다이","다이");
    }
}