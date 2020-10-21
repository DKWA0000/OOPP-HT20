package model;

import android.media.Image;

import java.util.Date;

/**
 * Class representing report holding station.
 *
 * @see AbstractReport
 *
 * @author Lucas Karlsson.
 */

public class ReportStation extends AbstractReport {
    IncidentType type;

    /**
     * Constructor for ReportRoute-object, takes the parameters and passes it.
     *
     * @param noContr int
     * @param time Time
     * @param image Image
     * @param station Station
     * @param reporter Reporter
     *
     * @see AbstractReport
     */
    public ReportStation(int noContr, Date time, Image image, Station station, Reporter reporter) {
        super(noContr, time, image, station, reporter, null);
        this.type = IncidentType.STATION;
    }

    public String toString(){
        return "";
    }
}