package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyReportsView extends ConstraintLayout {
    //Class which displays information regarding a given incident.
    TextView mPositionLabel;

    public MyReportsView(Context context) {
        super(context);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    public MyReportsView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    public MyReportsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    private void Init(){
        mPositionLabel = this.findViewById(R.id.positionLabel);
    }
}
