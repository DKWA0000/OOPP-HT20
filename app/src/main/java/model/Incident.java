
package model;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Class representing multiple reports on the same route and or station.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class Incident {

    private ArrayList<AbstractReport> listReports = new ArrayList<>();
    private final IncidentType typeOfIncident;
    private int nominalTrustFactor;
    private int upVotes = 0;
    private int downVotes = 0;

    /**
     * Constructor of Incident-object takes the IncidentType and passes it.
     *
     * @param type IncidentType
     */
    Incident(IncidentType type) {
        this.typeOfIncident = type;
        this.nominalTrustFactor = 0;
    }

    /**
     * Returns the latest report added to the listReport arraylist
     */
    private AbstractReport latestReport(){
     return listReports.get(listReports.size()-1);
    }

    /**
     * Add a report to the existing list of reports.
     *
     * @param r AbstractReport
     *
     * @see AbstractReport
     */
    public void addReport(AbstractReport r){
        listReports.add(r);
    }

    /**
     * Calculates and updates the collective trustfactor of the incident.
     *
     * @param report AbstractReport
     *
     * @see AbstractReport
     */
    //TODO Add user trustfactor and calculate nominal.
    public void updateNominalTrustFactor(AbstractReport report) { }

    public Station getLastActiveStation(){
        return latestReport().getStation();
    }

    public Time getTime() {
        return latestReport().getTimeOfReport();
    }

    public IncidentType getTypeOfIncident() {
        return typeOfIncident;
    }

    public ArrayList<AbstractReport> getListReports() {
        return listReports;
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
}
