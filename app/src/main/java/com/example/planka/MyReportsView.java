package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import model.MODEL;

public class MyReportsView extends ConstraintLayout {
    LinearLayout mReportList;
    MODEL model;
    /* Placeholder: MODEL bör implementeras via Observer-pattern eller dylikt */

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
        model = new MODEL(getContext().getAssets());
        //PLACEHOLDER
        for (int x = 0 ; x < model.getIncidentCount() ; x++){
            UserReportView test = new UserReportView(this.getContext(),"temp","temp",0);
            /* This is where I'd use the info received from model.getIncident(x)
            * to give explicit information regarding an Incident */
            test.SetText("ingenstans", "Aldrig", String.valueOf(x));
            /* If I had any
            * ( OBS: För stunden använder den här sidan Incidents istället för Reports)*/
            mReportList.addView(test);
        }
        if(mReportList.getChildCount() == 0)
        {
            TextView emptyListNote = new TextView(this.getContext());
            emptyListNote.setText("No Reports Found");
            mReportList.addView(emptyListNote);
        }
        //PLACEHOLDER_END
    }
}
