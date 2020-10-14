package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EditReportView extends ConstraintLayout {

    public EditReportView(Context context) {
        super(context);
        inflate(context,R.layout.editreportview,this);

    }

    public EditReportView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.editreportview,this);
    }

    public EditReportView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.editreportview,this);
    }

}
