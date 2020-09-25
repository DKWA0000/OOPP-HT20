package model;

import android.media.Image;

import java.sql.Time;

/**
 * Class representing report holding station.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class ReportStation extends AbstractReport {

    ReportStation(int noContr, Time time, Image image, Station station, Reporter reporter) {
        super(noContr, time, image, station, reporter);
    }
}
