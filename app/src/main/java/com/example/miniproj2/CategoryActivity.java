package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.miniproj2.Adapters.categoryAdapter;
import com.example.miniproj2.Models.categoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
private RecyclerView cate_rc;
  public static  List<categoryModel> list;
    private categoryAdapter adap;
    FirebaseDatabase dat=FirebaseDatabase.getInstance();
    DatabaseReference mr=dat.getReference();
private Dialog loading;
private TextView notenrolled;
    private FirebaseAuth usrauth;
    private Toolbar vr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        cate_rc=findViewById(R.id.cate_rc);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cate_rc.setLayoutManager(layoutManager);
        usrauth=FirebaseAuth.getInstance();
        notenrolled=findViewById(R.id.notenrolled);
        vr=findViewById(R.id.enrolled_toolbar);
        setSupportActionBar(vr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Enrolled Courses");

        loading=new Dialog(this);
        loading.setContentView(R.layout.loading);
        loading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);


        list=new ArrayList<>();
        categoryAdapter adapter=new categoryAdapter(list);
        cate_rc.setAdapter(adapter);

        mr.child("Enrolled").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {loading.show();
                    if(snapshot.hasChild(usrauth.getCurrentUser().getUid()))
                    {
                        notenrolled.setVisibility(View.GONE);
                        mr.child("Enrolled").child(usrauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot ds2:snapshot.getChildren())
                                {
                                    String id=ds2.child("enrolled").getValue().toString();
                                    Log.d("enrolid",id.toString());
                                    mr.child("Categories").child(id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            List<String> setss=new ArrayList<>();
                                            for(DataSnapshot ds:snapshot.child("set").getChildren())
                                            {
                                                setss.add(ds.getKey());
                                                String s=ds.child("set").getKey().toString();

                                               // Toast.makeText(CategoryActivity.this,""+s,Toast.LENGTH_SHORT).show();
                                            }
                                            Log.d("keyinfocate",snapshot.getKey().toString());
                                            list.add(new categoryModel(snapshot.child("url").getValue().toString(),snapshot.child("name").getValue().toString(),
                                                    setss,id,snapshot.child("courseDesc").getValue().toString()));
                                            adapter.notifyDataSetChanged();
                                            loading.dismiss();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            loading.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                    }
                    else
                    {
                        loading.dismiss();
                        notenrolled.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    loading.dismiss();
                    notenrolled.setVisibility(View.VISIBLE);
                }


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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}