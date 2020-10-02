package com.example.planka;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import model.AbstractReport;
import model.Incident;
import model.MODEL;
import model.UpdateType;

import java.time.Instant;
import java.util.Date;


public class JaokActivity extends AppCompatActivity {

    private MODEL model;

    private String noContr;
    private Date time;
    private String image;
    private String station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        model = new MODEL(getAssets()); //TODO: Do not rely on an AssetManager

        startup();

    }

    /**
     * Methods to be called at startup
     */
    private void startup() {

        // to main tab
        toLocation();

        initSpinners();

        setLineDropdown(View.INVISIBLE);
        setWhenDropdown(View.GONE);


        initAutoFill();

        loadReports();
        loadIncidents();

        addModelObserver();
    }

    private void addModelObserver() {
        model.addObserver((UpdateType type) -> {

            if(type == UpdateType.NEW_INCIDENT){
                Incident i = model.getLatestIncident();

                IncidentView iw = new IncidentView(getBaseContext(), i);
                ((LinearLayout)findViewById(R.id.Incidentlist)).addView(iw);

            }

            if(type == UpdateType.NEW_REPORT){
                AbstractReport report = model.getLatestReport();
               createReportViewItem(report);
            }



        });
    }


    private void loadIncidents() {
        //TODO: implement and call at startup
    }


    /**
     * Passes a String[] with all station names to the AutoCompleteTextView box.
     */
    private void initAutoFill() {
        String[] list = model.getAllStations();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);

        ((AutoCompleteTextView)findViewById(R.id.stationTextBox)).setAdapter(adapter2);
    }

    /**
     * Populates the drop down lists with correct content
     */
    private void initSpinners() {

        Spinner spinner = (Spinner) findViewById(R.id.lineSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.whenSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.when_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.controllantsSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinnerListeners();

    }

    /**
     * Loads all reports from the model into the GUI
     */
    private void loadReports() {

        for (AbstractReport report: model.getAllReports()
             ) {

            createReportViewItem(report);
        }
    }

    /**
     * Creates a UserReportViewItem and adds it to the UserReportList
     * @param report data to be presented
     */
    private void createReportViewItem(AbstractReport report) {
        String station = report.getStation().getName();
        String time = report.getTimeOfReport().toString();
        String controllants = Integer.toString(report.getnControllants());

        UserReportViewItem urw = new UserReportViewItem(getBaseContext(),station,time,controllants);
        ((LinearLayout)findViewById(R.id.Reportlist)).addView(urw);
    }


    /**
     * Method used in GUI to determine what element has been clicked
     *
     * @param view element that is clicked
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


    /**
     * Updates the color of the buttons in the top menu
     */
    private void toLocation() {
        activateLocationButton();
        inactivateReportButton();
        inactivateProfileButton();
    }

    /**
     * Updates the color of the buttons in the top menu and shows the ReportFormView
     */
    private void toReport() {
        inactivateLocationButton();
        activateReportButton();
        inactivateProfileButton();

        toMakeReport();
    }

    /**
     * Updates the color of the buttons in the top menu
     */
    private void toProfile() {
        inactivateLocationButton();
        inactivateReportButton();
        activateProfileButton();
    }

    /**
     * Updates the color of the submenu-text and shows ReportFormView
     */
    private void toMyReports() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.VISIBLE);
    }

    /**
     * Updates the color of the submenu-text and shows MyReportsView
     */
    private void toMakeReport() {
        ((TextView)findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView)findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));

        findViewById(R.id.reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
    }


    /**
     * Sets the visibility of the chose station text field and the line drop down list
     *
     * @param visible
     */
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


    /**
     * Creates listeners to the drop down lists and the autocomplete text box
     */
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

        ((AutoCompleteTextView)findViewById(R.id.stationTextBox)).setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                station = adapterView.getItemAtPosition(i).toString();
            }

        });


    }


    /**
     * Creates a report in the model if there are values fetched by the listeners
     */
    private void makeReport(){

        String image = null; // Will maybe be implemented at a later stage

        if(((RadioButton)findViewById(R.id.nowRadio)).isChecked()){
            time = Date.from(Instant.now());
        }
        else{
            time = null; // not implemented
        }

        if(noContr != null && time != null && station != null) {
            model.makeStationReport(noContr, time, image, station);

            System.out.println("REPORT MADE");
        }
        System.out.println("REPORT MADE???????????");

    }


}