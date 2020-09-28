package com.example.planka;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        ToLocation(findViewById(0));


        Spinner spinner = (Spinner) findViewById(R.id.lineSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.whenSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.when_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        hideLineDropdown(findViewById(0));
        hideWhenDropdown(findViewById(0));

    }



    public void ToLocation(View view) {
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon_active));
        findViewById(R.id.mainLocationView).setVisibility(View.VISIBLE);

        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon));
        findViewById(R.id.mainReportView).setVisibility(View.INVISIBLE);

        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon));
        findViewById(R.id.mainProfileView).setVisibility(View.INVISIBLE);

    }

    public void ToReport(View view) {
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon));
        findViewById(R.id.mainLocationView).setVisibility(View.INVISIBLE);

        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon_active));
        findViewById(R.id.mainReportView).setVisibility(View.VISIBLE);

        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon));
        findViewById(R.id.mainProfileView).setVisibility(View.INVISIBLE);

        ToMakeReport(view);

    }

    public void ToProfile(View view) {
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon));
        findViewById(R.id.mainLocationView).setVisibility(View.INVISIBLE);

        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon));
        findViewById(R.id.mainReportView).setVisibility(View.INVISIBLE);

        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon_active));
        findViewById(R.id.mainProfileView).setVisibility(View.VISIBLE);

    }

    public void ToMyReports(View view) {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.VISIBLE);

    }

    public void ToMakeReport(View view) {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));


        findViewById(R.id.reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
    }

    public void showLineDropdown(View view) {
        findViewById(R.id.lineDropdownText).setVisibility(View.VISIBLE);
        findViewById(R.id.lineDivider).setVisibility(View.VISIBLE);
        findViewById(R.id.lineSpinner).setVisibility(View.VISIBLE);
    }

    public void hideLineDropdown(View view) {
        findViewById(R.id.lineDropdownText).setVisibility(View.GONE);
        findViewById(R.id.lineDivider).setVisibility(View.GONE);
        findViewById(R.id.lineSpinner).setVisibility(View.GONE);
    }

    public void showWhenDropdown(View view) {
        findViewById(R.id.whenDropdownText).setVisibility(View.VISIBLE);
        findViewById(R.id.whenDivider).setVisibility(View.VISIBLE);
        findViewById(R.id.whenSpinner).setVisibility(View.VISIBLE);
    }

    public void hideWhenDropdown(View view) {
        findViewById(R.id.whenDropdownText).setVisibility(View.GONE);
        findViewById(R.id.whenDivider).setVisibility(View.GONE);
        findViewById(R.id.whenSpinner).setVisibility(View.GONE);
    }


}
