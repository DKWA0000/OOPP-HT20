package model;

import android.content.res.AssetManager;
import android.media.Image;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MODEL extends Observable{

    Network network;
    private ArrayList<Incident> IncidentList;
    private ArrayList<Incident> IncidentListRoute;
    private ArrayList<AbstractReport> reportsList = new ArrayList<>();
    public boolean latestReportIsRoute = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MODEL(HashMap<String, ArrayList> fileContent){

        network = new Network(fileContent);

        IncidentList = new ArrayList<>();

        IncidentListRoute = new ArrayList<>();

    }

    /**
     * Method to create a new Report for a specific station.
     *
     * @param noContr Number of reported controllers.
     * @param time Time the Report is being made.
     * @param image Attached Image.
     * @param station Station the User is creating a Report for.
     * @param it What type of incident the Report is.
     *
     * @return A new Report.
     *
     * @see AbstractReport
     * @see Reporter
     * @see Network
     * @see #updateControllers(Station)
     * @see #correspondingIncidentExists(AbstractReport)
     * @see #getCorrespondingIncident(AbstractReport)
     */
    public AbstractReport makeStationReport(String noContr, Date time, String image, String station, IncidentType it){
        latestReportIsRoute = false;
        Reporter r = new Reporter("temp@google.com");
        int n = Integer.parseInt(noContr);


        Image i = null;

        Station s = network.getStation(station);
        /*
            Bellow is used to change states att all relevant nodes after a report has been made on a station.
            TODO: I will fix the structure after report methods has been completed. Otherwise everything is implemented. //Seif
         */
        updateControllers(s);

        if(time == null){
            time = Date.from(Instant.now());
        }

        AbstractReport report = new ReportStation(n,time,i,s,r);
        reportsList.add(report);
        notifyObservers(UpdateType.NEW_REPORT);

        if (IncidentList.size() != 0 && correspondingIncidentExists(report)) {
            getCorrespondingIncident(report).addReport(report);
            return report;
        }
        getCorrespondingIncident(report).addReport(report);
        notifyObservers(UpdateType.NEW_INCIDENT);
        return report;
    }

    /**
     * Method to create a new Report for a specific Route.
     *
     * @param noContr Number of reported controllers.
     * @param time Time the Report is being made.
     * @param image Attached Image.
     * @param route route the User is creating a Report for.
     * @param it What type of incident the Report is.
     *
     * @return A new Report.
     *
     * @see AbstractReport
     * @see Reporter
     * @see Network
     * @see #updateControllers(Station) //Will be added when the functionality for creating a Report is complete.
     * @see #correspondingIncidentExistsRoute(AbstractReport)
     * @see #getCorrespondingIncidentRoute(AbstractReport)
     */
    public AbstractReport makeRouteReport(String noContr, Date time, String image, String route, IncidentType it){
        latestReportIsRoute = true;
        Reporter r = new Reporter("temp@google.com");
        int n = Integer.parseInt(noContr);
        Image i = null;
        Station s = network.getStation("Korsvägen");
        Route ro = new Route(route, network.getStationRoutes(s));

        if(time == null){
            time = Date.from(Instant.now());
        }

        AbstractReport report = new ReportRoute(n,time,i,s,ro,r);
        reportsList.add(report);
        notifyObservers(UpdateType.NEW_REPORT);

        if (IncidentListRoute.size() != 0 && correspondingIncidentExistsRoute(report)) {
            getCorrespondingIncidentRoute(report).addReport(report);
            return report;
        }
        getCorrespondingIncidentRoute(report).addReport(report);
        notifyObservers(UpdateType.NEW_INCIDENT);
        return report;
    }

    /**
     * Update the Node, as well as all adjacent nodes, a User has made a Station Report for, to warn other Users about the
     * presence of controllers in the Network.
     *
     * @param station the User has reported.
     *
     * @see Station
     * @see #getStationNodes(Station)
     * @see #getAdjacentNodes(List, int)
     * @see #setActiveControllers(List)
     */
    private void updateControllers(Station station){
        List<Network.Node> controllersAtNodes = new ArrayList<>();
        controllersAtNodes = getStationNodes(station);
        controllersAtNodes.addAll(getAdjacentNodes(controllersAtNodes, 1));
        setActiveControllers(controllersAtNodes);
    }

    /**
     * Method to set active controllers at a Node by changing its state to True.
     *
     * @param nodes Nodes that should have their state changed.
     *
     * @see Network
     * @see Network.Node
     */
    private void setActiveControllers(List<Network.Node> nodes){
        network.setActiveControllers(nodes);
    }

    /**
     * Method to get all adjacent nodes for a List of Nodes.
     * @param nodes to find adjacent Nodes for.
     * @param range how far we away we want to look for adjacent Nodes.
     *
     * @return A list of all adjacent Nodes.
     *
     * @see Network
     */
    private List<Network.Node> getAdjacentNodes(List<Network.Node> nodes, int range) {
        return network.getAdjacentNodes(nodes, range);
    }

    /**
     * Method to get all Nodes for a specific station.
     *
     * @param station whose Nodes we want to get.
     *
     * @return List of all Nodes for a Station.
     *
     * @see Network
     * @see Station
     */
    private List<Network.Node> getStationNodes(Station station){
        return station.getNodes();
    }

    /**
     * Method to get all Routes affected by active Reports. Should be used to display all affected Routes to the User.
     * @return List of all Routes affected by User Reports.
     */
    public List<Route> getAllImpactedRoutes(){
        return network.getAllImpactedRoutes();
    }

    /**
     * Get the Names of all stations in the Network.
     *
     * @return an Array of all Station names.
     *
     * @see Network
     * @see Station
     */
    public String[] getAllStations() {
        return network.getAllStationNames();
    }

    /**
     * Get the last made User Report.
     *
     * @return last made User Report.
     *
     * @see AbstractReport
     */
    public AbstractReport getLatestReport(){
        return reportsList.get(reportsList.size()-1);
    }

    /**
     * Get all Reports Users has made.
     *
     * @return all reports.
     *
     * @see AbstractReport
     */
    public ArrayList<AbstractReport> getAllReports(){
        return reportsList;
    }

    /**
     * Get the number of incidents.
     *
     * @return number of incidents.
     */
    public int getIncidentCount(){ return IncidentList.size(); }

    /**
     * Get a specific incident.
     *
     * @param index index of the Incident.
     * @param incidentList List holding all incidents.
     *
     * @return Sought after incident.
     *
     * @see Incident
     */
    public Incident getIncident(int index, ArrayList<Incident> incidentList){
        if(index >= 0 && index < incidentList.size())
            return incidentList.get(index);
        else
            return null;
    }

    /**
     * Get the latest incident.
     *
     * @return latest incident.
     *
     * @see Incident
     */
    public Incident getLatestIncident() {
        if (latestReportIsRoute) {
            return getIncident(IncidentListRoute.size()-1, IncidentListRoute);
        } else {
            return getIncident(IncidentList.size()-1, IncidentList);
        }
    }

    /**
     * Method to check if there already exists a similar incident.
     *
     * @param report to see if incident exists for.
     *
     * @return True if incident exists, false otherwise.
     *
     * @see AbstractReport
     * @see Incident
     * @see #makeRouteReport(String, Date, String, String, IncidentType)
     */
    public boolean correspondingIncidentExists(AbstractReport report) {
        for (Incident i : IncidentList) {
            if (i.getTypeOfIncident() == report.getType() && (i.getLastActiveStation().equals(report.getStation()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a corresponding incident.
     * @param report to find corresponding incident to.
     *
     * @return the corresponding incident.
     *
     * @see Incident
     * @see AbstractReport
     * @see #makeStationReport(String, Date, String, String, IncidentType)
     */
    public Incident getCorrespondingIncident(AbstractReport report) {
        for (Incident i : IncidentList) {
            if (i.getTypeOfIncident() == report.getType() && (i.getLastActiveStation().equals(report.getStation()))) {
                return i;
            }
        }
        Incident incident = new Incident(report.getType());
        IncidentList.add(incident);
        return incident;
    }

    /**
     * Method to check if there already exists a similar incident.
     *
     * @param report to see if incident exists for.
     *
     * @return True if incident exists, false otherwise.
     *
     * @see AbstractReport
     * @see Incident
     * @see #makeRouteReport(String, Date, String, String, IncidentType)
     */
    public boolean correspondingIncidentExistsRoute(AbstractReport report) {
        for (Incident i : IncidentListRoute) {
            if (i.getTypeOfIncident() == report.getType() && i.getLastActiveRoute().getLine().equals(report.getRoute().getLine())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a corresponding incident.
     * @param report to find corresponding incident to.
     *
     * @return the corresponding incident.
     *
     * @see Incident
     * @see AbstractReport
     * @see #makeRouteReport(String, Date, String, String, IncidentType)
     */
    public Incident getCorrespondingIncidentRoute(AbstractReport report) {
        for (Incident i : IncidentListRoute) {
            if (i.getTypeOfIncident() == report.getType() && i.getLastActiveRoute().getLine().equals(report.getRoute().getLine())) {
                return i;
            }
        }
        Incident incident = new Incident(report.getType());
        IncidentListRoute.add(incident);
        return incident;
    }
}
