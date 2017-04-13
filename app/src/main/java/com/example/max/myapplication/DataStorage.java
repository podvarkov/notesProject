package com.example.max.myapplication;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by MAX on 28.03.2017.
 */
public interface DataStorage {
    void addNote(Note note);
    void editNote(Note note);
    ArrayList<Note> getAllNotes();
    void saveNotes(Context context);
    void readNotes(Context context);
    void removeNotes(Note n);
    Note getNote(int position);
}
