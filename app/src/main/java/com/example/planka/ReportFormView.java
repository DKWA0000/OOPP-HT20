package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * View that holds the form you use to report a Incident.
 *
 * @author Lucas Karlsson
 */

public class ReportFormView extends ConstraintLayout {

    public ReportFormView(Context context) {
        super(context);
        inflate(context,R.layout.reportformview,this);
    }

    public ReportFormView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.reportformview,this);
    }

    public ReportFormView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.reportformview,this);
    }
}

