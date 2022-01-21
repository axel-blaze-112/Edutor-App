package com.example.miniproj2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproj2.R;
import com.example.miniproj2.Models.forumChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class forumChatMessageAdapter extends RecyclerView.Adapter<forumChatMessageAdapter.holderGroupChat>
{
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private ArrayList<forumChatMessages> forumChatmodel;
    private FirebaseAuth fireAuth;

    public forumChatMessageAdapter(Context context, ArrayList<forumChatMessages> forumChatmodel) {
        this.context = context;
        this.forumChatmodel = forumChatmodel;
        fireAuth= FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public holderGroupChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==MSG_TYPE_RIGHT)
        {
            View vi= LayoutInflater.from(context).inflate(R.layout.forum_chat_sender_right,parent,false);
            return new holderGroupChat(vi);
        }
        else
        {
            View v2= LayoutInflater.from(context).inflate(R.layout.forum_chat_receiver_left,parent,false);
            return new holderGroupChat(v2);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull holderGroupChat holder, int position)
    {
        forumChatMessages model=forumChatmodel.get(position);
        String timeStamp=model.getTimeStamp();
        String msg=model.getMessage();
        String SenderUID=model.getSender();
        String messageType=model.getType();
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeStamp));
      /*  SimpleDateFormat currentDate= new SimpleDateFormat("MMM, dd, yyyy");
        String saveCurrentDate=currentDate.format(cal.getTime());*/
        SimpleDateFormat CurrentTime= new SimpleDateFormat("hh: mm: a");
        String saveCurrentTime=CurrentTime.format(cal.getTime());

        if(messageType.equals("text"))
        {
            holder.messageImages.setVisibility(View.GONE);
            holder.userMessage.setVisibility(View.VISIBLE);
            holder.userMessage.setText(msg);
        }
        else
        {
            holder.messageImages.setVisibility(View.VISIBLE);
            holder.userMessage.setVisibility(View.GONE);
            try
            {
                Picasso.get().load(msg).into(holder.messageImages);
            }
            catch (Exception e)
            {
                holder.messageImages.setImageResource(R.drawable.profile);
            }
        }
        holder.userMessage.setText(msg);
        holder.userTime.setText(saveCurrentTime);

        setUserName(model,holder);
    }
    private void setUserName(forumChatMessages model, holderGroupChat holder)
    {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("uid").equalTo(model.getSender()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String name=""+ds.child("username").getValue();
                        holder.userName.setText(name);

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
    @Override
    public int getItemViewType(int position) {
       if(forumChatmodel.get(position).getSender().equals(fireAuth.getUid()))
       {
            return MSG_TYPE_RIGHT;
       }
       else
       {
           return MSG_TYPE_LEFT;
       }
    }

    @Override
    public int getItemCount() {
        return forumChatmodel.size();
    }

    class holderGroupChat extends RecyclerView.ViewHolder
    {
        private TextView userName,userMessage,userTime;
        private ImageView messageImages;
        public holderGroupChat(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.user_name);
            userMessage=itemView.findViewById(R.id.user_message);
            userTime=itemView.findViewById(R.id.user_time);
            messageImages=itemView.findViewById(R.id.message_images);
        }
    }
}
