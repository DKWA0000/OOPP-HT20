package com.example.planka;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyReportsView extends ConstraintLayout {

    public MyReportsView(Context context) {
        super(context);
        inflate(context,R.layout.myreportsview,this);
    }

    public MyReportsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.myreportsview,this);
    }

    public MyReportsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.myreportsview,this);
    }


}
