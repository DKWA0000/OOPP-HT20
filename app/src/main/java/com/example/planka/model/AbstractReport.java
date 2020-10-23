package com.example.planka.model;

import android.media.Image;

import java.util.Date;

/**
 * Abstracted information from ReportRoute and ReportStation.
 *
 * @author Lucas Karlsson.
 * @see Incident
 */

public abstract class AbstractReport extends Observable<AbstractReport> {

    private int nControllants;
    private Date timeOfReport;
    private Image imageView;
    private Station station;
    private Reporter reporter;
    private IncidentType type;
    private final Route route;

    /**
     * Constructor for ReportRoute-object and ReportStation-object takes the parameters and passes it.
     *
     * @param noContr  int
     * @param time     Time
     * @param image    Image
     * @param station  Station
     * @param reporter Reporter
     */
    AbstractReport(int noContr, Date time, Image image, Station station, Reporter reporter, Route route) {
        this.nControllants = noContr;
        this.timeOfReport = time;
        this.imageView = image;
        this.station = station;
        this.reporter = reporter;
        this.route = route;
    }

    /**
     * Method to get controllers in a report.
     *
     * @return number of controllers in a report.
     */
    public int getnControllants() {
        return nControllants;
    }

    /**
     * Method to get the time of a report.
     *
     * @return Date of report.
     */
    public Date getTimeOfReport() {
        return timeOfReport;
    }

    /**
     * Method to get the Station of a report.
     *
     * @return Station of report.
     *
     * @see Station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Method to get the Route of a report.
     *
     * @return Route of a report.
     *
     * @see Route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Method to get the reporter of a Report.
     *
     * @return Reporter of the report.
     *
     * @see Reporter
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Method to get the incident of the Report.
     *
     * @return type of the report.
     *
     * @see IncidentType
     */
    public IncidentType getType() {
        return type;
    }

    /**
     * Method to set the type of a report.
     *
     * @param type to set to.
     *
     * @see IncidentType
     */
    public void setType(IncidentType type) {
        this.type = type;
    }

    /**
     * Method to set the number of controllants on a report.
     *
     * @param n number of controllants.
     */
    public void setNControllants(int n) {
        this.nControllants = n;
        notifyObservers(this);
    }

    /**
     * Method to get info from a report.
     * @return String containing info on a report.
     */
    public String getInfo() {
        return "{nmbr: " + getnControllants() +
                ",time: " + getTimeOfReport() +
                ",station: " + getStation().getName() +
                "}";
    }
}