package model;

import android.content.res.AssetManager;
import android.media.Image;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class MODEL extends Observable{

    Network network;
    private ArrayList<Incident> IncidentList;
    private ArrayList<AbstractReport> reportsList = new ArrayList<AbstractReport>();

    public MODEL(AssetManager am){

        network = new Network(am);

        IncidentList = new ArrayList<>();

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

        if (correspondingIncidentExists(report)) {
            getCorrespondingIncident(report).addReport(report);
            return report;
        }
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

    public boolean correspondingIncidentExists(AbstractReport report) {
        for (Incident i : IncidentList) {
            if (i.getTypeOfIncident() == report.getType() && i.getLastActiveStation().equals(report.getStation())) {
                return true;
            }
        }
        return false;
    }

    public Incident getCorrespondingIncident(AbstractReport report) {
        for (Incident i : IncidentList) {
            if (i.getTypeOfIncident() == report.getType() && i.getLastActiveStation().equals(report.getStation())) {
                return i;
            }
        }
        Incident incident = new Incident(report.getType());
        IncidentList.add(incident);
        return incident;
    }
}
