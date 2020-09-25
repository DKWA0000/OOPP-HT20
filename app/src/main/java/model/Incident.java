
package model;

import java.sql.Time;
import java.util.ArrayList;

public class Incident {


    private ArrayList<AbstractReport> listReports = new ArrayList<>();
    private final IncidentType typeOfIncident;
    private int nominalTrustFactor;
    private int upVotes = 0;
    private int downVotes = 0;

    Incident(IncidentType type) {
        this.typeOfIncident = type;
        this.nominalTrustFactor = 0;
    }


    public int getVotes(){
        return upVotes-downVotes;
    }

    public void upVote(){ //TODO: collect user-data and cap to only on vote per user?
        upVotes++;
    }

    public void downVote(){
        downVotes++;
    }

    private AbstractReport latestReport(){
     return listReports.get(listReports.size()-1);
    }

    public Station getLastActiveStation(){
        return latestReport().getStation();
    }

    public void addReport(AbstractReport r){
        listReports.add(r);
    }

    public IncidentType getTypeOfIncident() {
        return typeOfIncident;
    }

    public ArrayList<AbstractReport> getListReports() {
        return listReports;
    }

    //TODO Add user trustfactor and calculate nominal.
    public void updateNominalTrustFactor(AbstractReport report) {

    }

    public Time getTime() {
        return latestReport().getTimeOfReport();
    }
}
