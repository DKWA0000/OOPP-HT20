package model;

import java.util.ArrayList;

public class Incident {
    private ArrayList<AbstractReport> listReports = new ArrayList<>();
    private final String typeOfIncident;
    private int nominalTrustFactor;

    Incident(String string) {
        this.typeOfIncident = string;
    }

    public String getTypeOfIncident() {
        return typeOfIncident;
    }

    public ArrayList<AbstractReport> getListReports() {
        return listReports;
    }


}
