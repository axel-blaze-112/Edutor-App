package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miniproj2.Adapters.productItemAdapter;
import com.example.miniproj2.Adapters.redeemApprovAdapter;
import com.example.miniproj2.Models.redeemApprovModel;
import com.example.miniproj2.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyRedeemHistory extends AppCompatActivity {
private RecyclerView approv_rc;
    private Toolbar toolbar;

    List<redeemApprovModel> redeemmodel;
    redeemApprovAdapter redeemAda;
    private DatabaseReference usrref;
    private FirebaseAuth fireauth;
    private String currentuserid;
    private TextView notenrolled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redeem_history);



        toolbar=findViewById(R.id.redemTool);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("My Reedem History");
        notenrolled=findViewById(R.id.notenrolled);
        approv_rc=findViewById(R.id.approv_rc);
        usrref=  FirebaseDatabase.getInstance().getReference();

        redeemmodel=new ArrayList<>();
        LinearLayoutManager lm=new LinearLayoutManager(this);
        approv_rc.setLayoutManager(lm);
        redeemAda=new redeemApprovAdapter(redeemmodel,this);

        approv_rc.setAdapter(redeemAda);

        fireauth= FirebaseAuth.getInstance();
        currentuserid=fireauth.getCurrentUser().getUid();
        usrref.child("redeems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    String uid=ds.child("uid").getValue().toString();
                    if(uid.equals(currentuserid))
                    {

                        String prodid=ds.child("prod_id").getValue().toString();;
                        String prodprice=ds.child("product_price").getValue().toString();
                        String prodName=ds.child("product_name").getValue().toString();
                        String prodimg=ds.child("product_image_url").getValue().toString();
                        String waitIcon=ds.child("iconimg").getValue().toString();
                        String approval=ds.child("approval").getValue().toString();
                        String pleaseWait=ds.child("pleaseWait").getValue().toString();

                        redeemmodel.add(new redeemApprovModel(approval,prodid,prodimg,prodName,prodprice,pleaseWait,waitIcon));



                    }




                }

                redeemAda.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}