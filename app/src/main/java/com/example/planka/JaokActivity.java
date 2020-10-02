package com.example.planka;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import model.AbstractReport;
import model.Incident;
import model.MODEL;


public class JaokActivity extends AppCompatActivity {

    MODEL model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        model = new MODEL(getAssets());

        // main tab
        toLocation();

        initSpinners();
        spinnerListeners();

        setLineDropdown(View.INVISIBLE);
        setWhenDropdown(View.GONE);


        initAutoFill();

        loadReports();
        loadIncidents();

        model.addObserver(() -> {

            AbstractReport report = model.getLatestReport();

            UserReportView urw = new UserReportView(getBaseContext(),report.getStation().getName(),report.getTimeOfReport().toString(),report.getnControllants());
            ((LinearLayout)findViewById(R.id.Reportlist)).addView(urw);

            Incident i = model.getLatestIncident();

            IncidentView iw = new IncidentView(getBaseContext(), i);
            ((LinearLayout)findViewById(R.id.Incidentlist)).addView(iw);


        });

    }

    private void loadIncidents() {
        //TODO:
    }

    private void initAutoFill() {
        String[] list = model.getAllStations();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);

        ((AutoCompleteTextView)findViewById(R.id.stationTextBox)).setAdapter(adapter2);
    }

    private void initSpinners() {

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

    }

    private void loadReports() {

        for (AbstractReport report: model.getAllReports()
             ) {
            UserReportView urw = new UserReportView(getBaseContext(),report.getStation().getName(),report.getTimeOfReport().toString(),report.getnControllants());
            ((LinearLayout)findViewById(R.id.Reportlist)).addView(urw);

        }
    }


    /**
     * Method used in GUI to determine what element has been clicked
     *
     * @param view
     */
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
        if(view == findViewById(R.id.stationRadio)){
            setStationDropDown(View.VISIBLE);
        }
        if(view == findViewById(R.id.tramRadio)){
            setStationDropDown(View.INVISIBLE);
        }
        if(view == findViewById(R.id.nowRadio)){
            setWhenDropdown(View.GONE);
        }
        if(view == findViewById(R.id.pastRadio)){
            setWhenDropdown(View.VISIBLE);
        }
        if(view == findViewById(R.id.makeReportButton)){
            makeReport();
        }


    }


    private void activateLocationButton(){
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon_active));
        findViewById(R.id.mainLocationView).setVisibility(View.VISIBLE);
    }

    private void inactivateLocationButton(){
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon));
        findViewById(R.id.mainLocationView).setVisibility(View.INVISIBLE);
    }

    private void activateReportButton(){
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon_active));
        findViewById(R.id.mainReportView).setVisibility(View.VISIBLE);
    }

    private void inactivateReportButton(){
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon));
        findViewById(R.id.mainReportView).setVisibility(View.INVISIBLE);
    }

    private void activateProfileButton(){
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon_active));
        findViewById(R.id.mainProfileView).setVisibility(View.VISIBLE);
    }

    private void inactivateProfileButton(){
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon));
        findViewById(R.id.mainProfileView).setVisibility(View.INVISIBLE);
    }


    private void toLocation() {
        activateLocationButton();
        inactivateReportButton();
        inactivateProfileButton();
    }

    private void toReport() {
        inactivateLocationButton();
        activateReportButton();
        inactivateProfileButton();

        toMakeReport();
    }

    private void toProfile() {
        inactivateLocationButton();
        inactivateReportButton();
        activateProfileButton();
    }

    private void toMyReports() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.VISIBLE);
    }

    private void toMakeReport() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));

        findViewById(R.id.reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
    }


    private void setStationDropDown(int visible) {

        if(visible == View.VISIBLE)
           setLineDropdown(View.INVISIBLE);
        else
            setLineDropdown(View.VISIBLE);

        findViewById(R.id.stationTextBox).setVisibility(visible);
        findViewById(R.id.stationText).setVisibility(visible);
        findViewById(R.id.lineDivider).setVisibility(visible);
    }

    private void setLineDropdown(int visible) {
        findViewById(R.id.lineDropdownText).setVisibility(visible);
        findViewById(R.id.lineDivider).setVisibility(visible);
        findViewById(R.id.lineSpinner).setVisibility(visible);
    }

    private void setWhenDropdown(int visible) {
        findViewById(R.id.whenDropdownText).setVisibility(visible);
        findViewById(R.id.whenDivider).setVisibility(visible);
        findViewById(R.id.whenSpinner).setVisibility(visible);
    }

    String noContr;
    String time;
    String image;
    String station;

    private void spinnerListeners(){
        ((Spinner)findViewById(R.id.controllantsSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noContr = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                noContr = null;
            }
        });

        ((Spinner)findViewById(R.id.whenSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                time = null;
            }
        });

        ((AutoCompleteTextView)findViewById(R.id.stationTextBox)).setOnItemClickListener(new AdapterView.OnItemClickListener() {




            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                station = adapterView.getItemAtPosition(i).toString();
            }


        });


    }


    private void makeReport(){
        String image = null;
        model.makeStationReport(noContr,time,image,station);
    }


}
