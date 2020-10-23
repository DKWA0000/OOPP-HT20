package Model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;

import com.example.planka.model.Incident;
import com.example.planka.model.IncidentType;
import com.example.planka.model.ReportRoute;
import com.example.planka.model.Reporter;
import com.example.planka.model.Route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Joakim Tubring.
 */

public class testIncident {

    private Incident testIncident;
    private ReportRoute testReportRoute;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        testIncident = new Incident(IncidentType.ROUTE);
        testReportRoute = new ReportRoute(2, new Time(1, 2, 3), null,
                new Route("testRoute",
                new ArrayList<>()), new Reporter("testMail"));
        testIncident.addReport(testReportRoute);
    }

    /**
     * Verify that an incident is created.
     */

    @Test
    public void testCreateIncident(){
        assertNotNull(testIncident);
        assertEquals("class Incident", testIncident.getClass().toString());
    }

    /**
     * Verify that report has been created.
     */

    @Test
    public void testaddReport(){
        assertNotNull(testIncident.getLatestReport());
    }

    /**
     * Verify that latest report is correct.
     */

    @Test
    public void testlatestReport(){
        assertEquals("{nmbr: 2,time: 01:02:03,station: testStation}",
                testIncident.getLatestReport().getInfo());
    }

    /**
     * Testing correct return value.
     */

    @Test
    public void testgetNominalTrustFactor() {
        assertEquals(0, testIncident.getNominalTrustFactor());
    }

    /**
     * Verify that nominal trustfactor has been updated.
     */

    @Test
    public void testupdateNominalTrustFactor() {
        testIncident.updateNominalTrustFactor(testIncident.getLatestReport());
        assertEquals(1, testIncident.getNominalTrustFactor());
    }

    /**
     * Verify that correct station is returned.
     */

    @Test
    public void testgetLastActiveStation(){
        assertEquals("testStation", testIncident.getLastActiveStation().getName());
    }

    /**
     * Verify that correct time is returned.
     */

    @Test
    public void testgetTime() {
        assertEquals("01:02:03", testIncident.getTime().toString());
    }

    /**
     * Verify that correct incidenttype is returned.
     */

    @Test
    public void testgetTypeOfIncident() {
        assertEquals(IncidentType.ROUTE, testIncident.getTypeOfIncident());
    }

    /**
     * Verify that listReports has been updated.
     */

    @Test
    public void testgetListReports() {
        assertEquals(1, testIncident.getListReports().size());
    }

    /**
     * Verify correct return value for votes.
     */

    @Test
    public void testgetVotes(){
        assertEquals(0, testIncident.getVotes());
    }

    /**
     * Testing the upVote method.
     */

    @Test
    public void upVote(){
        testIncident.upVote();
        assertEquals(1,testIncident.getVotes());
    }

    /**
     * Testing the downVote method.
     */

    @Test
    public void downVote(){
        testIncident.downVote();
        assertEquals(-1, testIncident.getVotes());
    }

    /**
     * Verify that correct route is returned.
     */

    @Test
    public void testgetLastActiveRoute(){
        assertEquals("testRoute", testIncident.getLastActiveRoute().getLine());
    }

}
