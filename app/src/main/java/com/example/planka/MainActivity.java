package com.example.planka;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import model.*;
import service.FileReader;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @author Lucas Karlsson
 * @see model.Incident
 * @see model.MODEL
 */

public class MainActivity extends AppCompatActivity {

    private MODEL model;
    private String noContr;
    private String editContr;
    private String image;
    private String station;
    private String route;
    private String userRoute;
    private LinearLayout Incidentlist;
    private SearchView searchView;
    private ArrayList<View> copyOfIncidentlist;
    public static final String NOTIFICATIONCHANNELID = "10001";
    private final static String DEFAULTCHANNELID = "default";

    /**
     * Method running when creating a MainActivity
     *
     * @param savedInstanceState Bundle
     * @see MODEL
     * @see Incident
     * @see AbstractReport
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        FileReader fileReader = new FileReader(this.getAssets());
        HashMap<String, ArrayList> allRoutes = fileReader.getAllRoutes();
        Incidentlist = findViewById(R.id.Incidentlist);
        copyOfIncidentlist = new ArrayList<>();
        searchView = findViewById(R.id.searchView);
        model = new MODEL(allRoutes);
        startup();
    }

    /**
     * When a completely new incident on a new location is created this method alerts the user with a notification.
     *
     * @see Intent
     * @see NotificationManager
     */
    public void sendNotification() {
        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, DEFAULTCHANNELID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Ny Rapport!")
                .setContentIntent(resultIntent)
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentText("Hej! En ny rapport har kommit in, öppna appen för att kolla!");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIONCHANNELID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATIONCHANNELID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    /**
     * Sends the User a notification when a Route they have selected is affected by controllers.
     */
    public void sendNotificationRoute() {
        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, DEFAULTCHANNELID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Linjen påverkas av kontrollanter!")
                .setContentIntent(resultIntent)
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentText("Hej! Enligt en rapport påverkas denna linje av kontrollanter!");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIONCHANNELID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATIONCHANNELID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    /**
     * Method that handles input in searchView and runs when character is added or deleted.
     *
     * @param menu Menu
     * @see MODEL
     * @see Incident
     * @see IncidentView
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (int i = 0; i < Incidentlist.getChildCount(); i++) {
                    if (!copyOfIncidentlist.contains(Incidentlist.getChildAt(i))) {
                        copyOfIncidentlist.add(Incidentlist.getChildAt(i));
                    }
                }
                Incidentlist.removeAllViews();

                if (newText.isEmpty()) {
                    Incidentlist.removeAllViews();
                    for (View view : copyOfIncidentlist) {
                        Incidentlist.addView(view);
                    }
                } else {
                    for (View view : copyOfIncidentlist) {
                        String currentView = ((IncidentView) view).getLocationText().getText().toString();
                        if (currentView.contains(newText)) {
                            Incidentlist.addView(view);
                        }
                    }
                }
                return false;
            }
        });
        return true;
    }

    /**
     * Method that runs on startup.
     */
    private void startup() {
        toLocation();
        initSpinners();
        setLineDropdown(View.INVISIBLE);
        setWhenDropdown();
        initAutoFill();
        loadReports();
        loadIncidents();
        addModelObserver();
    }

    /**
     * Method that runs when a update is que:ed.
     *
     * @see UpdateType
     * @see MODEL
     */
    private void addModelObserver() {
        model.addObserver((UpdateType type) -> {

            if (type == UpdateType.NEW_INCIDENT) {
                Incident i = model.getLatestIncident();
                DateFormat outputformat = new SimpleDateFormat("HH:mm:ss - dd/MM/yy");
                sendNotification();
                if (!model.isLatestReportRoute()) {
                    String station = i.getLastActiveStation().getName();
                    String nCont = String.valueOf(i.getListReports().get(0).getnControllants());
                    String timee = outputformat.format(i.getListReports().get(0).getTimeOfReport());
                    IncidentView iw = new IncidentView(getBaseContext(), station, nCont, timee);
                    Incidentlist.addView(iw);

                } else if (model.isLatestReportRoute()) {
                    String route = i.getLastActiveRoute().getLine();
                    String nCont = String.valueOf(i.getListReports().get(0).getnControllants());
                    String timee = outputformat.format(i.getListReports().get(0).getTimeOfReport());
                    IncidentView iw = new IncidentView(getBaseContext(), route, nCont, timee);
                    Incidentlist.addView(iw);
                }
            }

            if (type == UpdateType.NEW_REPORT) {
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ((AutoCompleteTextView) findViewById(R.id.stationTextBox)).setAdapter(adapter2);
    }

    /**
     * Populates the drop down lists with correct content
     */
    private void initSpinners() {
        Spinner spinner = findViewById(R.id.lineSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner = findViewById(R.id.userRoute);
        adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner = findViewById(R.id.whenSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.when_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = findViewById(R.id.controllantsSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.lines_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinnerListeners();

    }

    /**
     * Loads all reports from the model into the GUI
     */
    private void loadReports() {
        for (AbstractReport report : model.getAllReports()) {
            createReportViewItem(report);
        }
    }

    /**
     * Creates a UserReportViewItem and adds it to the UserReportList
     *
     * @param report data to be presented
     */
    private void createReportViewItem(AbstractReport report) {
        UserReportViewItem urw;
        String time = report.getTimeOfReport().toString();
        String controllants = Integer.toString(report.getnControllants());
        if (model.isLatestReportRoute()) {
            String route = report.getRoute().getLine();
            urw = new UserReportViewItem(getBaseContext(), route, time, controllants);
        } else {
            String station = report.getStation().getName();
            urw = new UserReportViewItem(getBaseContext(), station, time, controllants);
        }
        ((LinearLayout) findViewById(R.id.Reportlist)).addView(urw);
    }


    /**
     * Method used in GUI to determine what element has been clicked
     *
     * @param view element that is clicked
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locationsButton:
                toLocation();
                break;

            case R.id.reportsButton:
                toReport();
                break;

            case R.id.profileButton:
                toProfile();
                break;

            case R.id.makeReportText:
                toMakeReport();
                break;

            case R.id.myReportsText:
                toMyReports();
                break;

            case R.id.stationRadio:
                setStationDropDown(View.VISIBLE);
                break;

            case R.id.tramRadio:
                setStationDropDown(View.INVISIBLE);
                break;

            case R.id.nowRadio:
                setWhenDropdown();
                break;

            case R.id.pastRadio:
                setStationDropDown(View.VISIBLE);
                break;

            case R.id.makeReportButton:
                makeReport();
                break;

            case R.id.updateButton:
                toEditReport();
                break;

            case R.id.userRoute:
                setLineDropdown(View.VISIBLE);
                break;

            case R.id.userSave:
                saveProfile();
                break;
        }
    }


    private void activateLocationButton() {
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon_active));
        findViewById(R.id.mainLocationView).setVisibility(View.VISIBLE);
    }

    private void inactivateLocationButton() {
        findViewById(R.id.locationsButton).setForeground(getDrawable(R.drawable.location_icon));
        findViewById(R.id.mainLocationView).setVisibility(View.INVISIBLE);
    }

    private void activateReportButton() {
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon_active));
        findViewById(R.id.mainReportView).setVisibility(View.VISIBLE);
    }

    private void inactivateReportButton() {
        findViewById(R.id.reportsButton).setForeground(getDrawable(R.drawable.report_icon));
        findViewById(R.id.mainReportView).setVisibility(View.INVISIBLE);
    }

    private void activateProfileButton() {
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon_active));
        findViewById(R.id.mainProfileView).setVisibility(View.VISIBLE);
    }

    private void inactivateProfileButton() {
        findViewById(R.id.profileButton).setForeground(getDrawable(R.drawable.profile_icon));
        findViewById(R.id.mainProfileView).setVisibility(View.INVISIBLE);
    }


    /**
     * Updates the color of the buttons in the top menu
     */
    private void toLocation() {
        searchView.setQuery("", true);
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
        setLineDropdown(View.VISIBLE);
        inactivateLocationButton();
        inactivateReportButton();
        activateProfileButton();
    }

    /**
     * Updates the color of the submenu-text and shows ReportFormView
     */
    private void toMyReports() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.VISIBLE);
        findViewById(R.id.editReportView).setVisibility(View.INVISIBLE);
    }

    /**
     * Updates the color of the submenu-text and shows MyReportsView
     */
    private void toMakeReport() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));

        findViewById(R.id.reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
        findViewById(R.id.editReportView).setVisibility(View.INVISIBLE);
    }

    /**
     * Shows EditReportView
     */
    private void toEditReport() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));

        findViewById(R.id.reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.myReportsView).setVisibility(View.INVISIBLE);
        findViewById(R.id.editReportView).setVisibility(View.VISIBLE);
    }

    /**
     * Sets the visibility of the chose station text field and the line drop down list
     *
     * @param visible Int
     */
    private void setStationDropDown(int visible) {

        if (visible == View.VISIBLE)
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
        findViewById(R.id.userRoute).setVisibility(visible);
    }

    private void setWhenDropdown() {
        findViewById(R.id.whenDropdownText).setVisibility(View.GONE);
        findViewById(R.id.whenDivider).setVisibility(View.GONE);
        findViewById(R.id.whenSpinner).setVisibility(View.GONE);
    }


    /**
     * Creates listeners to the drop down lists and the autocomplete text box
     */
    private void spinnerListeners() {
        ((Spinner) findViewById(R.id.controllantsSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noContr = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                noContr = null;
            }
        });

        ((AutoCompleteTextView) findViewById(R.id.stationTextBox)).setOnItemClickListener((adapterView, view, i, l) -> station = adapterView.getItemAtPosition(i).toString());

        ((Spinner) findViewById(R.id.lineSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                route = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                route = null;
            }
        });

