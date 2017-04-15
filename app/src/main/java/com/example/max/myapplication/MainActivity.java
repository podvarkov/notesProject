package com.example.max.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MemCacheDataStorage.getInstance().readNotes(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id=null;
                Intent intent=new Intent(getApplicationContext(),AddNoteActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);

            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recycler_v);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerAdapter=new RecyclerAdapter(MemCacheDataStorage.getInstance().getAllNotes(),this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MemCacheDataStorage.getInstance().saveNotes(this);
    }
}
