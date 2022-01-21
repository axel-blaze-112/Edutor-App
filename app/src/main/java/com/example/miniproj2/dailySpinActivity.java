package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproj2.Models.userModel;
import com.example.miniproj2.Spin.SpinItem;
import com.example.miniproj2.Spin.WheelView;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class dailySpinActivity extends AppCompatActivity {
private Button playbtn,checkinBtn;
private WheelView wheelView;
List<SpinItem> spinItemList=new ArrayList<>();
private FirebaseUser usr;
private FirebaseAuth useraut;
DatabaseReference reference;
private String currentuserid;
TextView coins;
private DatabaseReference myref;
int currentSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_spin2);

        playbtn=findViewById(R.id.playBtn);
        wheelView=findViewById(R.id.wheel_VIew);
        coins=findViewById(R.id.coinsTv);
        checkinBtn=findViewById(R.id.CheckInbtn);

        myref=  FirebaseDatabase.getInstance().getReference();
        useraut= FirebaseAuth.getInstance();









        FirebaseAuth auth =FirebaseAuth.getInstance();
        usr=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("users");


        checkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyCheck();
            }
        });

        loadData();
        spinlist();
        clickListener();




    }

    private void spinlist(){
    SpinItem item1=new SpinItem();
    item1.text="0";
    item1.color=0xffFFF3E0;
    spinItemList.add(item1);

    SpinItem item2=new SpinItem();
    item2.text="3";
    item2.color=0xffFFE0B2;
    spinItemList.add(item2);


    SpinItem item3=new SpinItem();
    item3.text="5";
    item3.color=0xffFFCC80;
    spinItemList.add(item3);


    SpinItem item4=new SpinItem();
    item4.text="4";
    item4.color=0xffFFF3E0;
    spinItemList.add(item4);


    SpinItem item5=new SpinItem();
    item5.text="8";
    item5.color=0xffFFF3E0;
    spinItemList.add(item5);


    SpinItem item6=new SpinItem();
    item6.text="10";
    item6.color=0xffFFE0B2;
    spinItemList.add(item6);

    SpinItem item7=new SpinItem();
    item7.text="2";
    item7.color=0xffFFCC80;
    spinItemList.add(item7);

    SpinItem item8=new SpinItem();
    item8.text="1";
    item8.color=0xffFFF3E0;
    spinItemList.add(item8);

    SpinItem item9=new SpinItem();
    item9.text="6";
    item9.color=0xffFFE0B2;
    spinItemList.add(item9);


    SpinItem item10=new SpinItem();
    item10.text="8";
    item10.color=0xffFFF3E0;
    spinItemList.add(item10);

        SpinItem item11=new SpinItem();
        item11.text="5";
        item11.color=0xffFFF3E0;
        spinItemList.add(item11);

        SpinItem item12=new SpinItem();
        item12.text="15";
        item12.color=0xffFFF3E0;
        spinItemList.add(item12);

        wheelView.setData(spinItemList);
        wheelView.setRound(getRandCircleRound());

        wheelView.LuckRoundItemSelectedListener(new WheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                playbtn.setEnabled(true);

                playbtn.setAlpha(1f);
                String value=spinItemList.get(index -1 ).text;

                updateDateFirebase(Integer.parseInt(value));

            }
        });


    }

    public void clickListener()
    {
        int index=getRandomIndex();
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSpin==1)
                {
                    wheelView.startWheelWIthTargetIndex(index);
                    Toast.makeText(dailySpinActivity.this,"Daily Spin",Toast.LENGTH_SHORT).show();
                }
                else if(currentSpin==0)
                {
                    playbtn.setEnabled(false);
                    playbtn.setAlpha(.6f);
                    Toast.makeText(dailySpinActivity.this,"Daily Spin over",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    playbtn.setEnabled(false);
                    playbtn.setAlpha(.6f);
                    wheelView.startWheelWIthTargetIndex(index);

                }


            }
        });
    }

    private int getRandomIndex()
    {
        int [] index=new int[]{1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,7,7,9,9,10,11,12};

        int random=new Random().nextInt(index.length);


        return index[random];
    }

    private int getRandCircleRound()
    {
        Random random=new Random();
        return random.nextInt(10)+15;

    }

    private void loadData()
    {
        reference.child(usr.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        userModel m=snapshot.getValue(userModel.class);
                        coins.setText(String.valueOf(m.getCoins()));
                        currentSpin=m.getSpins();

                        String currentSpins="Spin the Wheel" +String.valueOf(m.getSpins());
                        playbtn.setText(currentSpins);


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(dailySpinActivity.this,"Error: " +error.getDetails(),Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void updateDateFirebase(int reward )
    {

        int currentCoins=Integer.parseInt(coins.getText().toString());
        int updateCoins=currentCoins+reward;
        int updatedSpins=0;

        HashMap<String ,Object> coinsH=new HashMap<>();
        coinsH.put("coins",updateCoins);
        coinsH.put("spins",updatedSpins);

        reference.child(usr.getUid())
                .updateChildren(coinsH)
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                    }
                });


    }

    private void dailyCheck()
    {
        final SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitle("Please Wait...");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();



        final Date currentDate= Calendar.getInstance().getTime();
        final SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);




        DatabaseReference ref22=FirebaseDatabase.getInstance().getReference().child("DailySpins");
        currentuserid=useraut.getCurrentUser().getUid();

        ref22.child(useraut.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String dbDateString=snapshot.child("date").getValue(String.class);
                    try
                    {
                        assert dbDateString != null;

                        Date dbDate=dateFormat.parse(dbDateString);

                        String xDate=dateFormat.format(currentDate);
                        Date date=dateFormat.parse(xDate);

                        if(currentDate.after(dbDate )&& date.compareTo(dbDate)!=0)
                        {
                            DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("users");
                            ref1.child(useraut.getCurrentUser().getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            userModel model=snapshot.getValue(userModel.class);
                                            int currentSpins=Integer.parseInt(snapshot.child("spins").getValue().toString());
                                            int update = currentSpins+1;

                                            HashMap<String,Object> map=new HashMap<>();
                                            map.put("spins",update);

                                            ref1.child(useraut.getCurrentUser().getUid()).updateChildren(map);

                                            Date newDate=Calendar.getInstance().getTime();
                                            String newDateString=dateFormat.format(newDate);

                                            HashMap<String , String> datemap=new HashMap<>();
                                            datemap.put("date", newDateString);

                                            ref22.child(useraut.getCurrentUser().getUid()).setValue(datemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                    sweetAlertDialog.changeAlertType(sweetAlertDialog.SUCCESS_TYPE);
                                                    sweetAlertDialog.setTitleText("Success");
                                                    sweetAlertDialog.setContentText("Spin Added to your Account Successfully");
                                                    sweetAlertDialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    sweetAlertDialog.dismissWithAnimation();
                                                        }
                                                    }).show();
                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                Toast.makeText(dailySpinActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else
                        {
                            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitle("Failed");
                            sweetAlertDialog.setContentText("You Have Already rewarded , come back tommarrow");
                            sweetAlertDialog.setConfirmButton("OK",null);
                            sweetAlertDialog.show();
                        }


                    }
                    catch (ParseException e)
                    {
                     sweetAlertDialog.dismissWithAnimation();
                    }
                }
                else
                {
                    sweetAlertDialog.changeAlertType(sweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("System busy");
                    sweetAlertDialog.setContentText("System is busy, Please try again later!!");
                    sweetAlertDialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                        }
                    });
                    sweetAlertDialog.show();

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
        Intent i=new Intent(dailySpinActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}