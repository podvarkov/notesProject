package com.example.max.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    private EditText mainText;
    private EditText title;
    private Intent intent;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mainText = (EditText) findViewById(R.id.ed_noteMainText);
        title = (EditText) findViewById(R.id.ed_note_title);
        intent = getIntent();
        id= (Integer) intent.getSerializableExtra("ID");
        if (id!=null) {
            Note n=MemCacheDataStorage.getInstance().getNote(id);
            mainText.setText(n.getNoteMainText());
            title.setText(n.getNoteTitle());
        }
    }



    public void addNote(View view) {
        Note note = new Note(title.getText().toString(), mainText.getText().toString());
        note.setNoteID(id);
        if (id==null) {
            MemCacheDataStorage.getInstance().addNote(note);
        } else {
            MemCacheDataStorage.getInstance().editNote(note);
        }
        finish();
    }
}



