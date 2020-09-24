package model;

import android.media.Image;

import java.sql.Time;

public class ReportStation extends AbstractReport {

    ReportStation(int noContr, Time time, Image image, Station station) {
        super(noContr, time, image, station);
    }
}
