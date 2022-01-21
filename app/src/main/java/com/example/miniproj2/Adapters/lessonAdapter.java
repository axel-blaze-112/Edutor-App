package com.example.miniproj2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.lessonModel;
import com.example.miniproj2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class lessonAdapter extends RecyclerView.Adapter<lessonAdapter.ViewHolder> {


    Context context;
    List<lessonModel> ls;
    public lessonAdapter(Context context, List<lessonModel> ls) {
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
        lessonModel l=ls.get(position);
        holder.name_of_the_video.setText(l.getName());
        holder.uploader_name.setText(l.getDescription());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView request_user_profile_image;
        TextView name_of_the_video,uploader_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_of_the_video=itemView.findViewById(R.id.name_of_the_video);

    }
}
}