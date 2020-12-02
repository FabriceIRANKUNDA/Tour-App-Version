package com.example.slidingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loading {
    private Activity activity;
    private AlertDialog alertDialog;
    public Loading(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
