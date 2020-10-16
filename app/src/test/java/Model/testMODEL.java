package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import model.AbstractReport;
import model.Incident;
import model.IncidentType;
import model.MODEL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author: Joakim Tubring.
 */

public class testMODEL {

    private HashMap<String, ArrayList> testMap = new HashMap<>();
    private MODEL testMODEL = new MODEL(testMap);
    private AbstractReport report;
    private AbstractReport routeReport;
    private String testTime;
    private String reportString;
    private ArrayList<Incident> incidentList = new ArrayList<>();

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        ArrayList<String> R1 = new ArrayList<>();
        R1.add("testStationA 1");
        R1.add("testStationB 3");
        R1.add("testStationC 4");
        R1.add("testStationD 7");
        R1.add("testStationE 9");

        ArrayList<String> R2 = new ArrayList<>();
        R2.add("testStationF 2");
        R2.add("testStationC 4");
        R2.add("testStationD 7");
        R2.add("testStationG 6");
        R2.add("testStationH 8");

        testMap.put("R1", R1);
        testMap.put("R2", R2);

        testMODEL = new MODEL(testMap);

        report = testMODEL.makeStationReport("2", null,null,
                "testStationA", IncidentType.STATION);

        testTime = new Date().toString();
        reportString = report.getInfo().replace(testTime,"");

        incidentList.add(new Incident(IncidentType.STATION));



    }

    /**
     * Verify that MODEL has been created.
     */

    @Test
    public void testcreateModel(){
        assertNotNull(testMODEL);
        assertEquals("class model.MODEL", testMODEL.getClass().toString());
    }

    /**
     * Verify that StationReport has been created excluding time
     * Because of latency issues in build.
     */

    @Test
    public void testmakeStationReport(){
        assertEquals("{nmbr: 2,time: ,station: testStationA}", reportString);
    }

    /**
     * Verify that RouteReport has been created.
     */

    @Test
    public void testmakeRouteReport(){
        AbstractReport reportRoute = testMODEL.makeRouteReport("2",
                null,null, null,IncidentType.ROUTE);
        assertNotNull(reportRoute);
    }

    /**
     * Verify that the correct Stations is returned.
     */

    @Test
    public void testgetAllStations(){
        assertEquals("[testStationA, testStationB, testStationC, testStationD," +
                        " testStationE, testStationF, testStationG, testStationH]",
                Arrays.toString(testMODEL.getAllStations()));
    }

    /**
     * Verify that correct report is returned.
     */

    @Test
    public void testgetLatestReport(){
        assertEquals(report.getInfo(), testMODEL.getLatestReport().getInfo());
    }

    /**
     * Verify that correct number of reports is returned.
     */

    @Test
    public void testgetAllReports(){
        assertEquals(1, testMODEL.getAllReports().size());
    }

    /**
     * Verify that correct incidentcount is returned.
     */

    @Test
    public void testgetIncidentCount(){
        assertEquals(1, testMODEL.getIncidentCount());
    }

    /**
     * Verify that the correct Incident is returned.
     */

    @Test
    public void testgetIncident(){
        assertEquals(IncidentType.STATION, testMODEL.getIncident(0, incidentList).getTypeOfIncident());
        assertEquals(0, testMODEL.getIncident(0, incidentList).getVotes());
    }

    /**
     * Verify that the correct incident is returned.
     */

    @Test
    public void testgetCorrespondingIncident(){
        assertEquals(IncidentType.STATION, testMODEL.getCorrespondingIncident(report).getTypeOfIncident());
    }

    /**
     * Test if the incident bound to report is found.
     */

    @Test
    public void testgetCorrespondingIncidentExists(){
        assertTrue(testMODEL.correspondingIncidentExists(report));
    }

    /**
     * Testing that method could find the Incident "marked" with
     * ROUTE.
     */

    @Test
    public void testgetCorrespondingIncidentRoute(){
        report = testMODEL.makeStationReport("2", null,null,
                "testStationA", IncidentType.ROUTE);
        assertEquals(IncidentType.ROUTE, testMODEL.getCorrespondingIncidentRoute(report).getTypeOfIncident());
    }

    /**
     * Test that the incident report with IncidentType ROUTE
     * is found.
     */

    @Test
    public void testgetCorrespondingIncidentExistsRoute(){
        assertTrue(testMODEL.correspondingIncidentExistsRoute(report));
    }

    /**
     * Test that correct Incident is returned.
     */

    @Test
    public void testgetLatestIncident(){
        assertEquals(IncidentType.STATION, testMODEL.getLatestIncident().getTypeOfIncident());
    }

}