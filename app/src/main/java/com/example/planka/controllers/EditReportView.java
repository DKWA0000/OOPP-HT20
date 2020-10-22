package com.example.planka.controllers;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.planka.R;

/**
 * Class responsible for the view that shows when you edit a report.
 *
 * @author Lucas Karlsson
 */

public class EditReportView extends ConstraintLayout {

    public EditReportView(Context context) {
        super(context);
        inflate(context, R.layout.editreportview, this);

    }

    public EditReportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.editreportview, this);
    }

    public EditReportView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.editreportview, this);
    }

}
