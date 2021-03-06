package com.example.planka.model;

import android.media.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Class responsible for connecting our backend together.
 *
 * @see Network
 * @see AbstractReport
 * @see Incident
 *
 * @author Seif Eddine Bourogaa
 */
public class MODEL extends Observable<UpdateType> {

    Network network;
    private final ArrayList<Incident> IncidentList;
    private final ArrayList<Incident> IncidentListRoute;
    private final ArrayList<AbstractReport> reportsList = new ArrayList<>();
    private boolean latestReportIsRoute = false;
    private AbstractReport editReport;
    private Reporter foo;

    public MODEL(HashMap<String, ArrayList> fileContent){
        network = new Network(fileContent);
        IncidentList = new ArrayList<>();
        IncidentListRoute = new ArrayList<>();
        foo = new Reporter("foo@bar.me");
    }

    /**
     * Method to create a new Report for a specific station.
     *
     * @param noContr Number of reported controllers.
     * @param time Time the Report is being made.
     * @param image Attached Image.
     * @param station Station the User is creating a Report for.
     * @return A new Report.
     *
     * @see AbstractReport
     * @see Reporter
     * @see Network
     * @see #addControllersNodes(Station)
     * @see #correspondingIncidentExists(AbstractReport)
     * @see #getCorrespondingIncident(AbstractReport)
     */
    public AbstractReport makeStationReport(String noContr, Date time, String image, String station){
        latestReportIsRoute = false;
        Reporter reporter = getUser();
        int numberOfControllers = Integer.parseInt(noContr);
        //Attaching an image have not been implemented.
        Image i = null;
        Station stationOfReport = network.getStation(station);
        addControllersNodes(stationOfReport);

        if(time == null){
            time = new Date();
        }

        AbstractReport report = new ReportStation(numberOfControllers, time, i, stationOfReport, reporter);
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
     * @return A new Report.
     *
     * @see AbstractReport
     * @see Reporter
     * @see Network
     * @see #addControllersRoute(Route)
     * @see #correspondingIncidentExistsRoute(AbstractReport)
     * @see #getCorrespondingIncidentRoute(AbstractReport)
     */
    public AbstractReport makeRouteReport(String noContr, Date time, String image, String route){
        latestReportIsRoute = true;
        Reporter reporter = getUser();
        int numberOfControllers = Integer.parseInt(noContr);
        //Attaching an image have not been implemented.
        Image i = null;
        Route routeOfReport = network.getRouteFromString(route);
        addControllersRoute(routeOfReport);

        if(time == null){
            time = new Date();
        }

        AbstractReport report = new ReportRoute(numberOfControllers, time, i, routeOfReport, reporter);
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
     */
    private void addControllersNodes(Station station){
        List<Node> controllersAtNodes;
        controllersAtNodes = getStationNodes(station);
        controllersAtNodes.addAll(getAdjacentNodes(controllersAtNodes, 1));
        setActiveControllersNodes(controllersAtNodes);
    }

    /**
     * Set controllers at a Route. 
     * @param route to have controllers. 
     *              
     * @see Network#setActiveControllersRoutes(Route) 
     */
    private void addControllersRoute(Route route){
        network.setActiveControllersRoutes(route);
    }

    /**
     * Method to set active controllers at a Node by changing its state to True.
     *
     * @param nodes Nodes that should have their state changed.
     *
     * @see Network
     * @see Node
     * @see Network#setActiveControllersNodes(List) 
     */
    private void setActiveControllersNodes(List<Node> nodes){
        network.setActiveControllersNodes(nodes);
    }

    /**
     * Check if the Route a User want to get notifications for are affected by controllers.
     * @param userRoutes Route user want to have notifications for.
     *                   
     * @return True if affected by controllers, false otherwise. 
     * 
     * @see Network#userRouteImpacted(String) 
     */
    public boolean userRouteImpacted(String userRoutes){
        return network.userRouteImpacted(userRoutes);
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
    private List<Node> getAdjacentNodes(List<Node> nodes, int range) {
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
    private List<Node> getStationNodes(Station station){
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

    public ArrayList<Incident> getIncidentList() {
        ArrayList<Incident> incidents = new ArrayList<>(IncidentList);
        incidents.addAll(IncidentListRoute);
        return incidents;
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

    public boolean isLatestReportRoute() {
        return latestReportIsRoute;
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
     * @see #makeRouteReport(String, Date, String, String)
     */
    public boolean correspondingIncidentExists(AbstractReport report) {
        for (Incident incident : IncidentList) {
            if (incident.getTypeOfIncident() == report.getType() && (incident.getLastActiveStation().equals(report.getStation()))) {
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
     * @see #makeStationReport(String, Date, String, String)
     */
    public Incident getCorrespondingIncident(AbstractReport report) {
        for (Incident incident : IncidentList) {
            if (incident.getTypeOfIncident() == report.getType() && (incident.getLastActiveStation().equals(report.getStation()))) {
                return incident;
            }
        }
        Incident incident = new Incident(report.getType());
        IncidentList.add(incident);
        return incident;
    }

    /**
     * Method to check if there already exists a similar incident to a RouteReport.
     *
     * @param report to see if incident exists for.
     *
     * @return True if incident exists, false otherwise.
     *
     * @see AbstractReport
     * @see Incident
     * @see #makeRouteReport(String, Date, String, String)
     */
    public boolean correspondingIncidentExistsRoute(AbstractReport report) {
        for (Incident incident : IncidentListRoute) {
            if (incident.getTypeOfIncident() == report.getType() && incident.getLastActiveRoute().getLine().equals(report.getRoute().getLine())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a corresponding incident for a RouteReport.
     * @param report to find corresponding incident to.
     *
     * @return the corresponding incident.
     *
     * @see Incident
     * @see AbstractReport
     * @see #makeRouteReport(String, Date, String, String)
     */
    public Incident getCorrespondingIncidentRoute(AbstractReport report) {
        for (Incident incident : IncidentListRoute) {
            if (incident.getTypeOfIncident() == report.getType() && incident.getLastActiveRoute().getLine().equals(report.getRoute().getLine())) {
                return incident;
            }
        }
        Incident incident = new Incident(report.getType());
        IncidentListRoute.add(incident);
        return incident;
    }

    /**
     * Set method used after editing a Report.
     * 
     * @param report to set edits to.
     *               
     * @see #editReport(int) 
     */
    public void setEditReport(AbstractReport report){
        editReport = report;
    }
    
    /**
     * Method to edit a Report.
     * 
     * @param nCont number of controllers to add to Report. 
     *              
     * @see AbstractReport
     */
    public void editReport(int nCont) {
        editReport.setNControllants(nCont);
        notifyObservers(UpdateType.REPORT_UPDATE);
    }

    /**
     * Method to get a Reporter.
     *
     * @return Reporter.
     *
     * Note: Only used as a place holder for Users.
     */
    public Reporter getUser(){
        return foo;
    }
}
