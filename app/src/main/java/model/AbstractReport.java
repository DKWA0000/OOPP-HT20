package model;

import android.media.Image;

import java.sql.Time;

/**
 * Abstracted information from ReportRoute and ReportStation.
 *
 * @see Incident
 *
 * @author: Lucas Karlsson.
 */

public abstract class AbstractReport {

    private Image imageView;
    private int nControllants;
    private final Time timeOfReport;
    private final Station station;
    private final Reporter reporter;

    /**
     * Constructor for ReportRoute-object and ReportStation-object takes the parameters and passes it.
     *
     * @param noContr int
     * @param time Time
     * @param image Image
     * @param station Station
     * @param reporter Reporter
     */
    AbstractReport (int noContr, Time time, Image image, Station station, Reporter reporter) {
        this.nControllants = noContr;
        this.timeOfReport = time;
        this.imageView = image;
        this.station = station;
        this.reporter = reporter;
    }

    public int getnControllants() {
        return nControllants;
    }

    public Time getTimeOfReport() {
        return timeOfReport;
    }

    public Station getStation() {
        return station;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public Image getImage() {
        return imageView;
    }

    public void setNControllants(int n) {
        this.nControllants = n;
    }

    public void setImageView(Image image) {
        this.imageView = image;
    }



}
