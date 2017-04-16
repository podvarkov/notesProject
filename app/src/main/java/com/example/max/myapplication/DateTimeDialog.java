package com.example.max.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimeDialog extends DialogFragment {
    DeleteDialog.DeleteDialogListener dialogListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener=(DeleteDialog.DeleteDialogListener)activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = null;
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.date_time_picker,null);
        Button setButton=(Button)v.findViewById(R.id.date_time_set);
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.time_picker);


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                        calendar.set(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
                dialogListener.onSetTimeClick(calendar.getTime());
                dismiss();
            }
        });

        builder = new AlertDialog.Builder(getActivity());

        builder.setCustomTitle(inflater.inflate(R.layout.set_notif,null))
                .setView(v)
                .setIcon(R.drawable.note);

        return builder.create();
    }
}
