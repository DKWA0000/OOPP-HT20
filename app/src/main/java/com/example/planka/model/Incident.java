package com.example.planka.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class representing multiple reports on the same route and or station.
 *
 * @author Lucas Karlsson.
 * @see AbstractReport
 */

public class Incident extends Observable<Incident> implements Observer<AbstractReport>{

    private final ArrayList<AbstractReport> listReports = new ArrayList<>();
    private final IncidentType typeOfIncident;
    private int totalTrustFactor = 0;
    private int nominalTrustFactor;
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
     * @see AbstractReport
     */
    public AbstractReport getLatestReport() {
        return listReports.get(listReports.size() - 1);
    }

    /**
     * Add a report to the existing list of reports.
     *
     * @param r AbstractReport
     * @see AbstractReport
     */
    public void addReport(AbstractReport r) {
        if(listReports.size() > 0)
            getLatestReport().removeObserver(this);
        listReports.add(r);
        r.addObserver(this);
        System.out.println("ADDED REPORT");
        notifyObservers(this);
    }

    /**
     * Calculates the the average of controllants from all reports in an incident
     */
    public int getNumberOfControllants() {
        int averageAmountOfControllants = 0;
        for (AbstractReport r : listReports) {
            averageAmountOfControllants += r.getnControllants();
        }
        return averageAmountOfControllants / listReports.size();
    }

    /**
     * Calculates and updates the collective trustfactor of the incident.
     *
     * @param report AbstractReport
     * @see AbstractReport
     */
    public void updateNominalTrustFactor(AbstractReport report) {
        totalTrustFactor += report.getReporter().getTrustFactor();
        nominalTrustFactor = totalTrustFactor / listReports.size();
    }

    @Override
    public void notify(AbstractReport report) {
        notifyObservers(this);
    }

    /**
     * Method to get the nominal trust factor of an Incident.
     *
     * @return nominal trust factor of the Incident.
     */
    public int getNominalTrustFactor() {
        return nominalTrustFactor;
    }

    /**
     * Get the last active Station.
     *
     * @return last active Station.
     *
     * @see Station
     */
    public Station getLastActiveStation() {
        return getLatestReport().getStation();
    }

    /**
     * Get the last active Route.
     *
     * @return last active Route.
     *
     * @see Route
     */
    public Route getLastActiveRoute() {
        return getLatestReport().getRoute();
    }

    /**
     * Get time of the latest report.
     *
     * @return time of latest report.
     */
    public Date getTime() {
        return getLatestReport().getTimeOfReport();
    }

    /**
     * Get what type of Incident it is.
     *
     * @return type of Incident.
     *
     * @see IncidentType
     */
    public IncidentType getTypeOfIncident() {
        return typeOfIncident;
    }

    /**
     * Method to get all reports.
     *
     * @return List of all Reports.
     *
     * @see AbstractReport
     */
    public ArrayList<AbstractReport> getListReports() {
        return listReports;
    }

    /**
     * Method to get votes.
     *
     * @return number of votes.
     */
    public int getVotes() {
        return upVotes - downVotes;
    }

    /**
     * Method for upvoting.
     */
    public void upVote() {
        upVotes++;
        notifyObservers(this);
    }

    /**
     * Method to down vote.
     */
    public void downVote() {
        downVotes++;
        notifyObservers(this);
    }

}
