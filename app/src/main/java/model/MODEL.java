package model;

import android.content.res.AssetManager;
import android.media.Image;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MODEL extends Observable{

    Network network;
    FileReader fileReader;
    private ArrayList<Incident> IncidentList;
    private ArrayList<AbstractReport> reportsList = new ArrayList<AbstractReport>();

    public MODEL(AssetManager am){

        fileReader = new FileReader(am);
        HashMap<String, ArrayList> allRoutes = fileReader.getAllRoutes();
        network = new Network(allRoutes);

        IncidentList = new ArrayList<>();


        //for testing
        for (int i = 0; i < ((int)(Math.random()*1)+1); i++) {
            makeTemplateReport();
        }

    }



    /**
     * Makes a template report (ONLY FOR TESTING)
     */
    private void makeTemplateReport() {
        makeStationReport("3", null, null,  "Korsvägen");
    }

    public AbstractReport makeStationReport(String noContr, Date time, String image, String station){


        Reporter r = new Reporter("temp@google.com");
        int n = Integer.parseInt(noContr);


        Image i = null;

        Station s = network.getStation(station);

        if(time == null){
            time = Date.from(Instant.now());
        }

        AbstractReport report = new ReportStation(n,time,i,s,r);
        reportsList.add(report);

        notifyObservers(UpdateType.NEW_REPORT);

        // testing
        System.out.println(report.getInfo());

        getCorrespondingIncident(report).addReport(report);

        notifyObservers(UpdateType.NEW_INCIDENT);

        return report;
    }


    public String[] getAllStations() {
        return network.getAllStationNames();
    }


    public AbstractReport getLatestReport(){
        return reportsList.get(reportsList.size()-1);
    }

    public ArrayList<AbstractReport> getAllReports(){
        return reportsList;
    }

    public int getIncidentCount(){ return IncidentList.size(); }

    public Incident getIncident(int index){
        if(index >= 0 && index < IncidentList.size())
            return IncidentList.get(index);
        else
            return null;
    }


    public Incident getLatestIncident() {

       return getIncident(getIncidentCount()-1);
    }

    public Incident getCorrespondingIncident(AbstractReport report) {
        for (Incident i : IncidentList) {
            if (i.getTypeOfIncident() == report.getType() && i.getLastActiveStation().equals(report.getStation())) {
                return i;
            }
        }
        Incident incident = new Incident(report.getType());
        return incident;
    }
}
