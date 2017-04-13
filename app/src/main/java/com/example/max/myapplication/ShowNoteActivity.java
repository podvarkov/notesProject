package com.example.max.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNoteActivity extends AppCompatActivity {
    TextView title;
    TextView maintext;
    TextView date;
    Integer position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title=(TextView)findViewById(R.id.show_note_title);
        maintext=(TextView)findViewById(R.id.show_note_maintext);
        date=(TextView)findViewById(R.id.show_note_date);
        position=(Integer)getIntent().getSerializableExtra("ID");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (position!=null) {
            Note note=MemCacheDataStorage.getInstance().getNote(position);
            title.setText(note.getNoteTitle());
            maintext.setText(note.getNoteMainText());
            date.setText(note.getNoteCreatedDate().toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove:
                Note n=MemCacheDataStorage.getInstance().getNote(position);
                MemCacheDataStorage.getInstance().removeNotes(n);
                Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_edit:
                Intent edit=new Intent(getApplicationContext(),AddNoteActivity.class);
                edit.putExtra("ID",position);
                startActivity(edit);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
