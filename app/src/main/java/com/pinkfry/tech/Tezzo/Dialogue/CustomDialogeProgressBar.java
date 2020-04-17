package com.pinkfry.tech.Tezzo.Dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.pinkfry.tech.Tezzo.R;

public class CustomDialogeProgressBar extends Dialog {
    public Context c;
    public Dialog d;





    public CustomDialogeProgressBar(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_view_progress);



    }

}
