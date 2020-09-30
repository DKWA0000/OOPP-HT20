package com.example.planka;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class FilipActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        toLocation();


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

        spinner = (Spinner) findViewById(R.id.controllantsSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        hideLineDropdown(findViewById(0));
        hideWhenDropdown(findViewById(0));
    }


    public void onClick(View view){

        if (view == findViewById(R.id.locationsButton)) {
            toLocation();
        }
        if (view == findViewById(R.id.reportsButton)) {
            toReport();
        }
        if (view == findViewById(R.id.profileButton)) {
            toProfile();
        }
        if (view == findViewById(R.id.makeReportText)) {
            toMakeReport();
        }
        if (view == findViewById(R.id.myReportsText)) {
            toMyReports();
        }

    }

    public void activateLocationButton(){
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon_active));
        findViewById(R.id.mainLocationView).setVisibility(View.VISIBLE);
    }

    public void inactivateLocationButton(){
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon));
        findViewById(R.id.mainLocationView).setVisibility(View.INVISIBLE);
    }

    public void activateReportButton(){
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon_active));
        findViewById(R.id.mainReportView).setVisibility(View.VISIBLE);
    }

    public void inactivateReportButton(){
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon));
        findViewById(R.id.mainReportView).setVisibility(View.INVISIBLE);
    }

    public void activateProfileButton(){
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon_active));
        findViewById(R.id.mainProfileView).setVisibility(View.VISIBLE);
    }

    public void inactivateProfileButton(){
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon));
        findViewById(R.id.mainProfileView).setVisibility(View.INVISIBLE);
    }


    public void toLocation() {
        activateLocationButton();
        inactivateReportButton();
        inactivateProfileButton();
    }

    public void toReport() {
        inactivateLocationButton();
        activateReportButton();
        inactivateProfileButton();

        toMakeReport();

    }

    public void toProfile() {
        inactivateLocationButton();
        inactivateReportButton();
        activateProfileButton();
    }

    public void toMyReports() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.VISIBLE);

    }

    public void toMakeReport() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));


        findViewById(R.id.reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
    }


    public void showStationDropdown(View view) {
        hideLineDropdown(view);
        findViewById(R.id.stationTextBox).setVisibility(View.VISIBLE);
        findViewById(R.id.stationText).setVisibility(View.VISIBLE);
        findViewById(R.id.lineDivider).setVisibility(View.VISIBLE);
    }

    public void hideStationDropdown(View view) {
        findViewById(R.id.stationTextBox).setVisibility(View.INVISIBLE);
        findViewById(R.id.stationText).setVisibility(View.INVISIBLE);
        findViewById(R.id.lineDivider).setVisibility(View.INVISIBLE);
    }


    public void showLineDropdown(View view) {
        hideStationDropdown(view);
        findViewById(R.id.lineDropdownText).setVisibility(View.VISIBLE);
        findViewById(R.id.lineDivider).setVisibility(View.VISIBLE);
        findViewById(R.id.lineSpinner).setVisibility(View.VISIBLE);
    }



    public void hideLineDropdown(View view) {
        findViewById(R.id.lineDropdownText).setVisibility(View.INVISIBLE);
        findViewById(R.id.lineDivider).setVisibility(View.INVISIBLE);
        findViewById(R.id.lineSpinner).setVisibility(View.INVISIBLE);
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

    public void makeReport(View view){




    }


}
