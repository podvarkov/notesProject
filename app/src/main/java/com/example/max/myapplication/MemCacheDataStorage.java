package com.example.max.myapplication;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by MAX on 30.03.2017.
 */
public class MemCacheDataStorage implements DataStorage{
    private static MemCacheDataStorage ourInstance = new MemCacheDataStorage();

    public static MemCacheDataStorage getInstance() {
        return ourInstance;
    }
    private ArrayList<Note> notes;
    private MemCacheDataStorage() {

    }

    @Override
    public void addNote(Note note) {
        note.setNoteID(notes.size());
        notes.add(note);
    }

    @Override
    public void editNote(Note note) {
        notes.set(note.getNoteId(),note);
    }

    @Override
    public ArrayList<Note> getAllNotes() {
        return notes;
    }

    @Override
    public void saveNotes(Context context) {
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
        Log.d("Log",gson.toJson(notes));
        try {
            FileOutputStream fileOutputStream =context.openFileOutput("notes.dat",Context.MODE_PRIVATE);
            fileOutputStream.write(gson.toJson(notes).getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            }

    @Override
    public void readNotes(Context context) {
        String line="";
        String resJson="";
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
        try {
            FileInputStream fileInputStream=context.openFileInput("notes.dat");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));

            while ((line=bufferedReader.readLine())!=null) {
                resJson+=line;
            }
            fileInputStream.close();
            if (!resJson.equals("")) {
            notes=gson.fromJson(resJson,new TypeToken<ArrayList<Note>>(){}.getType());
            } else {notes=new ArrayList<>();}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            notes=new ArrayList<>();
            Log.d("Log","IOException");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public void removeNotes(Note n) {
            notes.remove(n);
        }
    }

