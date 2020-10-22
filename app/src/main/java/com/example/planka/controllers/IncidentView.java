package com.example.planka.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.planka.R;
import com.example.planka.model.Incident;
import com.example.planka.model.IncidentType;
import com.example.planka.model.MODEL;
import com.example.planka.model.Observer;

import java.text.SimpleDateFormat;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @author Lucas Karlsson
 * @see Incident
 * @see MODEL
 */

public class IncidentView extends ConstraintLayout implements Observer<Incident> {

    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;
    private boolean like = false;

    public IncidentView(Context context, Incident incident) {
        super(context);
        inflate(context, R.layout.incidentview, this);
        Init();

        String time = new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(incident.getLatestReport().getTimeOfReport());
        String controllants = Integer.toString(incident.getLatestReport().getnControllants());
        String position;
        if (incident.getLatestReport().getType() == IncidentType.ROUTE) {
            position = "Linje " + incident.getLatestReport().getRoute().getLine();
        } else {
            position = incident.getLatestReport().getStation().getName();
        }

        SetText(position, time, controllants);
        incident.addObserver(this);
        ImageView thumb = findViewById(R.id.iw_like);
        thumb.setImageResource(R.drawable.like);

        findViewById(R.id.iw_like).setOnClickListener((View view) -> {
            if (!like) {
                thumb.setImageResource(R.drawable.like_green);
                incident.upVote();
                like = true;
            } else {
                thumb.setImageResource(R.drawable.like);
                incident.upVote();
                like = false;
            }
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

    public void update(Incident incident) {
        String time = new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(incident.getLatestReport().getTimeOfReport());
        String controllants = Integer.toString(incident.getLatestReport().getnControllants());
        String position;
        if (incident.getLatestReport().getType() == IncidentType.ROUTE) {
            position = "Linje " + incident.getLatestReport().getRoute().getLine();

        } else {
            position = incident.getLatestReport().getStation().getName();
        }

        SetText(position, time, controllants);

        if (incident.getVotes() > 4) {
            ImageView verified = findViewById(R.id.iw_verified);
            verified.setImageResource(R.drawable.verified);
        }
    }

    public void SetText(String position, String time, String count) {
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }

    public TextView getLocationText() {
        return this.mLocationText;
    }

    @Override
    public void notify(Incident data) {
        update(data);
    }

}
