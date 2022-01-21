package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UserSignUpActivity extends AppCompatActivity {
    private String forumId="1618052034192";
    private FirebaseUser currentuser;
    private TextView alreadyhaveaccount;
    private TextInputEditText useremail,userpassword,username,PhoneNo;
    private Button userregister;
    private ProgressDialog lgnprogress;
    private FirebaseAuth usrauth;
    private CardView registercard;
    private FirebaseAuth UserAuth;
    private FirebaseAuth useraut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        PhoneNo=findViewById(R.id.phNum);
        userpassword=findViewById(R.id.uspass);
        useremail= findViewById(R.id.usemail);
        username =findViewById(R.id.usn);
        lgnprogress = new ProgressDialog(this);
        userregister=findViewById(R.id.btSubmit);
        alreadyhaveaccount=findViewById(R.id.already_have_account);
        useraut= FirebaseAuth.getInstance();




        UserAuth=FirebaseAuth.getInstance();
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendusertoMainActivity();

            }
        });
        userregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount(){
        String Username=username.getText().toString().trim();
        String UserRegEmail=useremail.getText().toString().trim();
        String phoneNo= PhoneNo.getText().toString().trim();
        String UserRegPass=userpassword.getText().toString().trim();

        if(TextUtils.isEmpty(UserRegEmail) && TextUtils.isEmpty(UserRegPass) && TextUtils.isEmpty(Username) && TextUtils.isEmpty(phoneNo)){
            Toast.makeText(UserSignUpActivity.this,"Please Enter the Required Credentials",Toast.LENGTH_LONG).show();
        }
        else {
            lgnprogress.setTitle("Creating New Account");
            lgnprogress.setMessage("Please Wait While Account Is Being Created...");
            lgnprogress.setCanceledOnTouchOutside(true);
            lgnprogress.show();
            UserAuth.createUserWithEmailAndPassword(UserRegEmail, UserRegPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentuserid = UserAuth.getCurrentUser().getUid();

                        HashMap<String,Object> hash=new HashMap<>();
                        hash.put("username",Username);
                        hash.put("useremail",UserRegEmail);
                        hash.put("userphonno",phoneNo);
                        hash.put("userpass",UserRegPass);
                        hash.put("coins",100);
                        hash.put("spins",3);
                        hash.put("uid",UserAuth.getCurrentUser().getUid());
                        hash.put("image","https://firebasestorage.googleapis.com/v0/b/miniproj2-cddb9.appspot.com/o/adminicon.png?alt=media&token=cf00b866-bf32-4e8b-acbe-af91f56d0848");
                        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference();
                        dbref.child("users").child(currentuserid).setValue(hash);
                        Toast.makeText(UserSignUpActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();

                        String timestamp=""+System.currentTimeMillis();

                        HashMap<String,Object> forum=new HashMap<>();
                        forum.put("username",Username);
                        forum.put("uid",currentuserid);
                        forum.put("timestamp",timestamp);

                        DatabaseReference forumdb=FirebaseDatabase.getInstance().getReference();
                        forumdb.child("forum").child(forumId).child("participants").child(currentuserid).setValue(forum);

                        Date date= Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                        Calendar calendar=Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_MONTH,-1);
                        Date previousDate=calendar.getTime();
                        String dateString=dateFormat.format(previousDate);
                        FirebaseDatabase.getInstance().getReference().child("DailySpins")
                                .child(useraut.getCurrentUser().getUid())
                                .child("date")
                                .setValue(dateString);


                        lgnprogress.dismiss();
                        sendusertoMainActivity();
                    } else {
                        String errormess = task.getException().toString();
                        Toast.makeText(UserSignUpActivity.this, "Error :" + errormess, Toast.LENGTH_LONG).show();
                        lgnprogress.dismiss();
                    }
                }
            });


    }

    }
    private void sendusertoMainActivity(){
        Intent mainintent= new Intent(UserSignUpActivity.this,MainActivity.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }
}