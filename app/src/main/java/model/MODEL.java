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
        network.getStation("Korsvägen");

        IncidentList = new ArrayList<>();


        //for testing
        for (int i = 0; i < ((int)(Math.random()*3)+1); i++) {
            makeTemplateReport();
        }

    }


    // For testing
    private void makeTemplateReport() {
        makeStationReport("3", null, null,  "Korsvägen");
    }

    public void makeStationReport(String noContr, String time, String image, String station){


        Reporter r = new Reporter("temp@google.com");
        int n = Integer.parseInt(noContr);
        Date t;
        if(time == null || time == "")
           t = Date.from(Instant.now());
        else
            t = Date.from(Instant.parse(time));

        Image i = null;
        Station s = network.getStation(station);
        AbstractReport report = new ReportStation(n,t,i,s,r);


        System.out.println(report.getInfo());
        reportsList.add(report);

        Incident inc = new Incident("Station");
        IncidentList.add(inc);
        notifyObservers();
    }


    public String[] getAllStations() {
        return network.getAllStations();
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
}
