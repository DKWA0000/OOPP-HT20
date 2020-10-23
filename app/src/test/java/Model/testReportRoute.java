package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Time;
import java.util.ArrayList;

import com.example.planka.model.IncidentType;
import com.example.planka.model.ReportRoute;
import com.example.planka.model.Reporter;
import com.example.planka.model.Route;

/**
 * @author: Joakim Tubring.
 */

public class testReportRoute {

    private ReportRoute testReportRoute;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        testReportRoute = new ReportRoute(2, new Time(1, 2, 3), null,
                new Route("testRoute", new ArrayList<>()),
                new Reporter("testMail"));
    }

    /**
     * Verify that Reportroute is created.
     */

    @Test
    public void testNewReportroute(){
        assertNotNull(testReportRoute);
        assertEquals("class ReportRoute", testReportRoute.getClass().toString());
    }

    /**
     * Verify that correct nControllants is returned.
     */

    @Test
    public void testgetnControllants() {
        assertEquals(2, testReportRoute.getnControllants());
    }

    /**
     * Verify that the correct time is returned.
     */

    @Test
    public void testgetTimeOfReport() {
        assertEquals(new Time(1,2,3), testReportRoute.getTimeOfReport());
    }

    /**
     * Verify that the correct Station is returned.
     */

    @Test
    public void testgetStation() {
        assertEquals("testStation", testReportRoute.getStation().getName());
    }

    /**
     * Verify that the correct Mail is returned.
     */

    @Test
    public void testgetReporter() {
        assertEquals("testMail", testReportRoute.getReporter().getMail());
    }

    /**
     * Verify that the correct type is returned.
     */

    @Test
    public void testgetType() {
        assertEquals(IncidentType.ROUTE, testReportRoute.getType());
    }

    /**
     * Test that we could adjust nControllants.
     */

    @Test
    public void testsetNControllants() {
        testReportRoute.setNControllants(4);
        assertEquals(4, testReportRoute.getnControllants());
    }

    /**
     * Test that the correct info-String is returned.
     */

    @Test
    public void testgetInfo(){
        assertEquals("{nmbr: 2,time: 01:02:03,station: testStation}", testReportRoute.getInfo());
    }

}

