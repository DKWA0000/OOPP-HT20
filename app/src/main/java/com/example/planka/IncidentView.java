package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @author Lucas Karlsson
 * @see model.Incident
 * @see model.MODEL
 */

public class IncidentView extends ConstraintLayout {
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public IncidentView(Context context, String station, String nCont, String time) {
        super(context);
        inflate(context, R.layout.incidentview, this);
        Init();
        SetText(station, time, String.valueOf(nCont));

        ImageView thumb = ((ImageView) findViewById(R.id.iw_like));

        thumb.setImageResource(R.drawable.like);
        findViewById(R.id.iw_like).setOnClickListener((View view) -> {
            thumb.setImageResource(R.drawable.like_green);
            ImageView verified = ((ImageView) findViewById(R.id.iw_verified));
            verified.setImageResource(R.drawable.verified);
        });
    }

    public IncidentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.incidentview, this);
        Init();
    }

    public IncidentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.incidentview, this);
        Init();
    }

    private void Init() {
        mLocationText = findViewById(R.id.locationText);
        mTimeText = findViewById(R.id.timeText);
        mCountText = findViewById(R.id.countText);
    }

    public void SetText(String position, String time, String count) {
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }

    public TextView getLocationText() {
        return this.mLocationText;
    }
}
