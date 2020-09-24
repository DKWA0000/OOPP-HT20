package model;

import android.media.Image;

import java.sql.Time;

public class ReportRoute extends AbstractReport{

    private final Route route;

    ReportRoute(int noContr, Time time, Image image, Station station, Route route) {
        super(noContr, time, image, station);
        this.route = route;
    }
}