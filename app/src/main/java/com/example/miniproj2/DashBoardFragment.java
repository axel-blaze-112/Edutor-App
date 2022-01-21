package com.example.miniproj2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.miniproj2.Adapters.selectedCourseAdapter;
import com.example.miniproj2.Models.selectedCourseModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class DashBoardFragment extends Fragment {
private RecyclerView selected_course_recycler;
    RecyclerView enrolledRecycler;
    selectedCourseAdapter enrolledAdap;
    ImageView settings,logout,chat;
    private FirebaseAuth useraut;
    public DashBoardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_dash_board, container, false);
        useraut= FirebaseAuth.getInstance();
        settings=v.findViewById(R.id.settings);
        settings=v.findViewById(R.id.settings);

        chat=v.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getContext(),forumChatActivity.class);
                startActivity(in);
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getContext(),EditProfileActivity.class);
                startActivity(in);

            }
        });

        logout=v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              logout();

            }
        });




        ArrayList<selectedCourseModel> list=new ArrayList<>();
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme1));
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme2));
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme3));
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme4));
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme5));
        list.add(new selectedCourseModel("Java","Java is an object-oriented programming language",R.drawable.theme6));

        setProductsRecy(v,list);
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setProductsRecy(View v, ArrayList<selectedCourseModel> selectedModel)
    {
        selected_course_recycler=v.findViewById(R.id.selected_course_recycler);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        selected_course_recycler.setLayoutManager(lm);
        enrolledAdap=new selectedCourseAdapter(getContext() ,selectedModel);
        selected_course_recycler.setAdapter(enrolledAdap);
    }
    private void logout()
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
    private void sendUserToLoginActivity() {
        Intent intent = new Intent(getContext(),UserLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

}