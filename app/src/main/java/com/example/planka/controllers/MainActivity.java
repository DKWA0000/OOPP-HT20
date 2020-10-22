package com.example.planka.controllers;

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
import com.example.planka.R;
import com.example.planka.model.*;
import com.example.planka.service.FileReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class responsible for the view that displays Incidents.
 *
 * @author Lucas Karlsson
 * @see Incident
 * @see MODEL
 */

public class MainActivity extends AppCompatActivity implements Observer<UpdateType> {

    private MODEL model;
    private String noContr;
    private String image;
    private String station;
    private String route;
    private String userRoute;
    private LinearLayout Incidentlist;
    private SearchView searchView;
    private ArrayList<View> copyOfIncidentlist;
    private static final String NOTIFICATIONCHANNELID = "10001";
    private final static String DEFAULTCHANNELID = "default";
    private String editContr;

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
        model.addObserver(this);
        startup();
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
    }

    /**
     * Method that runs when a update is que:ed.
     *
     * @see UpdateType
     * @see MODEL
     */


    @Override
    public void notify(UpdateType type) {
        switch (type) {
            case NEW_INCIDENT:
                Incident i = model.getLatestIncident();
                IncidentView iw = new IncidentView(getBaseContext(), i);
                Incidentlist.addView(iw);
                break;

            case NEW_REPORT:
                AbstractReport report = model.getLatestReport();
                createReportViewItem(report);
                break;
        }
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
        adapter = ArrayAdapter.createFromResource(this, R.array.controllants_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = findViewById(R.id.erw_controllantsEditSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.controllants_array, android.R.layout.simple_spinner_item);
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

        View.OnClickListener listener = (View view) -> {
            model.setEditReport(report);
            toEditReport();
        };

        UserReportViewItem urw = new UserReportViewItem(getBaseContext(), report, listener);

        ((LinearLayout) findViewById(R.id.mrw_reportList)).addView(urw);
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

            case R.id.erw_closeButton:
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

            case R.id.erw_submitButton:
                editReport();
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
        inactivateLocationButton();
        inactivateReportButton();
        activateProfileButton();
        setUserRouteDropDown(View.VISIBLE);
    }

    /**
     * Updates the color of the submenu-text and shows ReportFormView
     */
    private void toMyReports() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.rw_reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.rw_myReportsView).setVisibility(View.VISIBLE);
        findViewById(R.id.rw_editReportView).setVisibility(View.INVISIBLE);
    }

    /**
     * Updates the color of the submenu-text and shows MyReportsView
     */
    private void toMakeReport() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.text_gray));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.smurf));

        findViewById(R.id.rw_reportFormView).setVisibility(View.VISIBLE);
        findViewById(R.id.rw_myReportsView).setVisibility(View.INVISIBLE);
        findViewById(R.id.rw_editReportView).setVisibility(View.INVISIBLE);
    }

    /**
     * Shows EditReportView
     */
    private void toEditReport() {
        ((TextView) findViewById(R.id.myReportsText)).setTextColor(getColor(R.color.smurf));
        ((TextView) findViewById(R.id.makeReportText)).setTextColor(getColor(R.color.text_gray));

        findViewById(R.id.rw_reportFormView).setVisibility(View.INVISIBLE);
        findViewById(R.id.rw_myReportsView).setVisibility(View.INVISIBLE);
        findViewById(R.id.rw_editReportView).setVisibility(View.VISIBLE);
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
    }

    private void setUserRouteDropDown(int visible) {
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

        ((Spinner) findViewById(R.id.erw_controllantsEditSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editContr = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editContr = null;
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
            if (userRoute != null && model.userRouteImpacted(userRoute)) {
                sendNotificationRoute();
            }
        } else if (((RadioButton) findViewById(R.id.tramRadio)).isChecked() && noContr != null && time != null && route != null) {
            model.makeRouteReport(noContr, time, image, route);
            if (userRoute != null && model.userRouteImpacted(userRoute)) {
                sendNotificationRoute();
            }
        }
    }

    /**
     * Edits a report if editContr is not null
     */
    private void editReport() {
        if (editContr != null) {
            model.editReport(Integer.parseInt(editContr));
        }
        toMyReports();
    }

    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }


}
