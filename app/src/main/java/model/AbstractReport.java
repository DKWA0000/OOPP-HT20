package model;

import android.media.Image;

import java.util.Date;

/**
 * Abstracted information from ReportRoute and ReportStation.
 *
 * @author Lucas Karlsson.
 * @see Incident
 */

public abstract class AbstractReport {

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

    public int getnControllants() {
        return nControllants;
    }

    public Date getTimeOfReport() {
        return timeOfReport;
    }

    public Station getStation() {
        return station;
    }

    public Route getRoute() {
        return route;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public IncidentType getType() {
        return type;
    }

    public void setType(IncidentType type) {
        this.type = type;
    }

    public void setNControllants(int n) {
        this.nControllants = n;
    }

    public String getInfo() {
        return "{nmbr: " + getnControllants() +
                ",time: " + getTimeOfReport() +
                ",station: " + getStation().getName() +
                "}";
    }

}