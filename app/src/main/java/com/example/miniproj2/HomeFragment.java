package com.example.miniproj2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.miniproj2.Adapters.courseHomeAdapter;
import com.example.miniproj2.Adapters.courseTrendingAdapter;
import com.example.miniproj2.Adapters.productItemAdapter;
import com.example.miniproj2.Models.courseHomeModel;
import com.example.miniproj2.Models.productsModel;
import com.example.miniproj2.Models.trendingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    public RecyclerView popular_rc;
    public RecyclerView course_for_you_rc;
    ArrayList<courseHomeModel> cmodel,searchModel;
  //  ArrayList<trendingModel> rcsd,searchModels;
    courseHomeAdapter cAdapter;
  //  courseTrendingAdapter  ads;
    private DatabaseReference usrref;
    private EditText search;
    private TextView usrnamel;
    FirebaseDatabase dat=FirebaseDatabase.getInstance();
    DatabaseReference mr=dat.getReference();
    private FirebaseAuth usrauth;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v=inflater.inflate(R.layout.fragment_home, container, false);
        popular_rc=v.findViewById(R.id.popular_recycler);
       // course_for_you_rc=v.findViewById(R.id.most_course_rc);
        usrref=  FirebaseDatabase.getInstance().getReference();
        usrauth= FirebaseAuth.getInstance();
        cmodel=new ArrayList<>();
        usrnamel=v.findViewById(R.id.usernamecourse);
        search=v.findViewById(R.id.editTextTextPersonName);
        //rcsd=new ArrayList<>();
        searchModel=new ArrayList<>();
        //searchModels=new ArrayList<>();

        mr.child("users").child(usrauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String username=snapshot.child("username").getValue().toString();
                usrnamel.setText("Hello , "+username);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


      //  LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false );
        //course_for_you_rc.setLayoutManager(layoutManager);
      //  course_for_you_rc.setItemAnimator(new DefaultItemAnimator());

      //  ads= new courseTrendingAdapter(rcsd,getContext());
      //  course_for_you_rc.setAdapter(ads);
        // course_for_you_rc.setAdapter(cAdapter);
        StaggeredGridLayoutManager gl=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false );
        popular_rc.setLayoutManager(gl);
        popular_rc.setItemAnimator(new DefaultItemAnimator());

        cAdapter= new courseHomeAdapter(cmodel,getContext());

        popular_rc.setAdapter(cAdapter);




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

                    cAdapter= new courseHomeAdapter(cmodel,getContext());
                    popular_rc.setAdapter(cAdapter);
                    cAdapter.notifyDataSetChanged();


                }
                else
                {
                    filter(s.toString());
                }
            }

        });



        loadcourse();
        //loadTrending();


        return v;

    }
   /* private void loadTrending()
    {

        usrref.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds1: snapshot.getChildren())
                {
                    String courseName=ds1.child("name").getValue().toString();
                    String url=ds1.child("url").getValue().toString();
                    String desc=ds1.child("courseDesc").getValue().toString();
                    for(DataSnapshot ds: ds1.child("videos").getChildren())
                    {


                    }
                    ds1.child("videos").getChildrenCount();
                    rcsd.add((new trendingModel(String.valueOf(ds1.child("videos").getChildrenCount()),courseName , url,ds1.getKey(),desc)));
                    ads.notifyDataSetChanged();

                  *//*  Integer[] langcover={R.drawable.theme2,R.drawable.theme4,R.drawable.theme5,R.drawable.theme7};
                    for(int i=0;i<langcover.length;i++)
                    {


                    }*//*
                   *//* rcsd.add((new trendingModel(String.valueOf(ds1.child("videos").getChildrenCount()),courseName , url,ds1.getKey(),desc)));
                    ads.notifyDataSetChanged();*//*
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }*/
    private void loadcourse()
    {


        usrref.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds1: snapshot.getChildren())
                {
                    String courseName=ds1.child("name").getValue().toString();
                    String url=ds1.child("url").getValue().toString();
                    for(DataSnapshot ds: ds1.child("videos").getChildren())
                    {


                    }
                    ds1.child("videos").getChildrenCount();
                    Log.d("countvid",String.valueOf(ds1.child("videos").getChildrenCount()));
                    cmodel.add((new courseHomeModel(String.valueOf(ds1.child("videos").getChildrenCount()),courseName , url,ds1.getKey())));
                    cAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void filter(String text)
    {
        for(courseHomeModel ps:cmodel)
        {
            if(ps.getLangName().equals(text))
            {
                searchModel.add(ps);
            }
        }

        cAdapter= new courseHomeAdapter(searchModel,getContext());
        popular_rc.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

}