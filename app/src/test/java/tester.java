import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.ImageDecoder;
import android.media.Image;

import com.example.planka.MainActivity;

import org.junit.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import model.Incident;
import model.IncidentType;
import model.Network;
import model.ReportRoute;
import model.ReportStation;
import model.Reporter;
import model.Route;
import model.Station;

import static org.junit.Assert.*;

public class tester{

    /**
     * Testing correct name for Station and size on nodelist.
     */
    @Test
    public void testStation(){
        Station testStation = new Station("Angered");
        assertEquals("Angered", testStation.getName());
        assertEquals(0,testStation.getNodes().size());
    }

    /**
     * Testing correct name for Route and size() on stopslist.
     */
    @Test
    public void testRoute(){
        Route testRoute = new Route("3", new LinkedList<>());
        assertEquals(testRoute.getLine(), "3");
        assertEquals(0,testRoute.getNodes().size());
    }

    /**
     * Testing Reporterclass for mail and trustfactor.
     */
    @Test
    public void testReporter(){
        Reporter testReporter = new Reporter("testmail");
        assertEquals("testmail",testReporter.getMail());
        testReporter.increaseTrustFactor(1.0);
        assertEquals(1.0, testReporter.getTrustFactor(),0.01);
    }

    /**
     * Testing IncidentClass for IncidentType and for upVotes / downVotes.
     */
    @Test
    public void testIncident(){
        Incident testIncident1 = new Incident(IncidentType.STATION);
        Incident testIncident2 = new Incident(IncidentType.ROUTE);
        assertEquals(IncidentType.STATION, testIncident1.getTypeOfIncident());
        assertEquals(IncidentType.ROUTE, testIncident2.getTypeOfIncident());
        testIncident1.upVote();
        assertEquals(1, testIncident1.getVotes());
        testIncident2.downVote();
        assertEquals(-1, testIncident2.getVotes());
        testIncident1.downVote();
        assertEquals(0, testIncident1.getVotes());
        testIncident2.upVote();
        assertEquals(0, testIncident2.getVotes());
    }

    /**
     * Testing reportRoute class for noContr, Time and image.
     */
    @Test
    public void testReportRoute(){
        ReportRoute testReportRoute = new ReportRoute(2, new Time(1,2,3),null,
                new Station("testStation"), new Route("testRoute", new LinkedList<>()),
                new Reporter("testmail"));

        assertEquals(2, testReportRoute.getnControllants());
        assertEquals(new Time(1,2,3), testReportRoute.getTimeOfReport());
        assertNull(testReportRoute.getImage());


    }

    /**
     * Testing reportStation class for noContr, Time and image.
     */
    @Test
    public void testReportStation(){
        ReportStation testReportStation = new ReportStation(2, new Time(1,2,3),null,
                new Station("testStation"),
                new Reporter("testmail"));

        assertEquals(2, testReportStation.getnControllants());
        assertEquals(new Time(1,2,3), testReportStation.getTimeOfReport());
        assertNull(testReportStation.getImage());
    }

    @Test
    public void testNetwork(){


    }


}

