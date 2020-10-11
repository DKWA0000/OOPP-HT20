package model;

import android.media.Image;

import java.util.Date;

/**
 * Class representing report holding route and station.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class ReportRoute extends AbstractReport{

    private IncidentType type;


    public ReportRoute(int noContr, Date time, Image image, Station station, Route route, Reporter reporter) {
        super(noContr, time, image, station, reporter, route);
        this.type = IncidentType.ROUTE;
    }

}