package model;

import android.media.Image;

import java.sql.Time;

public abstract class AbstractReport {

    private int nControllants;
    private Time timeOfReport;
    private Image imageView;
    private Station station;

    AbstractReport (int noContr, Time time, Image image, Station station) {
        this.nControllants = noContr;
        this.timeOfReport = time;
        this.imageView = image;
        this.station = station;
    }

    public int getnControllants() {
        return nControllants;
    }
    public Time getTimeOfReport() { return timeOfReport; }
    public Station getStation() {
        return station;
    }

    public void setnControllants(int n) {
        this.nControllants = n;
    }


}
