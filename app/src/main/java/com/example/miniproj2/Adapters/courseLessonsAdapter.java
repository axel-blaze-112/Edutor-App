package com.example.miniproj2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.courseLessonsModel;
import com.example.miniproj2.R;
import com.example.miniproj2.ViewVideoActivity;
import com.example.miniproj2.courseDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class courseLessonsAdapter extends RecyclerView.Adapter<courseLessonsAdapter.ViewHolder> {

    Context context;
    List<courseLessonsModel> ls;
    public courseLessonsAdapter(Context context, List<courseLessonsModel> ls) {
        this.context = context;
        this.ls = ls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.drv_item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        courseLessonsModel l=ls.get(position);
        holder.name_of_the_video.setText(l.getTopicName());
        holder.setData(ls.get(position).getKey(),ls.get(position).getVideourl());


    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_of_the_video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_of_the_video=itemView.findViewById(R.id.name_of_the_video);

        }

        private void setData(String key,String Videourl)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ints=new Intent(context, ViewVideoActivity.class);
                    ints.putExtra("key",key);
                    ints.putExtra("videourl",Videourl);
                    context.startActivity(ints);
                }
            });
        }
    }
}
