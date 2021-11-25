package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_My extends Fragment {

    ArrayList<UserDTO> arrayList = new ArrayList<>();
    Button logout, taltae;
    Context context;
    String email;
    private UserDTO myInfo;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    Fragment_My(Context c) {
        this.context = c;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.talteaup :
                    for(int i=0; i<arrayList.size(); i++) {
                        if(arrayList.get(i).getId().equals(email)) {
                            userListDelete(arrayList.get(i));
                            break;
                        }
                    }
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(context,"회원탈퇴성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(v.getContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(getContext(),"회원탈퇴성공",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }
                    });


                    break;

                case R.id.logoutbtu :
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(v.getContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(getContext(),"로그아웃!",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
            }

        }
    };

    public void userListDelete(UserDTO user) {
        databaseReference.child(user.getId().substring(0,user.getId().indexOf('@'))).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"데이터삭제성공",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"데이터삭제실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        myInfo = new UserDTO();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserDTO userDTO = dataSnapshot.getValue(UserDTO.class);
                    arrayList.add(userDTO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        View v = inflater.inflate(R.layout.activity_fragmentmenu3,container,false);
        logout = v.findViewById(R.id.logoutbtu);
        taltae = v.findViewById(R.id.talteaup);
        taltae.setOnClickListener(onClickListener);
        logout.setOnClickListener(onClickListener);
        return v;
    }
}