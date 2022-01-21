package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproj2.Adapters.courseLessonsAdapter;
import com.example.miniproj2.Models.courseLessonsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class courseDetailsActivity extends AppCompatActivity {
    private RecyclerView rc;
    private FirebaseUser currentuser;
    private courseLessonsAdapter lessonAdapter;
    private List<courseLessonsModel> lm;
    private courseLessonsModel lessonModel;

    private FirebaseDatabase fb;
    private DatabaseReference dr;
    private Button enroll_button,ViewTest;
    private FirebaseAuth usrauth;
    private TextView courseNameselected,coursedescSelected,enrolltocheckoutless,noVideosYet;
    private ImageView course_imgage_selected,backselectedCourse;
   private DatabaseReference ref;

private String key,title;
private String enrollbtn="";
//private List<String> sets;
//private String setID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        ref=FirebaseDatabase.getInstance().getReference();
        rc=findViewById(R.id.lesson_rc);
        rc.setHasFixedSize(true);
        LinearLayoutManager ln=new LinearLayoutManager(this);
        rc.setLayoutManager(ln);
        usrauth=FirebaseAuth.getInstance();
        course_imgage_selected=findViewById(R.id.course_imgage_selected);
        backselectedCourse=findViewById(R.id.backselectedCourse);
        courseNameselected=findViewById(R.id.courseNameselected);
        coursedescSelected=findViewById(R.id.coursedescSelected);
        enrolltocheckoutless=findViewById(R.id.enrolltocheckoutless);
        noVideosYet=findViewById(R.id.noVideosYet);
        //ViewTest=findViewById(R.id.ViewTest);

        key=getIntent().getStringExtra("key");
       // sets=CategoryActivity.list.get(getIntent().getIntExtra("position",0)).getSet();
//        title=getIntent().getStringExtra("title").toString();
       // setID=CategoryActivity.list.get(getIntent().getIntExtra("position",0)).getSet();

        Log.d("key",key.toString());
        lm=new ArrayList<>();
        lessonAdapter=new courseLessonsAdapter(courseDetailsActivity.this,lm);
        rc.setAdapter(lessonAdapter);
        ref.child("Categories").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                if(snapshot.hasChild("enrolledStudents"))
                {
                    for(DataSnapshot ds:snapshot.child("enrolledStudents").getChildren())
                    {
                        if(ds.getKey().equals(usrauth.getCurrentUser().getUid()))
                        {
                            enrollbtn="Already Enrolled";
                            enrolltocheckoutless.setVisibility(View.GONE);
                            enroll_button.setText(enrollbtn);

                        }
                       // else
                      //  {
                            if(snapshot.hasChild("videos"))
                            {
                                if(enrollbtn.equals("Enroll"))
                                {
                                    enrollbtn="Enroll";
                                    enrolltocheckoutless.setVisibility(View.VISIBLE);
                                    enroll_button.setText(enrollbtn);
                                    noVideosYet.setVisibility(View.GONE);
                                    rc.setVisibility(View.GONE);
                                }
                                else if(enrollbtn.equals("Already Enrolled"))
                                {

                                    rc.setVisibility(View.VISIBLE);

                                }

                            }
                            else
                            {
                                noVideosYet.setVisibility(View.VISIBLE);
                            }
                       // }
                    }
                }
                else
                {
                    if(enrollbtn.equals("Already Enrolled"))
                    {
                        noVideosYet.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        noVideosYet.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        enroll_button=findViewById(R.id.enroll_button);
        enroll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("Categories").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
                    {
                        if(snapshot.hasChild("enrolledStudents"))
                        {
                            for(DataSnapshot ds:snapshot.child("enrolledStudents").getChildren())
                            {
                               if(ds.child("uid").equals(usrauth.getCurrentUser().getUid()))
                               {
                                   enrollbtn="Already Enrolled";
                                   enrolltocheckoutless.setVisibility(View.GONE);
                                   enroll_button.setText(enrollbtn);

                               }
                               else
                               {
                                   enrollbtn="Enroll";
                                   enrolltocheckoutless.setVisibility(View.VISIBLE);
                                   enroll_button.setText(enrollbtn);
                                   enroll();
                               }
                            }
                        }
                        else
                        {
                            enrollbtn="Enroll";
                            enrolltocheckoutless.setVisibility(View.VISIBLE);
                            enroll_button.setText(enrollbtn);
                            enroll();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }
        });

        ref.child("Categories").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                lm.clear();

                    String name=snapshot.child("name").getValue().toString();
                    String courseDesc=snapshot.child("courseDesc").getValue().toString();
                    String courseImg=snapshot.child("url").getValue().toString();


                courseNameselected.setText("Course Name : "+name);
                coursedescSelected.setText("Description : "+courseDesc);
                Picasso.get().load(courseImg).into(course_imgage_selected);

                for(DataSnapshot ds:snapshot.child("videos").getChildren())
                {
                        String vidname = ds.child("search").getValue().toString();
                        String search = ds.child("topicName").getValue().toString();
                        String url = ds.child("videourl").getValue().toString();
                        for(DataSnapshot ds1:ds.getChildren())
                        {

                        }
                        lm.add(new courseLessonsModel(vidname,url,search,ds.getKey()));
                }
                lessonAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      /*  ViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(courseDetailsActivity.this,setActivity.class);
                i.putExtra("title",title);
                i.putExtra("position",sets);
                i.putExtra("key",key);
                startActivity(i);
            }
        });*/
    }
    private void enroll()
    {
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("uid",usrauth.getCurrentUser().getUid());
        ref.child("Categories").child(key).child("enrolledStudents").child(usrauth.getCurrentUser().getUid()).setValue(hash);

        HashMap<String,Object> hashs=new HashMap<>();
        hashs.put("enrolled",key);
         ref.child("Enrolled").child(usrauth.getCurrentUser().getUid()).child(key).setValue(hashs);

        ref.child("Categories").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                if(snapshot.hasChild("enrolledStudents"))
                {
                    for(DataSnapshot ds:snapshot.child("enrolledStudents").getChildren())
                    {
                        if(ds.getKey().equals(usrauth.getCurrentUser().getUid()))
                        {
                            enrollbtn="Already Enrolled";
                            enrolltocheckoutless.setVisibility(View.GONE);
                            enroll_button.setText(enrollbtn);

                        }
                        else
                        {
                            enrollbtn="Enroll";
                            enrolltocheckoutless.setVisibility(View.VISIBLE);
                            enroll_button.setText(enrollbtn);
                        }

                    }
                }
                if(snapshot.hasChild("videos"))
                {
                    noVideosYet.setVisibility(View.GONE);
                    rc.setVisibility(View.VISIBLE);
                }
                else
                {
                    noVideosYet.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(courseDetailsActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}