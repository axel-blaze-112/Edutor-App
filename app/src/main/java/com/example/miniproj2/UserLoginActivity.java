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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginActivity extends AppCompatActivity {
    private FirebaseUser currentuser;
    private TextView forgetpass;
    private TextInputEditText useremail,userpassword;
    private Button userlogin,donthaveaccount;
    private ProgressDialog lgnprogress;
    private FirebaseAuth usrauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        userlogin = findViewById(R.id.btlogin);
        useremail = findViewById(R.id.lemail);
        userpassword = findViewById(R.id.lpass);
        donthaveaccount= findViewById(R.id.btsignup);
        forgetpass= findViewById(R.id.forgetpassss);
        lgnprogress = new ProgressDialog(this);
        usrauth=FirebaseAuth.getInstance();
        currentuser=usrauth.getCurrentUser();

        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UserLoginActivity.this,UserSignUpActivity.class);
                startActivity(intent);
            }
        });
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(UserLoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });



    }
    private void login(){
        String UserEmail=useremail.getText().toString();
        String UserPass=userpassword.getText().toString();
        if(TextUtils.isEmpty(UserEmail) && TextUtils.isEmpty(UserPass)){
            Toast.makeText(UserLoginActivity.this,"Please Enter the Required Credentials",Toast.LENGTH_LONG).show();
        }
        else{
            lgnprogress.setTitle("Signing In");
            lgnprogress.setMessage("Please Wait...");
            lgnprogress.setCanceledOnTouchOutside(true);
            lgnprogress.show();
            usrauth.signInWithEmailAndPassword(UserEmail,UserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        lgnprogress.dismiss();
                        Toast.makeText(UserLoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                        sendUserToMainActivity();
                        /*   Intent intent= new Intent(userlogin.this,MainActivity.class);
                        startActivity(intent);*/
                    }
                    else{
                        String errormess=task.getException().toString();
                        Toast.makeText(UserLoginActivity.this,"Error :"+ errormess,Toast.LENGTH_LONG).show();
                        lgnprogress.dismiss();
                    }
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser != null) {
            sendUserToMainActivity();
        }
    }
    private void sendUserToMainActivity() {
        Intent mainintent= new Intent(UserLoginActivity.this,MainActivity.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }
}