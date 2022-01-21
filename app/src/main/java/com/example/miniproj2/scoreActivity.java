package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class scoreActivity extends AppCompatActivity {
private Button btn;
private TextView scored,total,recivepoints;
private String key,setId;
private int scoreduser;
FirebaseDatabase dat=FirebaseDatabase.getInstance();
DatabaseReference mr=dat.getReference();
private FirebaseAuth usrauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        usrauth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.done_btn);
        scored=findViewById(R.id.scored);
        total=findViewById(R.id.total);
        recivepoints=findViewById(R.id.recivepoints);
        scored.setText(String.valueOf(getIntent().getIntExtra("scored",0)));
        total.setText(String.valueOf(getIntent().getIntExtra("total",0)));
        scoreduser=getIntent().getIntExtra("scored",0);
        key=getIntent().getStringExtra("key");
        setId=getIntent().getStringExtra("setId");

        Log.d("keyinfo",key.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        HashMap<String,Object> scorehash=new HashMap<>();
        scorehash.put("uid",usrauth.getCurrentUser().getUid());
        scorehash.put("score",scoreduser);

        mr.child("testScore").child(key).child(setId).child(usrauth.getCurrentUser().getUid()).setValue(scorehash);
        mr.child("testScore").child(key).child(setId).child(usrauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if( scoreduser==1)
                {
                    recivepoints.setText("Sorry You Failed");
                    return;
                }
                else if(scoreduser==2)
                {
                    recivepoints.setText("Sorry You Failed");
                    return;
                }
                else if(scoreduser==3)
                {

                    mr.child("testScore").child(key).child(setId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
                        {
                            if(snapshot.hasChild(usrauth.getCurrentUser().getUid()))
                            {
                                Toast.makeText(scoreActivity.this,"You have Already Taken this test",Toast.LENGTH_SHORT).show();
                                recivepoints.setText("You Have Already Taken This Test");
                            }
                            else
                            {
                                mr.child("users").child(usrauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        int coins=Integer.parseInt(snapshot.child("coins").getValue().toString());
                                        int updatecoins=coins+5;
                                        HashMap<String,Object>updatedcoinshash=new HashMap<>();
                                        updatedcoinshash.put("coins",updatecoins);
                                        mr.child("users").child(usrauth.getCurrentUser().getUid()).updateChildren(updatedcoinshash);
                                        recivepoints.setText("You Received : "+5);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
                else if(scoreduser==4)
                {
                    if(snapshot.hasChild(usrauth.getCurrentUser().getUid()))
                    {
                        Toast.makeText(scoreActivity.this,"You have Already Taken this test",Toast.LENGTH_SHORT).show();
                        recivepoints.setText("You Have Already Taken This Test");
                    }
                    else
                    {
                        mr.child("users").child(usrauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                int coins=Integer.parseInt(snapshot.child("coins").getValue().toString());
                                int updatecoins=coins+10;
                                HashMap<String,Object>updatedcoinshash=new HashMap<>();
                                updatedcoinshash.put("coins",updatecoins);
                                mr.child("users").child(usrauth.getCurrentUser().getUid()).updateChildren(updatedcoinshash);
                                recivepoints.setText("You Received : "+10);
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }


                }
                else if(scoreduser==5)
                {

                    if(snapshot.hasChild(usrauth.getCurrentUser().getUid()))
                    {
                        Toast.makeText(scoreActivity.this,"You have Already Taken this test",Toast.LENGTH_SHORT).show();
                        recivepoints.setText("You Have Already Taken This Test");
                    }
                    else
                    {
                        mr.child("users").child(usrauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                int coins=Integer.parseInt(snapshot.child("coins").getValue().toString());
                                int updatecoins=coins+15;
                                HashMap<String,Object>updatedcoinshash=new HashMap<>();
                                updatedcoinshash.put("coins",updatecoins);
                                mr.child("users").child(usrauth.getCurrentUser().getUid()).updateChildren(updatedcoinshash);
                                recivepoints.setText("You Received : "+15);
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}