package model;

import android.media.Image;

import java.sql.Time;

/**
 * Class representing report holding route and station.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class ReportRoute extends AbstractReport{


    private final Route route;

    public ReportRoute(int noContr, Time time, Image image, Station station, Route route, Reporter reporter) {
        super(noContr, time, image, station, reporter);
        this.route = route;
    }
}