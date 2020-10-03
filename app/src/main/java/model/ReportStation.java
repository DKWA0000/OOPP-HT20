package model;

import android.media.Image;

import java.util.Date;

/**
 * Class representing report holding station.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class ReportStation extends AbstractReport {
    IncidentType type;

    public ReportStation(int noContr, Date time, Image image, Station station, Reporter reporter) {
        super(noContr, time, image, station, reporter);
        System.out.println("REPORT MADE");
        this.type = IncidentType.STATION;
    }

    public String toString(){
        return "";
    }
}