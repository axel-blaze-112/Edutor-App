package com.example.miniproj2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.courseHomeModel;
import com.example.miniproj2.Models.trendingModel;
import com.example.miniproj2.R;
import com.example.miniproj2.courseDetailsActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class courseTrendingAdapter extends RecyclerView.Adapter<courseTrendingAdapter.ViewHolder>
{
    ArrayList<trendingModel> mainmodel;
    Context context;

    public courseTrendingAdapter(ArrayList<trendingModel> mainmodel, Context context) {
        this.mainmodel = mainmodel;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public courseTrendingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_course_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull courseTrendingAdapter.ViewHolder holder, int position) {
        Picasso.get().load(mainmodel.get(position).getCoverUrl()).into(holder.imageView);

        holder.textView.setText(mainmodel.get(position).getLangName());
        holder.setData(mainmodel.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return mainmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,enrolled_course_description;
        CardView course_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.enrolled_course_cover);
            textView=itemView.findViewById(R.id.enrolled_course_name);
            enrolled_course_description=itemView.findViewById(R.id.enrolled_course_description);
            course_card=itemView.findViewById(R.id.course_card);

        }
        private void setData(String key)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ints=new Intent(context, courseDetailsActivity.class);
                    ints.putExtra("key",key);
                    context.startActivity(ints);
                }
            });

        }
    }
}
