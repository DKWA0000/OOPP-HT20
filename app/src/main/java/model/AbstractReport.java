package model;

import android.media.Image;

import java.sql.Time;

public abstract class AbstractReport {

    private int nControllants;
    private Time timeOfReport;
    private Image imageView;
    private Station station;
    private Reporter reporter;

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

    public void setNControllants(int n) {
        this.nControllants = n;
    }


}