        ((Spinner) findViewById(R.id.userRoute)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userRoute = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                userRoute = null;
            }
        });

    }


    /**
     * Creates a report in the model if there are values fetched by the listeners
     */
    private void makeReport() {
        String image = null; // Will maybe be implemented at a later stage

        Date time;
        if (((RadioButton) findViewById(R.id.nowRadio)).isChecked()) {
            time = Date.from(Instant.now());
        } else {
            time = null; // not implemented
        }

        if (((RadioButton) findViewById(R.id.stationRadio)).isChecked() && noContr != null && time != null && station != null) {
            model.makeStationReport(noContr, time, image, station);
            if(userRoute != null && model.userRouteImpacted(userRoute)) {
                sendNotificationRoute();
            }
        } else if (((RadioButton) findViewById(R.id.tramRadio)).isChecked() && noContr != null && time != null && route != null) {
            model.makeRouteReport(noContr, time, image, route);
            if(userRoute != null && model.userRouteImpacted(userRoute)) {
                sendNotificationRoute();
            }
        }
    }

    /**
     * Save User Profile.
     */
    private void saveProfile(){
        if(userRoute != null){
            checkUserRoute(userRoute);
        }
    }

    private void checkUserRoute(String userRoute){
        if(model.userRouteImpacted(userRoute)) {
            sendNotificationRoute();
        }
    }

    /**
     * Edits a report if editContr is not null
     * TODO: identify which report should be edited
     */
    private void editReport() {
        if (editContr != null) {
            //[Report].setNControllants(noContr);
        }
    }

    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }
}
