package com.example.max.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by MAX on 27.03.2017.
 */
public class Note {
    private String noteTitle;
    private String noteMainText;
    private Date noteCreatedDate;
    private Integer noteId;
    private Boolean notificationTime;
    Note(String noteTitle,String noteMainText) {
        this.noteMainText=noteMainText;
        this.noteTitle=noteTitle;
        this.noteCreatedDate=new Date();
        this.noteId=null;
        this.notificationTime=false;
    }
    public void setSetNotificationTime(boolean flag) {
        notificationTime=flag;
    }
    public Boolean isSetNotificationTime() {
        return notificationTime;
    }
    public String getNoteTitle() {
        return noteTitle;
    }
    public String getNoteMainText() {
        return noteMainText;
    }
    public Integer getNoteId(){ return noteId;}
    public Date getNoteCreatedDate() {
        return noteCreatedDate;
    }
    public void setNoteCreatedDate(Date noteCreatedDate) {
        this.noteCreatedDate = noteCreatedDate;
    }
    public void setNoteID(Integer id){
        this.noteId=id;
    }
}
