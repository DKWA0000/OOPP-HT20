package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyReportsView extends ConstraintLayout {
    LinearLayout mReportList;

    public MyReportsView(Context context) {
        super(context);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    public MyReportsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    public MyReportsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.myreportview,this);
        Init();
    }

    private void Init(){
        mReportList = findViewById(R.id.Reportlist);
        //PLACEHOLDER
        for (int x = 0 ; x < 5 ; x++){
            IncidentItemView test = new IncidentItemView(this.getContext());
            test.SetText("ingenstans", "Aldrig", String.valueOf(x));
            mReportList.addView(test);
        }
        //PLACEHOLDER_END
    }
}
