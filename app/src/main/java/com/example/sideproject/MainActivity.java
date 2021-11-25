package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    EditText id, pw;
    Button loginbtu, signupbtu, finishbtu;
    FirebaseUser user;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtu = findViewById(R.id.loginButton);
        signupbtu = findViewById(R.id.signupButton);
        finishbtu = findViewById(R.id.finishButton);
        id = findViewById(R.id.etId);
        pw = findViewById(R.id.etPw);

        loginbtu.setOnClickListener(onClickListener);
        signupbtu.setOnClickListener(onClickListener);
        finishbtu.setOnClickListener(onClickListener);
        firebaseAuth = FirebaseAuth.getInstance();

        //로그아웃 여부확인
        user = FirebaseAuth.getInstance().getCurrentUser();
        //로그아웃 안햇다면 바로 MainFragment.class로 화면전환
        if(user != null) {
            /*SharedPreferences 데이터 저장 */
//            SharedPreferences sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("NAME","");
//            editor.commit();
            Intent intent = new Intent(MainActivity.this, MainFragment.class);
            startActivity(intent);
            finish();
        }





        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, MainFragment.class);
                    startActivity(intent);
                    finish();
                }
            }
        };




    }




    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton :
                    String d = id.getText().toString();
                    String p = pw.getText().toString();

                    if(!d.equals("") && !p.equals(""))
                        loginUser(d,p);

                    break;

                case R.id.signupButton :
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                    break;

                case R.id.finishButton :
                    finish();
                    break;
            }

        }

    };

    private void loginUser(String id, String pass) {
        firebaseAuth.signInWithEmailAndPassword(id, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startToast("로그인성공");
                            firebaseAuth.addAuthStateListener(authStateListener);
                        } else {
                            startToast("로그인 실패");
                        }
                    }
                });
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }

        private void startToast (String mes){
            Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
        }
}