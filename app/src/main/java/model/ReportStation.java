package model;

import android.media.Image;

import java.sql.Time;

public class ReportStation extends AbstractReport {
    // TODO Use class station instead of node,
    ReportStation(int noContr, Time time, Image image) {
        super(noContr, time, image);
    }
}
