package com.example.max.myapplication;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by MAX on 15.04.2017.
 */
public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification noti=(Notification)intent.getParcelableExtra("NOTI");
        Log.d("Log","Time Recieved "+new Date().toString());
        int notifyId=intent.getIntExtra("ID",0);
        Log.d("Log","ID "+notifyId);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId,noti);
        MemCacheDataStorage.getInstance().readNotes(context);
        MemCacheDataStorage.getInstance().getNote(notifyId).setSetNotificationTime(false);
    }
}