package model;

import android.media.Image;

import java.sql.Time;

public class ReportRoute extends AbstractReport{

    private final Route route;

    ReportRoute(int noContr, Time time, Image image, Station station, Route route, Reporter reporter) {
        super(noContr, time, image, station, reporter);
        this.route = route;
    }
}