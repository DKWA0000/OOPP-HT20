package model;

import android.media.Image;

import java.util.Date;

/**
 * Abstracted information from ReportRoute and ReportStation.
 *
 * @see Incident
 *
 * @author: Lucas Karlsson.
 */

public abstract class AbstractReport {

    private int nControllants;
    private Date timeOfReport;
    private Image imageView;
    private Station station;
    private Reporter reporter;

    /**
     * Constructor for ReportRoute-object and ReportStation-object takes the parameters and passes it.
     *
     * @param noContr int
     * @param time Time
     * @param image Image
     * @param station Station
     * @param reporter Reporter
     */
    AbstractReport (int noContr, Date time, Image image, Station station, Reporter reporter) {
        this.nControllants = noContr;
        this.timeOfReport = time;
        this.imageView = image;
        this.station = station;
        this.reporter = reporter;
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

    public Reporter getReporter() {
        return reporter;
    }

    public void setNControllants(int n) {
        this.nControllants = n;
    }

    public String getInfo(){
        return "{nmbr: "+getnControllants() +
                ",time: " + getTimeOfReport() +
                ",station: " + getStation().getName() +
                "}";


    }

}