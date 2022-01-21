package com.example.miniproj2.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miniproj2.QuestionsActivity;
import com.example.miniproj2.R;

import java.util.List;

public class gridAdapter extends BaseAdapter {

    private List<String> sets;
    private String category;
    private String key;
    public gridAdapter(List<String> sets,String category,String key) {
        this.sets = sets;
        this.category=category;
        this.key=key;
    }
    @Override
    public int getCount() {
        return sets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null)
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }
        else
        {
            view=convertView;

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q=new Intent(parent.getContext(), QuestionsActivity.class);
               q.putExtra("category",category);
                q.putExtra("setId",sets.get(position));
                q.putExtra("key",key);
                parent.getContext().startActivity(q);
            }
        });

        ((TextView)view.findViewById(R.id.textBVew)).setText(String.valueOf(position+1));

        return view;
    }
}
