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

public class LocationView extends ConstraintLayout {
    //Class which displays information regarding a given incident.
    TextView mPositionLabel;

    public LocationView(Context context) {
        super(context);
        inflate(context,R.layout.locationview,this);
        Init();
    }

    public LocationView(Context context, AttributeSet attrs) {
        //this is the constructor that currently gets called
        super(context, attrs);
        inflate(context,R.layout.locationview,this);
        Init();
    }

    public LocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.locationview,this);
        Init();
    }

    private void Init(){
        mPositionLabel = this.findViewById(R.id.positionLabel);
    }
}
