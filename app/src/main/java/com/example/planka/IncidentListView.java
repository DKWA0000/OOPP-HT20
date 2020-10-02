package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import model.MODEL;

public class IncidentListView extends ConstraintLayout {
    LinearLayout mIncidentList;
    MODEL model;
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
        model = new MODEL(getContext().getAssets());
        //PLACEHOLDER
        for (int x = 0 ; x < model.getIncidentCount() ; x++){
            IncidentView test = new IncidentView(this.getContext(), model.getIncident(x));
            /* This is where I'd use the info received from model.getIncident(x)
             * to give explicit information regarding an Incident */
            test.SetText("Placeholder", "Placeholder", String.valueOf(x));
            /* If I had any */
            mIncidentList.addView(test);
        }
        if(mIncidentList.getChildCount() == 0)
        {
            TextView emptyListNote = new TextView(this.getContext());
            emptyListNote.setText("No Incidents Found");
            mIncidentList.addView(emptyListNote);
        }
        //PLACEHOLDER_END
    }
}
