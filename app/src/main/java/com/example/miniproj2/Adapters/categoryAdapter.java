package com.example.miniproj2.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniproj2.Models.categoryModel;
import com.example.miniproj2.R;
import com.example.miniproj2.setActivity;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder>{
    private List<categoryModel> cm;
    public categoryAdapter(List<categoryModel> cm) {
        this.cm = cm;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_active_course_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.setData(cm.get(position).getUrl(),cm.get(position).getName(),position,cm.get(position).getCourseDesc(),cm.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return cm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img;
        private TextView courseName,courseDesc;
        private ImageButton delete;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.cate_img);
            courseName=itemView.findViewById(R.id.Course_Name);
            courseDesc=itemView.findViewById(R.id.Course_desc);


        }
        private void setData(String url,String title,int position,String courseDesc,String key)
        {
            Glide.with(itemView.getContext()).load(url).into(img);
            this.courseName.setText(title);
            this.courseDesc.setText(courseDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(itemView.getContext(),setActivity.class);
                    i.putExtra("title",title);
                    i.putExtra("position",position);
                    i.putExtra("key",key);
                    itemView.getContext().startActivity(i);
                }
            });

        }
    }


}
