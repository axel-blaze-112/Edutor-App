package com.example.miniproj2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniproj2.Adapters.productItemAdapter;
import com.example.miniproj2.Models.productsModel;
import com.example.miniproj2.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RedeemFragment extends Fragment {
    public EditText btm;
    ConstraintLayout cn;
    List<productsModel> list,searchModel;
    List<userModel> usermod;
    RecyclerView productsRecy;
    productItemAdapter proditemAda;
    private String currentuserid;
    private DatabaseReference myref,usrref;
    private EditText search;
    private FirebaseAuth fireauth;
    TextView artse,penser,bookser,bagser,All,userprofname;
    CircleImageView userprofimg;


    public RedeemFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_redeem, container, false);

        //btm=v.findViewById(R.id.search_prod);
        cn=v.findViewById(R.id.constraintLayout101);
        search=v.findViewById(R.id.search_prod);
        artse=v.findViewById(R.id.artSear);
        penser=v.findViewById(R.id.penSear);
        bookser=v.findViewById(R.id.bookSear);
        bagser=v.findViewById(R.id.findcourse);
        All=v.findViewById(R.id.All);
        userprofname=v.findViewById(R.id.usernamesearch);
        userprofimg=v.findViewById(R.id.userprofilepic);

        myref=  FirebaseDatabase.getInstance().getReference();
        usrref=  FirebaseDatabase.getInstance().getReference();

        fireauth= FirebaseAuth.getInstance();
        currentuserid=fireauth.getCurrentUser().getUid();

        searchModel=new ArrayList<>();

        productsRecy=v.findViewById(R.id.prodc_recycler);
        list=new ArrayList<>();
        getData();
        RecyclerView.LayoutManager lm=new GridLayoutManager(getContext(), 2);
        productsRecy.setLayoutManager(lm);
        proditemAda=new productItemAdapter(getContext(),list);
        productsRecy.setAdapter(proditemAda);

        penser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModel.clear();
                for(productsModel ps:list)
                {
                    if(ps.getProdCategory().equals("Pens"))
                    {
                        searchModel.add(ps);
                    }
                }
                proditemAda=new productItemAdapter(getContext(),searchModel);
                productsRecy.setAdapter(proditemAda);
                proditemAda.notifyDataSetChanged();
            }
        });
        bookser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModel.clear();
                for(productsModel ps:list)
                {
                    if(ps.getProdCategory().equals("Books"))
                    {
                        searchModel.add(ps);
                    }
                }
                proditemAda=new productItemAdapter(getContext(),searchModel);
                productsRecy.setAdapter(proditemAda);
                proditemAda.notifyDataSetChanged();

            }
        });
        artse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModel.clear();
                for(productsModel ps:list)
                {
                    if(ps.getProdCategory().equals("Arts"))
                    {
                        searchModel.add(ps);
                    }
                }
                proditemAda=new productItemAdapter(getContext(),searchModel);
                productsRecy.setAdapter(proditemAda);
                proditemAda.notifyDataSetChanged();

            }
        });
        bagser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModel.clear();
                for(productsModel ps:list)
                {
                    if(ps.getProdCategory().equals("Bags"))
                    {
                        searchModel.add(ps);
                    }
                }
                proditemAda=new productItemAdapter(getContext(),searchModel);
                productsRecy.setAdapter(proditemAda);
                proditemAda.notifyDataSetChanged();

            }
        });
        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModel.clear();
                proditemAda=new productItemAdapter(getContext(),list);
                productsRecy.setAdapter(proditemAda);
                proditemAda.notifyDataSetChanged();

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                searchModel.clear();
                if(s.toString().isEmpty())
                {
                    proditemAda=new productItemAdapter(getContext(),list);
                    productsRecy.setAdapter(proditemAda);
                    proditemAda.notifyDataSetChanged();
                }
                else
                {
                    filter(s.toString());
                }
            }

        });
        usrref.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {

                    if(snapshot.hasChild("username"))
                    {
                        String username=snapshot.child("username").getValue().toString();
                        userprofname.setText("Hello , "+username);
                    }
                    if(snapshot.hasChild("image"))
                    {
                        String usernimg=snapshot.child("image").getValue().toString();
                        Picasso.get().load(usernimg).into(userprofimg);
                    }
                    else
                    {
                        Picasso.get().load(R.drawable.profile).into(userprofimg);
                    }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   private void getData()
    {

       myref.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds:snapshot.getChildren())
                {

                    String prodid=ds.child("prod_id").getValue().toString();;
                    String prodDesc=ds.child("product_description").getValue().toString();
                    String prodName=ds.child("product_name").getValue().toString();
                    String prodPrice=ds.child("product_price").getValue().toString();
                    String stockCount=ds.child("stock_count").getValue().toString();
                    String prodCate=ds.child("category").getValue().toString();
                    String prodimg=ds.child("product_image_url").getValue().toString();
                    list.add(new productsModel(prodid,prodName,Integer.parseInt(stockCount),Integer.parseInt(prodPrice),prodimg,prodCate,prodDesc));



                }
                proditemAda.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void filter(String text)
    {
        for(productsModel ps:list)
        {
            if(ps.getProductName().equals(text))
            {
                searchModel.add(ps);
            }
        }
        proditemAda=new productItemAdapter(getContext(),searchModel);
        productsRecy.setAdapter(proditemAda);
        proditemAda.notifyDataSetChanged();
    }

    private void pensearcy()
    {
        for(productsModel ps:list)
        {
            if(ps.getProdCategory().equals(""))
            {
                searchModel.add(ps);
            }
        }
        proditemAda=new productItemAdapter(getContext(),searchModel);
        productsRecy.setAdapter(proditemAda);
        proditemAda.notifyDataSetChanged();

    }

}