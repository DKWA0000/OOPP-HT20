package com.example.planka.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.planka.R;
import com.example.planka.model.Incident;
import com.example.planka.model.MODEL;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @author Lucas Karlsson
 * @see Incident
 * @see MODEL
 */

public class LocationView extends ConstraintLayout {

    TextView mPositionLabel;

    public LocationView(Context context) {
        super(context);
        inflate(context, R.layout.locationview, this);
        Init();
    }

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.locationview, this);
        Init();
    }

    public LocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.locationview, this);
        Init();
    }

    private void Init() {
        mPositionLabel = this.findViewById(R.id.urw_reportPositionLabel);
    }
}
