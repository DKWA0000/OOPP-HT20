package model;

import android.media.Image;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MODEL {

    private List<Incident> activeIncidents = new ArrayList<Incident>();
    private List<Incident> inActiveIncidents = new ArrayList<Incident>();
    private Network network;

    public void MODEL(Network n){
        this.network = n;
    }

    public void createRouteReport(int noContr, Time time, Image image, Station station, Route route, Reporter reporter) {
        AbstractReport report = new ReportRoute(noContr, time, image, station, route, reporter);
        Incident incident = matchIncident(report);
        if(incident == null){
            incident = new Incident(IncidentType.ROUTE);
        }
        incident.addReport(report);
    }

    private Incident matchIncident(AbstractReport report) {
        for (int i = 0; i < activeIncidents.size(); i++) {
            Incident incident = activeIncidents.get(i);
            Station incidentLastActiveStation = incident.getLastActiveStation();
            Station reportStation = report.getStation();

            // Incident is too old
            if(incident.getTime().compareTo(report.getTimeOfReport()) > 10){ // 10 minutes (IDK)
                inActiveIncidents.add(incident);
                activeIncidents.remove(incident);
                i--;
                continue;
            }

            if(incidentLastActiveStation == reportStation){
                return incident;
            }
            // Check 2 stations in both directions
            for(Station s : network.getAdjacentStations(reportStation,2)){
                if(incidentLastActiveStation == reportStation){ // TODO: .equals() !?
                    return incident;
                }
            }
            incident.updateNominalTrustFactor(report); //where is this supposed to go?
        }

        return null;
    }

    public void createStationReport(int noContr, Time time, Image image, Station station, Reporter reporter) {
        AbstractReport report = new ReportStation(noContr, time, image, station, reporter);
        Incident incident = matchIncident(report);
        if(incident == null){
            incident = new Incident(IncidentType.STATION);
        }
        incident.addReport(report);

    }



}
