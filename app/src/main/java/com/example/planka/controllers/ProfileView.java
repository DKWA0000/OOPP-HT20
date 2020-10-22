package com.example.planka.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.planka.R;

/**
 * View for the profile, and profile settings.
 *
 * @author Lucas Karlsson
 */

public class ProfileView extends ConstraintLayout {
    TextView mPositionLabel;

    public ProfileView(Context context) {
        super(context);
        inflate(context, R.layout.profileview, this);
        Init();
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.profileview, this);
        Init();
    }

    public ProfileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.profileview, this);
        Init();
    }

    private void Init() {
        mPositionLabel = this.findViewById(R.id.urw_reportPositionLabel);
    }
}
