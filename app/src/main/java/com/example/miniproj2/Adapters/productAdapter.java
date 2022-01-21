package com.example.miniproj2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.productCategoryModel;
import com.example.miniproj2.R;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
  Context context;

    public productAdapter(Context context, List<productCategoryModel> productCategoryModels) {
        this.context = context;
        this.productCategoryModels = productCategoryModels;
    }

    List<productCategoryModel> productCategoryModels;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.prod_row_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(productCategoryModels.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return productCategoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.cat_name);
        }
    }
}
