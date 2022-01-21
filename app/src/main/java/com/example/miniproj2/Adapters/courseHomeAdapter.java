package com.example.miniproj2.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.courseHomeModel;
import com.example.miniproj2.R;
import com.example.miniproj2.courseDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class courseHomeAdapter extends  RecyclerView.Adapter<courseHomeAdapter.ViewHolder>  {


    ArrayList<courseHomeModel> mainmodel;
    Context context;
   /* ArrayList<courseHomeModel> searchMd;*/

    public courseHomeAdapter(ArrayList<courseHomeModel> mainmodel, Context context) {
        this.mainmodel = mainmodel;
        this.context = context;
       // this.searchMd=mainmodel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_row_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHomeAdapter.ViewHolder holder, int position) {
        Picasso.get().load(mainmodel.get(position).getCoverUrl()).into(holder.imageView);
        holder.textView.setText(mainmodel.get(position).getLangName());
        holder.setData(mainmodel.get(position).getKey());
        holder.total_lessons.setText("Lessons "+ mainmodel.get(position).getLessonNo());

    }

    @Override
    public int getItemCount() {
        return mainmodel.size();
    }

    /*@Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<courseHomeModel> filteredlist=new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                filteredlist.addAll(mainmodel);
            }
            else
            {
                for(courseHomeModel course: mainmodel)
                {
                    if(course.toL)
                }
            }

            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };*/


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,total_lessons;
        CardView course_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.course_img);
            textView=itemView.findViewById(R.id.course_name);
            course_card=itemView.findViewById(R.id.course_card);
            total_lessons=itemView.findViewById(R.id.total_lessons);

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
