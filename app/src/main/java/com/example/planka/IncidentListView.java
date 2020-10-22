package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Class responsible for the view that hold IncidentListItems.
 *
 * @author Lucas Karlsson
 */

public class IncidentListView extends ConstraintLayout {

    LinearLayout mIncidentList;

    public IncidentListView(Context context) {
        super(context);
        inflate(context, R.layout.incidentlistview, this);
        Init();
    }

    public IncidentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.incidentlistview, this);
        Init();
    }

    public IncidentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.incidentlistview, this);
        Init();
    }

    private void Init() {
        mIncidentList = findViewById(R.id.Incidentlist);
        if (mIncidentList.getChildCount() == 0) {
            TextView emptyListNote = new TextView(this.getContext());
            emptyListNote.setText("No Incidents Found");
            mIncidentList.addView(emptyListNote);
        }
    }

}
