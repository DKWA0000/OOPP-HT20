package model;

import android.content.res.AssetManager;
import android.media.Image;

import java.time.Instant;
import java.util.Date;

public class MODEL {

    Network network;

    public MODEL(AssetManager am){
        System.out.println("WTF0");

        network = new Network(am);
        System.out.println("WTF2");
        network.getStation("Korsvägen");
    }

    public void makeStationReport(String noContr, String time, String image, String station){


        Reporter r = new Reporter("temp@google.com");
        System.out.println("CONTR:" + station);
        int n = Integer.parseInt(noContr);
        Date t;
        if(time == null || time == "")
           t = Date.from(Instant.now());
        else
            t = Date.from(Instant.parse(time));

        Image i = null;
        System.out.println("STATION NAME: " + station);
        Station s = network.getStation(station);
        System.out.println("STATIONOBJ; " +  s.getName());
        AbstractReport report = new ReportStation(n,t,i,s,r);

        System.out.println(report.getInfo());

    }


    public String[] getAllStations() {
        return network.getAllStations();
    }
}
