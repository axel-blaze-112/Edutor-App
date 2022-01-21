package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class prodDetailsActivity extends AppCompatActivity {
    private String currentuserid;
    private ImageView prodImg;
    private TextView prodName,prodprice,prodcategory,proddesc;
    private Button redeem;
    private DatabaseReference myref;
    private String pid;
    private FirebaseAuth fireauth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);

        prodImg=findViewById(R.id.product_detail_image);
        prodName=findViewById(R.id.details_prod_name);
        prodprice=findViewById(R.id.details_prod_price);
        prodcategory=findViewById(R.id.details_prod_category);
        proddesc=findViewById(R.id.details_prod_desc);
        myref=  FirebaseDatabase.getInstance().getReference();
        pid=getIntent().getStringExtra("prodid");
        redeem=findViewById(R.id.product_details_redeem);
        fireauth= FirebaseAuth.getInstance();
        currentuserid=fireauth.getCurrentUser().getUid();


        Log.d("checkpid",pid.toString());


        redeem.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                redeemFun();
            }
        });

        myref.child("products").child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot ds) {

                //String prodid=ds.child("prod_id").getValue().toString();;
                String prodDesc=ds.child("product_description").getValue().toString();
                String prodNames=ds.child("product_name").getValue().toString();
                String prodPrice=ds.child("product_price").getValue().toString();
                //String stockCount=ds.child("stock_count").getValue().toString();
                String prodCate=ds.child("category").getValue().toString();
                String prodimg=ds.child("product_image_url").getValue().toString();
                Picasso.get().load(prodimg).placeholder(R.drawable.theme2).into(prodImg);
                prodName.setText(prodNames);
                prodprice.setText(prodPrice);
                prodcategory.setText(prodCate);
                proddesc.setText(prodDesc);

            }





            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    private void redeemFun()
    {
        final SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(prodDetailsActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitle("Please Wait...");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();

        myref.child("products").child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot prodsnapshot)
            {
                int coinsrequired=Integer.parseInt(prodsnapshot.child("product_price").getValue().toString());
                int currentStocks=Integer.parseInt(prodsnapshot.child("stock_count").getValue().toString());
                myref.child("users").child(currentuserid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
                    {
                        int usercoins=Integer.parseInt(snapshot.child("coins").getValue().toString());

                        if(currentStocks==0)
                        {
                            Toast.makeText(prodDetailsActivity.this,"Product Out of Stock",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            if(usercoins >= coinsrequired)
                            {
                                int updatedcoins=usercoins-coinsrequired;

                                HashMap<String,Object> asd=new HashMap<>();
                                asd.put("coins",updatedcoins);
                                myref.child("users").child(currentuserid).updateChildren(asd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        Toast.makeText(prodDetailsActivity.this,"Coins Deducted Successfully", Toast.LENGTH_SHORT).show();
                                        String id= UUID.randomUUID().toString();



                                        String prodid=prodsnapshot.child("prod_id").getValue().toString();;
                                        String prodDesc=prodsnapshot.child("product_description").getValue().toString();
                                        String prodNames=prodsnapshot.child("product_name").getValue().toString();
                                        String prodPrice=prodsnapshot.child("product_price").getValue().toString();

                                        String prodCate=prodsnapshot.child("category").getValue().toString();
                                        String prodimg=prodsnapshot.child("product_image_url").getValue().toString();
                                        String approv="Your Product will be Approved by Admin";

                                        HashMap<String,Object> approvalmp=new HashMap<>();
                                        approvalmp.put("prod_id",prodid);
                                        approvalmp.put("product_image_url",prodimg);
                                        approvalmp.put("product_name",prodNames);
                                        approvalmp.put("redeem_id",id);
                                        approvalmp.put("product_price",prodPrice);
                                        approvalmp.put("approval",approv);
                                        approvalmp.put("uid",currentuserid);
                                        approvalmp.put("pleaseWait","Please wait!!");
                                        approvalmp.put("iconimg","https://firebasestorage.googleapis.com/v0/b/miniproj2-cddb9.appspot.com/o/pleaswaiticon.png?alt=media&token=7fdae1d3-b45f-49be-8661-ea2313ed9f18");
                                        myref.child("redeems").child(id).setValue(approvalmp);

                                        Toast.makeText(prodDetailsActivity.this, "you have enough coins", Toast.LENGTH_SHORT).show();
                                        sweetAlertDialog.changeAlertType(sweetAlertDialog.SUCCESS_TYPE);
                                        sweetAlertDialog.setTitleText("Success");
                                        sweetAlertDialog.setContentText("Please Wait Till Admin Approve Your Order!!");
                                        sweetAlertDialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        }).show();

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(prodDetailsActivity.this, "you dont have enought coins", Toast.LENGTH_SHORT).show();
                                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("Failed");
                                sweetAlertDialog.setContentText("you dont have enought coins");
                                sweetAlertDialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                }).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error)
            {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(prodDetailsActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}