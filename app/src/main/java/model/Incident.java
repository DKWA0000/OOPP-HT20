
package model;

import java.util.ArrayList;
import java.util.Date;

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
    private int totalTrustFactor = 0;
    private int nominalTrustFactor = 0;
    private int upVotes = 0;
    private int downVotes = 0;

    /**
     * Constructor of Incident-object takes the IncidentType and passes it.
     *
     * @param type IncidentType
     */
    public Incident(IncidentType type) {
        this.typeOfIncident = type;
        this.nominalTrustFactor = 0;
    }

    /**
     * Gets the latest report added to a Incident.
     *
     * @return AbstractReport
     *
     * @see AbstractReport
     */
    public AbstractReport latestReport(){
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
    public void updateNominalTrustFactor(AbstractReport report) {
        totalTrustFactor += report.getReporter().getTrustFactor();
        nominalTrustFactor = totalTrustFactor/listReports.size();
    }

    public int getNominalTrustFactor() {
        return nominalTrustFactor;
    }

    public Station getLastActiveStation(){ return latestReport().getStation(); }

    public Route getLastActiveRoute() { return latestReport().getRoute(); }

    public Date getTime() {
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
