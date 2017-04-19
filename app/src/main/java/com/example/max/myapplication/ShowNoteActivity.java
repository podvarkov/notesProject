package com.example.max.myapplication;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ShowNoteActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener {
    DialogFragment d;
    TextView title;
    TextView maintext;
    TextView date;
    Integer position;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = (TextView) findViewById(R.id.show_note_title);
        maintext = (TextView) findViewById(R.id.show_note_maintext);
        date = (TextView) findViewById(R.id.show_note_date);
        position = (Integer) getIntent().getSerializableExtra("ID");
        Log.d("ShowNote","pos = "+position);

    }


    @Override
    public void onSetTimeClick(Date date) {
        Log.d("Log", "" + date.getTime());
        setNotification(date);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //onPrepareOptionsMenu(toolbar.getMenu());
        if (position != null) {
            Note note = MemCacheDataStorage.getInstance().getNote(position);
            title.setText(note.getNoteTitle());
            maintext.setText(note.getNoteMainText());
            date.setText(note.getNoteCreatedDate().toString());
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (MemCacheDataStorage.getInstance().getNote(position).isNotified()) {
            menu.getItem(2).setIcon(R.mipmap.ic_notifications_off_black_24dp);
        } else {
            menu.getItem(2).setIcon(R.mipmap.ic_notifications_none_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove:
                DialogFragment deleteDialog = new DeleteDialog();
                FragmentManager fragmentManager = getFragmentManager();
                deleteDialog.show(fragmentManager, "TAG");
                break;
            case R.id.action_edit:
                Intent edit = new Intent(getApplicationContext(), AddNoteActivity.class);
                edit.putExtra("ID", position);
                startActivity(edit);
                break;
            case R.id.action_notify:
                if (MemCacheDataStorage.getInstance().getNote(position).isNotified()){
                    AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                    Intent alarmIntent = new Intent(this, NotificationReciever.class);
                    PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this,position,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(alarmPendingIntent);
                    MemCacheDataStorage.getInstance().getNote(position).setNotification(false);
                    onPrepareOptionsMenu(toolbar.getMenu());
                    Toast.makeText(this, "Canceled Notification", Toast.LENGTH_SHORT).show();
                } else {
                    d = new DateTimeDialog();
                    d.show(getFragmentManager(), "date");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubmitClick() {
        Note n = MemCacheDataStorage.getInstance().getNote(position);
        MemCacheDataStorage.getInstance().removeNotes(n);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCancelClick() {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();


    }

    public void setNotification(Date d) {
        //готовим интент для уведомления
        Intent intent = new Intent(this,ShowNoteActivity.class);
        intent.putExtra("ID",MemCacheDataStorage.getInstance().getNote(position).getNoteId()); //отправляем id заметки
        //готовим уведомление
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder noBuilder = new Notification.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.note)
                .setContentText(MemCacheDataStorage.getInstance().getNote(position).getNoteTitle())
                .setAutoCancel(true)
                .setContentTitle("Notify")
                .setContentIntent(pendingIntent);
        Notification noti = noBuilder.build();
        //готовим будильник.
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, NotificationReciever.class);
        alarmIntent.putExtra("NOTI",noti);
        alarmIntent.putExtra("ID",position);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this,position,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP,d.getTime(),alarmPendingIntent);
        MemCacheDataStorage.getInstance().getNote(position).setNotification(true);
        Toast.makeText(this,"Notification set on "+d,Toast.LENGTH_LONG).show();
        onPrepareOptionsMenu(toolbar.getMenu());
    }
}
