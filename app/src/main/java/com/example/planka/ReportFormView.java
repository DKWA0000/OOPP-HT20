package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ReportFormView extends ConstraintLayout {
    //Class which displays information regarding a given incident.
    TextView mPositionLabel;

    public ReportFormView(Context context) {
        super(context);
        inflate(context,R.layout.reportformview,this);
        Init();
    }

    public ReportFormView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.reportformview,this);
        Init();
    }

    public ReportFormView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.reportformview,this);
        Init();
    }

    private void Init(){
        mPositionLabel = this.findViewById(R.id.positionLabel);
    }
}
