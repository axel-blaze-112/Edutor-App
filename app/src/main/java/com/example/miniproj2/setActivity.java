package com.example.miniproj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.miniproj2.Adapters.gridAdapter;

import java.util.List;


public class setActivity extends AppCompatActivity {

private GridView gridView;
    private int setNo;
    private List<String> sets;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

       Toolbar bar=findViewById(R.id.toolbar);
       setSupportActionBar(bar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowCustomEnabled(true);
       getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        sets=CategoryActivity.list.get(getIntent().getIntExtra("position",0)).getSet();
        gridView=findViewById(R.id.gridView1);
        key=getIntent().getStringExtra("key");
        Log.d("keyinfoset",key.toString());
        gridAdapter adapter=new gridAdapter(sets,getIntent().getStringExtra("title"),key);
        gridView.setAdapter(adapter);

    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            fileList();
        }
        return super.onOptionsItemSelected(item);
    }*/
}