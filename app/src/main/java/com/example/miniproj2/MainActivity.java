package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
TextView btnl,btn2;
private FirebaseAuth useraut;

BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* btnl=findViewById(R.id.btn);
        btn2=findViewById(R.id.logout);*/
        useraut= FirebaseAuth.getInstance();
        /*btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserLoginActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           logout();
            }
        });*/

        navigationView=findViewById(R.id.Main_bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new Home()).commit();
        navigationView.setSelectedItemId(R.id.Home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                Fragment frag=null;
                switch(item.getItemId())
                {
                    case R.id.Home:
                        frag=new Home();
                        break;
                    case R.id.Reedem:
                        frag=new HomeFragment();
                        break;

                    case R.id.Dashboard:
                        frag=new RedeemFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,frag).commit();
                return true;
            }
        });



    }

}