package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserProfileShow extends AppCompatActivity{

    Button chat, finish;
    TextView emailtv, nickname;
    UserDTO userDTO = new UserDTO();
    UserDTO myuserDTO = new UserDTO();
    FirebaseDatabase database;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_show);

        setTitle("Profile");

        finish = findViewById(R.id.finish);
        emailtv = findViewById(R.id.tv_email);
        nickname = findViewById(R.id.tv_nickname);
        Intent intent = getIntent();
        userDTO.setId(intent.getStringExtra("id"));
        userDTO.setNickname(intent.getStringExtra("nickname"));

        emailtv.setText("Email : "+userDTO.getId());
        nickname.setText("NickName : "+userDTO.getNickname());

        finish.setOnClickListener(onClickListener);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //데이터가 쌓이지 않게 프래그먼트1 호출할때마다 clear로 비운뒤 데이터가져오기
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //DTO class 에 기본생성자가 존재하여야됨 (**** 무조건****)
                    UserDTO d = snapshot.getValue(UserDTO.class);
                    if(user.getEmail().equals(d.getId()))
                        myuserDTO = d;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String n = userDTO.getNickname();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("UserBangList");
            switch (v.getId()) {



                case R.id.finish :
                    finish();
                    break;
            }

        }
    };


}