package com.example.miniproj2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.courseHomeModel;
import com.example.miniproj2.Models.selectedCourseModel;
import com.example.miniproj2.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class selectedCourseAdapter extends  RecyclerView.Adapter<selectedCourseAdapter.ViewHolder>
{

    ArrayList<selectedCourseModel> mainmodel;
    Context context;

    public selectedCourseAdapter(Context context,ArrayList<selectedCourseModel> mainmodel ) {
        this.mainmodel = mainmodel;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_course_items,parent,false);
        return new selectedCourseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.selectedCourseName.setText(mainmodel.get(position).getEnrolledCourseName());
        holder.selectedCourseDescription.setText(mainmodel.get(position).getEnrolledCourseDescription());
        Picasso.get().load(mainmodel.get(position).getEnrolledCourseImage()).into(holder.selectedCourseImage);
    }

    @Override
    public int getItemCount() {
        return mainmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView selectedCourseImage;
        TextView selectedCourseName,selectedCourseDescription;
        Button SelectedStart;
        CardView SelectedCourseCard;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            selectedCourseImage=itemView.findViewById(R.id.enrolled_course_cover);
            selectedCourseName=itemView.findViewById(R.id.enrolled_course_name);
            selectedCourseDescription=itemView.findViewById(R.id.enrolled_course_description);
            SelectedStart=itemView.findViewById(R.id.enrolled_start_btn);
            SelectedCourseCard=itemView.findViewById(R.id.enrolled_course_card);
        }
    }
}
