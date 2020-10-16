package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @see model.Incident
 * @see model.MODEL
 *
 * @author Lucas Karlsson
 */

public class IncidentView extends ConstraintLayout {
    //Class which displays information regarding a given Incident.
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public IncidentView(Context context, String station, String nCont, String time) {
        super(context);
        inflate(context,R.layout.incidentview,this);
        Init();
        SetText(station, time, String.valueOf(nCont));
    }

    public IncidentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.incidentview,this);
        Init();
    }

    public IncidentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.incidentview,this);
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
