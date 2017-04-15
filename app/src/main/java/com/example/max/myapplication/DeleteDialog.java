package com.example.max.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

public class DeleteDialog extends DialogFragment{
    DeleteDialogListener dialogListener;
    public interface DeleteDialogListener{
        void onSubmitClick();
        void onCancelClick();
        void onSetTimeClick(Date date);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener=(DeleteDialogListener)activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete")
                .setIcon(R.drawable.note)
                .setMessage("Are You sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogListener.onSubmitClick();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogListener.onCancelClick();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

