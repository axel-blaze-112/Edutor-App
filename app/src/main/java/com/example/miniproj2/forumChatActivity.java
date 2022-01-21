package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproj2.Adapters.forumChatMessageAdapter;
import com.example.miniproj2.Models.forumChatMessages;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class forumChatActivity extends AppCompatActivity {
    private String forumId="1618052034192";
    private Toolbar toolbar;
    private ImageButton attach,sendMessage;
    private EditText msgTyping;
    private FirebaseAuth fireAuth;
    private RecyclerView chat_recycler;

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=400;
    private static final int IMAGE_PICK_GALLERY_CODE=1000;
    private static final int IMAGE_PICK_CAMERA_CODE=2000;
    private String[] cameraPermission;
    private String[] storagePermission;
    private Uri imageUri= null;
    private ArrayList<forumChatMessages> ForumChatMsg;
    private forumChatMessageAdapter ForumMsgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_chat);
       toolbar=findViewById(R.id.group_chat_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowCustomEnabled(true);
        cameraPermission=new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        storagePermission=new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        msgTyping=findViewById(R.id.message_typing);

        sendMessage=findViewById(R.id.send_msg);
        chat_recycler=findViewById(R.id.group_chat_recycler);

        fireAuth=FirebaseAuth.getInstance();


        attach=findViewById(R.id.attachment);
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(forumChatActivity.this,"Please Type Something..", Toast.LENGTH_SHORT).show();
                showImageImportDialog();
            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=msgTyping.getText().toString().trim();
                if(TextUtils.isEmpty(msg))
                {
                    Toast.makeText(forumChatActivity.this,"Please Type Something..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendMsg(msg);
                }
            }
        });

        loadGroupChatMsgs();

    }



    private void showImageImportDialog()
    {
        String[] options={"Camera","Gallery"};
        AlertDialog.Builder build=new AlertDialog.Builder(forumChatActivity.this);
        build.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which==0)
                {
                    if(!checkCameraPermission())
                    {
                        requestCameraPermission();
                    }
                    else
                    {
                        pickCamera();
                    }
                }
                else
                {
                    if(!checkStoragePermission())
                    {
                        requestStoragePermission();
                    }
                    else
                    {
                        pickGallery();
                    }

                }
            }
        }).show();
    }
    private void pickGallery()
    {
        Intent in=new Intent(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in,IMAGE_PICK_GALLERY_CODE);
    }
    private void pickCamera()
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"ForumImageTitle");
        contentValues.put(MediaStore.Images.Media.TITLE,"ForumImageDescription");
        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        in.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(in,IMAGE_PICK_CAMERA_CODE);
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }
    private boolean checkStoragePermission()
    {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }
    private boolean checkCameraPermission()
    {
        boolean result=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result && result1;
    }
    private void sendImageMessage()
    {
        ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Please Wait");
        pd.setMessage("Sending Image...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String filenamePath="forum_chat_images/"+"image"+ System.currentTimeMillis();
        StorageReference ref= FirebaseStorage.getInstance().getReference(filenamePath);
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        Task<Uri> pUriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!pUriTask.isSuccessful());
                        Uri pDownloadUri=pUriTask.getResult();
                        if(pUriTask.isSuccessful())
                        {
                            String timestamp=""+System.currentTimeMillis();
                            HashMap<String,Object> msgMap=new HashMap<>();
                            msgMap.put("sender",""+fireAuth.getUid());
                            msgMap.put("message",""+pDownloadUri);
                            msgMap.put("timestamp",""+timestamp);
                            msgMap.put("type",""+"image");

                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("forum");
                            ref.child(forumId).child("Messages").child(timestamp).setValue(msgMap).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    msgTyping.setText("");
                                    pd.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    pd.dismiss();
                                    Toast.makeText(forumChatActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
    }
    private void loadGroupChatMsgs()
    {

        ForumChatMsg=new ArrayList<>();
        DatabaseReference dbnewref=FirebaseDatabase.getInstance().getReference();
        dbnewref.child("forum").child(forumId).child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    forumChatMessages m=ds.getValue(forumChatMessages.class);
                    ForumChatMsg.add(m);
                }
                ForumMsgAdapter= new forumChatMessageAdapter(forumChatActivity.this,ForumChatMsg);
                chat_recycler.setAdapter(ForumMsgAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
    private void sendMsg(String msg)
    {
        String timestamp=""+System.currentTimeMillis();
        HashMap<String,Object> msgMap=new HashMap<>();
        msgMap.put("sender",""+fireAuth.getUid());
        msgMap.put("message",""+msg);
        msgMap.put("timestamp",""+timestamp);
        msgMap.put("type",""+"text");
        DatabaseReference  ref=FirebaseDatabase.getInstance().getReference("forum");
        ref.child(forumId).child("Messages").child(timestamp).setValue(msgMap).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                msgTyping.setText("");
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(forumChatActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                imageUri=data.getData();
                sendImageMessage();
            }
            if(requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                sendImageMessage();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0)
                {
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted)
                    {
                        pickCamera();
                    }
                    else
                    {
                        Toast.makeText(this,"Camera * Storage Permission Are Required...", Toast.LENGTH_SHORT ).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if(grantResults.length>0)
                {
                    boolean writeStorageAccepted=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted)
                    {
                        pickGallery();
                    }
                    else
                    {
                        Toast.makeText(this,"Storage Permssion required....",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
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