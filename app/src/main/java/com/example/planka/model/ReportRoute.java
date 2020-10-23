package com.example.planka.model;

import android.media.Image;
import java.util.Date;

/**
 * Class representing report holding route and station.
 *
 * @author Lucas Karlsson.
 * @see AbstractReport
 */

public class ReportRoute extends AbstractReport {

    /**
     * Constructor for ReportRoute-object, takes the parameters and passes it.
     *
     * @param noContr  int
     * @param time     Time
     * @param image    Image
     * @param route    Route
     * @param reporter Reporter
     * @see AbstractReport
     */
    public ReportRoute(int noContr, Date time, Image image, Route route, Reporter reporter) {
        super(noContr, time, image, null, reporter, route);
        setType(IncidentType.ROUTE);
    }

}