package com.example.sideproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainFragment extends AppCompatActivity implements onTabFragment {

    FrameLayout frameLayout;

    BottomNavigationView bottomNavigationView;
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment_UserList fragmentUser;
    Fragment_Chattinglist fragmentChattinglist;
    Fragment_My fragmentMy;
    ArrayList<UserDTO> userlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        frameLayout = findViewById(R.id.mainfragment);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        fragmentUser = new Fragment_UserList(this);
        fragmentChattinglist = new Fragment_Chattinglist(this);
        fragmentMy = new Fragment_My(this);
        /*SharedPreferences 데이터 받기 */
//        SharedPreferences sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
//        String n = sharedPreferences.getString("NAME","");
//        Log.e("SADSADSAD", n+"@@@");



        change(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1 :
                        change(1);
                        break;

                    case R.id.nav2 :
                        change(2);
                        break;

                    case R.id.nav3 :
                        change(3);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void change(int index) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (index) {
            case 1:
                setTitle("UserList");
                ft.replace(R.id.mainfragment, fragmentUser).commit();
                break;
            case 2:
                setTitle("ChattingList");
                ft.replace(R.id.mainfragment, fragmentChattinglist).commit();
                break;
            case 3:
                setTitle("MY");
                ft.replace(R.id.mainfragment, fragmentMy).commit();
                break;


        }
    }
}











