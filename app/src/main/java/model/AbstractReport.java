package model;

import android.media.Image;

import java.sql.Time;

public abstract class AbstractReport {

    //TODO Abstract station object

    private int nControllants;
    private Time timeOfReport;
    private Image imageView;

    AbstractReport (int noContr, Time time, Image image) {
        this.nControllants = noContr;
        this.timeOfReport = time;
        this.imageView = image;
    }

    public int getnControllants() {
        return nControllants;
    }
    public Time getTimeOfReport() { return timeOfReport; }
    public void setnControllants(int n) {
        this.nControllants = n;
    }

}
