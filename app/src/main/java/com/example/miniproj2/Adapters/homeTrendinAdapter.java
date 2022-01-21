package com.example.miniproj2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniproj2.Models.categoryModel;
import com.example.miniproj2.Models.trendingModel;
import com.example.miniproj2.R;
import com.example.miniproj2.setActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeTrendinAdapter extends RecyclerView.Adapter<homeTrendinAdapter.ViewHolder> {

    private List<categoryModel> cm;
    Context context;

    public homeTrendinAdapter(List<categoryModel> cm) {
        this.cm = cm;
    }
    @NonNull
    @NotNull
    @Override
    public homeTrendinAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.enrollcourse_items,parent,false);
        return new homeTrendinAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull homeTrendinAdapter.ViewHolder holder, int position) {
        holder.setData(cm.get(position).getUrl(),cm.get(position).getName(),position,cm.get(position).getCourseDesc(),cm.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return cm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView courseName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.enrolimg);
            courseName=itemView.findViewById(R.id.enroCourse_Name);
        }
        private void setData(String url,String title,int position,String courseDesc,String key)
        {
            Glide.with(itemView.getContext()).load(url).into(img);
            this.courseName.setText(title);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(itemView.getContext(), setActivity.class);
                    i.putExtra("title",title);
                    i.putExtra("position",position);
                    i.putExtra("key",key);
                    itemView.getContext().startActivity(i);
                }
            });*/
        }
    }
}
