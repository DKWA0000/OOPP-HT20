package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * View for a users reports
 *
 * @author Lucas Karlsson
 */
public class UserReportViewItem extends ConstraintLayout {
    private TextView mLocationText;
    private TextView mTimeText;
    private TextView mCountText;

    public UserReportViewItem(Context context, String position, String time, String count) {
        super(context);
        inflate(context,R.layout.userreportviewitem,this);
        Init();

        SetText(position,time,count);


    }

    public void setOnEditListener(OnClickListener listener){
        ((Button)findViewById(R.id.urw_editButton)).setOnClickListener(listener);
    }

    public UserReportViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.userreportviewitem,this);
        Init();
    }

    public UserReportViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.userreportviewitem,this);
        Init();
    }

    private void Init(){
        mLocationText = findViewById(R.id.urw_locationText);
        mTimeText = findViewById(R.id.urw_timeText);
        mCountText = findViewById(R.id.urw_countText);
    }

    public void SetText(String position, String time, String count){
        mLocationText.setText(position);
        mTimeText.setText(time);
        mCountText.setText(count);
    }
}
