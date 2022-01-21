package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.miniproj2.Models.questionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private TextView question,noIndicator;
    private LinearLayout optionContainer;
    private Button sharebtn,nextbtn;
    private String category;
    private String setId;
    String key;
    List<questionModel>  list;
    int postion;
    int score=0;
    FirebaseDatabase dat=FirebaseDatabase.getInstance();
    DatabaseReference mr=dat.getReference();
    private Dialog loading;
private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questions");

        question=findViewById(R.id.quest);
      nextbtn=findViewById(R.id.next);
      optionContainer=findViewById(R.id.optioncontainer);
        noIndicator=findViewById(R.id.questindi);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loading);
        loading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);
       // playanim(question,0,list.get(postion).getQuestion());

         category=getIntent().getStringExtra("category");
        setId=getIntent().getStringExtra("setId");

        key=getIntent().getStringExtra("key");
        Log.d("infoQuest",key.toString());
        loading.show();
        list=new ArrayList<>();
        mr.child("SETS").child(setId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {

                    String id=ds.child("id").getValue().toString();;
                    String quesiton=ds.child("question").getValue().toString();
                    String a=ds.child("optionA").getValue().toString();
                    String b=ds.child("optionB").getValue().toString();
                    String c=ds.child("optionC").getValue().toString();
                    String d=ds.child("optionD").getValue().toString();
                    String correctAns=ds.child("correctAns").getValue().toString();
                    list.add(new questionModel(id,quesiton,a,b,c,d,correctAns,setId));
                /*    Log.d("check", id.toString());
                    Log.d("check", quesiton.toString());
                    Log.d("check", a.toString());
                    Log.d("check", b.toString());
                    Log.d("check", c.toString());
                    Log.d("check", d.toString());
                    Log.d("check", correctAns.toString());
*/
                }
                if(list.size()>0)
                {
                    for(int i=0;i<4;i++)
                    {
                        optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }
                    playanim(question,0,list.get(postion).getQuestion());
                    nextbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextbtn.setEnabled(false);
                            nextbtn.setAlpha(0.7f);
                            enableOption(true);
                            postion++;
                            if(postion==list.size())
                            {
                                Intent i=new Intent(QuestionsActivity.this,scoreActivity.class);
                                i.putExtra("scored",score);
                                i.putExtra("total",list.size());
                                i.putExtra("setId",setId);
                                i.putExtra("key",key);
                                startActivity(i);
                                finish();
                                return;
                            }
                            count=0;
                            playanim(question,0,list.get(postion).getQuestion());
                        }
                    });
                }
                else
                {
                    finish();
                    Toast.makeText(QuestionsActivity.this,"no questiosn",Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
            loading.dismiss();
            finish();
            }
        });





    }


    private void playanim(View view , int value ,String data)
    {
        for(int i=0;i<4;i++)
        {
            optionContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
        }
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if(value==0 && count<4)
                {
                    String opt="";
                    if(count==0)
                    {
                        opt=list.get(postion).getA();
                    }
                    else if(count==1)
                    {
                        opt=list.get(postion).getB();
                    }
                    else if(count==2)
                    {
                        opt=list.get(postion).getC();
                    }
                    else if(count==3)
                    {
                        opt=list.get(postion).getD();
                    }
                    playanim(optionContainer.getChildAt(count),0,opt);
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value==0)
                {
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText(postion+1+"/"+list.size());
                    }
                    catch (ClassCastException e)
                    {
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playanim(view,1,data);
                }
               /* else
                {
                    enableOption(true);
                }*/

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button selectOption)
    {
        enableOption(false);
        nextbtn.setEnabled(true);
        nextbtn.setAlpha(1);
        if(selectOption.getText().toString().equals(list.get(postion).getAnswer()))
        {
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }
        else {
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button br = (Button) optionContainer.findViewWithTag(list.get(postion).getAnswer());
           // br.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }
    private void enableOption(boolean enable)
    {
        for(int i=0;i<4;i++)
        {
            optionContainer.getChildAt(i).setEnabled(enable);
            if(enable)
            {
                optionContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            }
        }
    }
}