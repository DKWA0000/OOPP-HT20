package model;

import android.media.Image;

import java.sql.Time;

public class ReportStation extends AbstractReport {

    ReportStation(int noContr, Time time, Image image, Station station, Reporter reporter) {
        super(noContr, time, image, station, reporter);
    }
}
