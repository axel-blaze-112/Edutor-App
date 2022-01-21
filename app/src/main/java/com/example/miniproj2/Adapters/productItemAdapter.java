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

import com.example.miniproj2.Models.productsModel;
import com.example.miniproj2.R;
import com.example.miniproj2.prodDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productItemAdapter extends RecyclerView.Adapter<productItemAdapter.Viewholder> {

    Context context;
    List<productsModel> prodm;

    public productItemAdapter(Context context, List<productsModel> prodm) {
        this.context = context;
        this.prodm = prodm;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.prod_card_items,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        /*holder.prodQty.setText(prodm.get(position).getProductQty());*/

        Picasso.get().load(prodm.get(position).getImageUrl()).into(holder.prodImage);
        holder.prodName.setText(prodm.get(position).getProductName());
        holder.prodPrice.setText(String.valueOf(prodm.get(position).getProductPrice()));
        holder.setData( position);
    }

    @Override
    public int getItemCount() {
        return prodm.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView prodImage;
        TextView prodName,prodQty,prodPrice;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        /*    prodQty =itemView.findViewById(R.id.prod_qty);*/
            prodImage =itemView.findViewById(R.id.prod_img);
            prodName =itemView.findViewById(R.id.prod_namess);
            prodPrice=itemView.findViewById(R.id.prod_pricess);


        }
        private void setData(int post)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(itemView.getContext(), prodDetailsActivity.class);
                    i.putExtra("prodid",prodm.get(post).getProductId());
                    itemView.getContext().startActivity(i);

                }
            });
        }
    }
}
