package com.example.planka.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.planka.R;
import com.example.planka.model.AbstractReport;
import com.example.planka.model.IncidentType;
import com.example.planka.model.Observer;

import java.text.SimpleDateFormat;

/**
 * View for a users reports
 *
 * @author Lucas Karlsson
 */
public class UserReportViewItem extends ConstraintLayout implements Observer<AbstractReport> {
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public UserReportViewItem(Context context, AbstractReport report, OnClickListener listener) {
        super(context);

        inflate(context, R.layout.userreportviewitem, this);
        Init();

        findViewById(R.id.urw_editButton).setOnClickListener(listener);

        update(report);
        report.addObserver(this);

    }



    public UserReportViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.userreportviewitem, this);
        Init();
    }

    public UserReportViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.userreportviewitem, this);
        Init();
    }

    private void Init() {
        mLocationText = findViewById(R.id.urw_locationText);
        mTimeText = findViewById(R.id.urw_timeText);
        mCountText = findViewById(R.id.urw_countText);
    }

    public void SetText(String position, String time, String count) {
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }


    public void update(AbstractReport report){
        System.out.println("UPDATE REPORT");
        String time = new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(report.getTimeOfReport());
        String controllants = Integer.toString(report.getnControllants());
        String position;
        if (report.getType() == IncidentType.ROUTE) {
            position = "Linje " + report.getRoute().getLine();

        } else {
            position = report.getStation().getName();
        }
        SetText(position, time, controllants);
    }
    @Override
    public void notify(AbstractReport data) {
        update(data);
    }
}
