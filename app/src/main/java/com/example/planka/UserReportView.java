package com.example.planka;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class UserReportView extends ConstraintLayout {
    //Class which displays information regarding a given report.
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public UserReportView(Context context,String position, String time, String count) {
        super(context);
        inflate(context,R.layout.userreportview,this);
        Init();

        SetText(position,time,count);

    }

    public UserReportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.userreportview,this);
        Init();
    }

    public UserReportView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.userreportview,this);
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
