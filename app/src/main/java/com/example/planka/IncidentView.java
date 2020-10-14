package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import model.IncidentType;

public class IncidentView extends ConstraintLayout {
    //Class which displays information regarding a given Incident.
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;
    private boolean endorsed = false;

    public IncidentView(IncidentType type, Context context, String station, String nCont, String time) {
        super(context);
        inflate(context,R.layout.incidentview,this);
        Init();
        if(type == IncidentType.ROUTE) {
            station = "Spårvagn " + station;
        }
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
        mLocationText = findViewById(R.id.urw_locationText);
        mTimeText = findViewById(R.id.urw_timeText);
        mCountText = findViewById(R.id.urw_countText);

        ((ImageView)findViewById(R.id.iw_endorseShield)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shield, null));
        findViewById(R.id.iw_endorseShield).setTooltipText("Ej pålitlig rapport");


        findViewById(R.id.iw_endorseShield).setOnClickListener((View view)->{
                endorse(view);
        });
    }

    private void endorse(View view) {
        ((ImageView) view).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shield_green, null));
        findViewById(R.id.iw_endorseShield).setTooltipText("Pålitlig rapport");
        endorsed = false;
    }

    public void SetText(String position, String time, String count){
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }
}
