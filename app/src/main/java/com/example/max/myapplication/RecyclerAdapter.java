package com.example.max.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MAX on 06.04.2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Note> mDataset;
    public Context context;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView noteTitle;
        public TextView noteDate;
        public TextView noteMainT;
        public ImageView im;
        public ViewHolder(View v, final Context context) {
            super(v);
            CardView cardView=(CardView)v.findViewById(R.id.card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Log","Position = "+getAdapterPosition());
                    Intent intent=new Intent(context,ShowNoteActivity.class);
                    intent.putExtra("ID",new Integer(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            im=(ImageView)v.findViewById(R.id.imageView);
            noteTitle = (TextView) v.findViewById(R.id.rv_title);
            noteDate=(TextView)v.findViewById(R.id.rv_date);
            noteMainT=(TextView)v.findViewById(R.id.rv_maintext);
        }
    }

    // Конструктор
    public RecyclerAdapter(ArrayList<Note> dataset,Context context) {
        mDataset = dataset;
        this.context=context;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v,context);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Note n=mDataset.get(position);
        holder.noteTitle.setText(n.getNoteTitle());
        if (n.getNoteMainText().length()>=150) {
            holder.noteMainT.setText(n.getNoteMainText().substring(0,149)+"\n...");
        } else holder.noteMainT.setText(n.getNoteMainText()+"\n...");
        holder.noteDate.setText(n.getNoteCreatedDate().toString());


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
