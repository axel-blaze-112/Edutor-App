package com.example.miniproj2.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.Models.redeemApprovModel;
import com.example.miniproj2.R;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class redeemApprovAdapter extends RecyclerView.Adapter<redeemApprovAdapter.ViewHolder> {

   List<redeemApprovModel> ram;
    Context context;
    public redeemApprovAdapter(List<redeemApprovModel> ram, Context context) {
        this.ram = ram;
        this.context = context;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.approv_card_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.prodname.setText(ram.get(position).getProduct_name());
        holder.prodprice.setText(ram.get(position).getProduct_price());
        holder.approvmsg.setText(ram.get(position).getApproval());
        holder.approvwait.setText(ram.get(position).getPleasewait());
        Picasso.get().load(ram.get(position).getProduct_image_url()).into(holder.prodimg);
        Picasso.get().load(ram.get(position).getWaitIcon()).into(holder.approvwaitimg);

    }

    @Override
    public int getItemCount() {
        return ram.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prodname,prodprice,approvmsg,approvwait;
        CircleImageView prodimg;
        ImageView approvwaitimg;
        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);

            prodname=itemView.findViewById(R.id.approv_prod_name);
            prodprice=itemView.findViewById(R.id.approv_prod_price);
            approvmsg=itemView.findViewById(R.id.approv_admin_msg);
            approvwait=itemView.findViewById(R.id.approv_please_wait);
            prodimg=itemView.findViewById(R.id.approv_prod_img);
            approvwaitimg=itemView.findViewById(R.id.iconimg);

        }



    }
}
