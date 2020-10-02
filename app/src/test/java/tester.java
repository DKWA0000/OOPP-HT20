import org.junit.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Incident;
import model.IncidentType;
import model.MODEL;
import model.ReportRoute;
import model.ReportStation;
import model.Reporter;
import model.Route;
import model.Station;

import static org.junit.Assert.*;

public class tester{

    /**
     * testNetwork for running tests.
     */
    Path path = FileSystems.getDefault().getPath("src/main/assets/routes");
    String dataPath = path.toUri().toString().replace("file:///", "");
    testNetwork net = new testNetwork(dataPath);
    Map<String, testStation> testStations = net.getStations();
    MODEL testModel1 = new MODEL();
    Incident testIncident = new Incident(IncidentType.ROUTE);
    /**
     * Testing correct name for Station and size on nodelist.
     */
    @Test
    public void testStation(){
        testStation test = new testStation("testStation");
        assertEquals("testStation", test.getName());
        assertEquals(0,test.getNodes().size());
        test.getNodes().add(net.createNode("testLage"));
        assertEquals(1, test.getNodes().size());
        assertEquals("testLage", test.getNodes().get(0).getName());
    }

    /**
     * Testing correct name for Route and size() on stopslist.
     */
    @Test
    public void testRoute(){
        testRoute test = new testRoute("testRoute", new LinkedList<>());
        assertEquals(test.getLine(), "testRoute");
        assertEquals(0,test.getNodes().size());
        test.getNodes().add(net.createNode("testnod"));
        assertEquals(1, test.getNodes().size());
        assertEquals("testnod", test.getNodes().get(0).getName());
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
        testReportRoute.setNControllants(4);
        assertEquals(4, testReportRoute.getnControllants());

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

    /**
     * Testing Network class methods.
     *
     */
    @Test
    public void testNetwork() throws IOException {
    net.addNode("testStation");
    Map<testNetwork.Node, List<testNetwork.Node>> testAdj = net.getAdjacencylist();
    assertTrue(testAdj.containsKey(net.createNode("testStation")));
    net.removeNode("testStation");
    assertFalse(testAdj.containsKey(net.createNode("testStation")));
    assertTrue(testAdj.containsKey(net.createNode("Mariaplan A")));

    File f[] = new File(dataPath).listFiles();
    Set<String> filer = new HashSet<>();


    for(int i = 0; i < f.length; i++){
        BufferedReader br = new BufferedReader(new FileReader(f[i].toString()));
        String line;
        while((line = br.readLine()) != null){
            filer.add(line);
        }
    }

    assertEquals(filer.size(), testAdj.size());
    assertEquals(filer.size(), testAdj.values().size());

    BufferedReader br1 = new BufferedReader(new FileReader(dataPath + "/3_Kålltorp.txt"));
    String lineR;
    testRoute ts = new testRoute("Kålltorp", new ArrayList<>());
    ArrayList<String> routeData = new ArrayList<>();

    while((lineR = br1.readLine()) != null){
        ts.getNodes().add(net.createNode(lineR));
        routeData.add(lineR);
    }

    assertEquals(net.getNextNode(ts.getNodes().get(0), ts).toString(), routeData.get(1));
    assertEquals(net.getPrevNode(ts.getNodes().get(1), ts).toString(), routeData.get(0));
    assertEquals(net.getRouteNodes(ts).size(), routeData.size());
    assertEquals("Musikvägen" ,net.getNodeStation(net.createNode("Musikvägen A")).getName());

    System.out.println(dataPath);


    }

    @Test
    public void testMODEL(){

    testModel1.createRouteReport(2,new Time(1,2,3), null,
        new Station("testStation"), new Route("testRoute",
            new ArrayList<>()), new Reporter("testmail"));

    }

}

/**
 * TODO Kolla dessa metoder efter testerna är klara.
 * Network.addEdge
 *
 * Metoder getPrevNode och getNextNode har ingen if-sats som hanterar
 * fallen när man hamnar utanför listan.
 */

