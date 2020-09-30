package com.example.planka;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class IncidentItemView extends ConstraintLayout {
    //Class which displays information regarding a given incident.
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public IncidentItemView(Context context) {
        super(context);
        inflate(context,R.layout.incidentitemview,this);
        Init();
    }

    public IncidentItemView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.incidentitemview,this);
        Init();
    }

    public IncidentItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.incidentitemview,this);
        Init();
    }

    private void Init(){
        mLocationText = findViewById(R.id.locationText);
        mTimeText = findViewById(R.id.timeText);
        mCountText = findViewById(R.id.countText);
    }

    public void SetText(String position, String time, String count){
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }
}
