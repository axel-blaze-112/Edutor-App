package com.example.miniproj2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproj2.Adapters.categoryAdapter;
import com.example.miniproj2.Adapters.courseTrendingAdapter;
import com.example.miniproj2.Adapters.homeTrendinAdapter;
import com.example.miniproj2.Models.categoryModel;
import com.example.miniproj2.Models.trendingModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private FirebaseAuth useraut,usr;
    private String currentuserid;
    private DatabaseReference myref,daily;
    private RecyclerView curenrolledcoursesRc,trendc;
    private int spincount;
    private DatabaseReference usrref;
    ArrayList<trendingModel> rcsd;
    courseTrendingAdapter ads;
    private EditText search;

    private categoryAdapter adap;
    public static List<categoryModel> list;
    FirebaseDatabase dat=FirebaseDatabase.getInstance();
    DatabaseReference mr=dat.getReference();
    private FirebaseAuth usrauth;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useraut= FirebaseAuth.getInstance();
        FirebaseUser currentusers=useraut.getCurrentUser();
        if(currentusers==null){
            sendUserToLoginActivity();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home2, container, false);
        toolbar=v.findViewById(R.id.toolbar);
        drawerLayout=v.findViewById(R.id.nav_drawer);
        navigationView=v.findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        usrauth=FirebaseAuth.getInstance();
        curenrolledcoursesRc=v.findViewById(R.id.curenrolledcoursesRc);
        usrref=  FirebaseDatabase.getInstance().getReference();
        trendc=v.findViewById(R.id.trendc);

        list=new ArrayList<>();
        homeTrendinAdapter adapter=new homeTrendinAdapter(list);
        curenrolledcoursesRc.setAdapter(adapter);
        mr.child("Enrolled").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                if(snapshot.hasChild(usrauth.getCurrentUser().getUid()))
                {
                    mr.child("Enrolled").child(usrauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            for(DataSnapshot ds2:snapshot.getChildren())
                            {
                                String id=ds2.child("enrolled").getValue().toString();
                                Log.d("enrolid",id.toString());
                                mr.child("Categories").child(id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot catsnapshot) {

                                        List<String> setss=new ArrayList<>();
                                        for(DataSnapshot ds:catsnapshot.child("set").getChildren())
                                        {
                                            setss.add(ds.getKey());
                                            String s=ds.child("set").getKey().toString();
                                            //Toast.makeText(getContext(),""+s,Toast.LENGTH_SHORT).show();
                                        }
                                        Log.d("keyinfocate",catsnapshot.getKey().toString());
                                        list.add(new categoryModel(catsnapshot.child("url").getValue().toString(),catsnapshot.child("name").getValue().toString(),
                                                setss,id,catsnapshot.child("courseDesc").getValue().toString()));
                                        /*      loading.dismiss();*/
                                        adapter.notifyDataSetChanged();
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                   /* loading.dismiss();
                                    finish();*/
                                    }
                                });
                            }
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



        rcsd=new ArrayList<>();
        ads= new courseTrendingAdapter(rcsd,getContext());
        trendc.setAdapter(ads);
        loadTrending();

        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        useraut= FirebaseAuth.getInstance();
        currentuserid=useraut.getCurrentUser().getUid();

        myref=  FirebaseDatabase.getInstance().getReference();
        daily=  FirebaseDatabase.getInstance().getReference();


        myref.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                String username=snapshot.child("username").getValue().toString();
                String coins=snapshot.child("coins").getValue().toString();
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = (TextView) headerView.findViewById(R.id.email_id_of_user);
                String CurrentusrEmail = useraut.getInstance().getCurrentUser().getEmail().toString();
                navUsername.setText(CurrentusrEmail);
                TextView userName=(TextView) headerView.findViewById(R.id.name_of_user);
                userName.setText(username);
                TextView userCoins=(TextView) headerView.findViewById(R.id.user_coins);
                userCoins.setText(coins);

                if(snapshot.hasChild("image"))
                {
                    String usrnImg=snapshot.child("image").getValue().toString();
                    CircleImageView userimage=(CircleImageView) headerView.findViewById(R.id.Icon);
                    Picasso.get().load(usrnImg).into(userimage);
                }
                else
                {
                    CircleImageView userimage=(CircleImageView) headerView.findViewById(R.id.Icon);
                    Picasso.get().load(R.drawable.adminicon).into(userimage);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.side_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true ;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        if(item.getItemId()==R.id.forum)
        {
            sendUserToForum();
        }
        if(item.getItemId()==R.id.Redeem)
        {
            sendUserToMyRedeem();
        }
        if(item.getItemId()==R.id. myCourses){
            sendUserTomycourse();
        }
        if(item.getItemId()==R.id.settings){
            sendUserToSettingActivity();
        }
       /* if(item.getItemId()==R.id.contacts){

        }*/
        if(item.getItemId()==R.id.dailySpins){
            sendUserToDailySpins();

        }
        if(item.getItemId()==R.id.logout)
        {
            AlertDialog.Builder aler=new AlertDialog.Builder(getContext());
            aler.setTitle("Do you Want to Logout");
            aler.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    useraut.signOut();
                    sendUserToLoginActivity();
                    getActivity().finish();
                }
            });
            aler.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            aler.show();
        }
        return false;
    }
    private void sendUserToForum() {
        Intent intent= new  Intent(getContext(),forumChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    private void sendUserToLoginActivity() {
        Intent intent = new Intent(getContext(),UserLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    private void sendUserTomycourse() {
        Intent intent = new Intent(getContext(),CategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void sendUserToSettingActivity() {
        Intent intent = new Intent(getContext(),EditProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();

    }
    private void sendUserToDailySpins() {
        Intent intent = new Intent(getContext(),dailySpinActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }
    private void sendUserToSetCate() {
        Intent intent = new Intent(getContext(),CategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    private void sendUserToMyRedeem() {
        Intent intent = new Intent(getContext(),MyRedeemHistory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }
    private void loadTrending()
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
                    rcsd.add((new trendingModel(String.valueOf(ds1.child("videos").getChildrenCount()),courseName ,url,ds1.getKey(),desc)));
                    ads.notifyDataSetChanged();
                   /* Integer[] langcover={R.drawable.theme1,R.drawable.theme2,R.drawable.theme3,R.drawable.theme4,R.drawable.theme5,R.drawable.theme6,R.drawable.theme7,R.drawable.theme8};
                    for(int i=0;i<langcover.length;i++)
                    {


                    }*/




                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}