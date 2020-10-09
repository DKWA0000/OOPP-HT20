package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import model.MODEL;

public class IncidentListView extends ConstraintLayout {
    LinearLayout mIncidentList;
    /* Placeholder: MODEL bör implementeras via Observer-pattern eller dylikt */

    public IncidentListView(Context context) {
        super(context);
        inflate(context,R.layout.incidentlistview,this);
        Init();

    }

    public IncidentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.incidentlistview,this);
        Init();
    }

    public IncidentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.incidentlistview,this);
        Init();
    }

    private void Init(){
        mIncidentList = findViewById(R.id.Incidentlist);
        //PLACEHOLDER
        //PLACEHOLDER_END
    }
}
