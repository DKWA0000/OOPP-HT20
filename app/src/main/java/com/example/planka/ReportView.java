package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * The view for individual reports.
 *
 * @author Lucas Karlsson
 */

public class ReportView extends ConstraintLayout {
    TextView mPositionLabel;

    public ReportView(Context context) {
        super(context);
        inflate(context, R.layout.reportview, this);
        Init();
    }

    public ReportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.reportview, this);
        Init();
    }

    public ReportView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.reportview, this);
        Init();
    }

    private void Init() {
        mPositionLabel = this.findViewById(R.id.urw_reportPositionLabel);
    }
}
