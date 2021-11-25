package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.UnicodeSet;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private String token, uid;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    Button joinbtu;
    EditText email, password,nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        joinbtu = findViewById(R.id.btn_join);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_passWord1);
        nickname = findViewById(R.id.editText_nickname);

        joinbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                            String e = email.getText().toString();
                            String p = password.getText().toString();
                            String n = nickname.getText().toString();
                            createUser(e, p, n);
                    } else {
                        startToast("공란이 존재합니다");
                    }


            }
        });



    }


    public void createUser(String email, String pass,String nickname){

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startToast("회원가입성공");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if(task.isSuccessful()) {
                                token = task.getResult().getToken(); // 회원가입한 ID Token 값 가져오기
                                uid = user.getUid();    // 회원가입한 ID UID 값 가져오기
                                userListadd(new UserDTO(email,pass,nickname,token,uid));
                            }
                        }
                    });
                    finish();
                } else {
                    startToast("다시입력해주세요~!");
               }
            }
        });

    }

    public void userListadd(UserDTO u) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserList");
        databaseReference.child(u.getId().substring(0,u.getId().indexOf('@'))).setValue(u);
    }

    public void startToast (String mes) {
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }
}




























