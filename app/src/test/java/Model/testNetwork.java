package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import model.Network;
import model.Node;
import model.Route;
import model.Station;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author: Joakim Tubring.
 */

public class testNetwork {

    private HashMap<String, ArrayList> testMap = new HashMap<>();
    private Network testNetwork;
    private Route testRoute;
    private Node node1;
    private Node node2;
    private Station testStation;
    private ArrayList<Node> nodelist = new ArrayList<>();

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
        R1.add("testStationA 1");
        R1.add("testStationA 1");

        ArrayList<String> R2 = new ArrayList<>();
        R2.add("testStationF 2");
        R2.add("testStationC 4");
        R2.add("testStationD 7");
        R2.add("testStationG 6");
        R2.add("testStationH 8");
        R2.add("testStationF 2");
        R2.add("testStationF 2");


        testMap.put("R1", R1);
        testMap.put("R2", R2);

        testNetwork = new Network(testMap);

        ArrayList<Node> testRouteList = new ArrayList<>();

        testRouteList.add(new Node("node1"));
        testRouteList.add(new Node("node2"));
        testRouteList.add(new Node("node3"));
        testRouteList.add(new Node("node4"));
        testRouteList.add(new Node("node5"));

        testRoute = new Route("testRoute", testRouteList);

        node1 = new Node("node1");
        node2 = new Node("node1");

        testStation = new Station("testStation");
        testStation.getNodes().add(new Node("node1"));
        testStation.getNodes().add(new Node("node2"));

        nodelist.add(new Node("testStationA 1"));

    }

    /**
     * Test that Network has been created.
     */

    @Test
    public void testCreateNetwork(){
        assertNotNull(testNetwork);
        assertEquals("class model.Network", testNetwork.getClass().toString());
    }

    /**
     * Test that correct Node is returned.
     */

    @Test
    public void testgetAdjacentNodes(){
        assertEquals("[testStationB 3, testStationE 9, testStationA 1]",
                testNetwork.getAdjacentNodes(nodelist, 1).toString());
        assertEquals(5, testNetwork.getAdjacentNodes(nodelist, 2).size());
        System.out.println(nodelist.toString());
    }

    /**
     * Test that the previous Node is testRoute is returned.
     */

    @Test
    public void testgetPrevNode(){
        assertEquals("node3", testNetwork.getPrevNode(testRoute.getNodes().get(3), testRoute).toString());
    }

    /**
     * Test that that next Node in testRoute is returned.
     */

    @Test
    public void testgetNextNode(){
        assertEquals("node4", testNetwork.getNextNode(testRoute.getNodes().get(2), testRoute).toString());
    }

    /**
     * Test that all Nodes in testRoute is returned.
     */

    @Test
    public void testgetRouteNodes(){
        assertEquals("[node1, node2, node3, node4, node5]",testNetwork.getRouteNodes(testRoute).toString());
    }

    /**
     * Verify that the correct Station is returned.
     */

    @Test
    public void testgetStation(){
        assertEquals("testStationA", testNetwork.getStation("testStationA").getName());
    }

    /**
     * Verify that the "subnodes" Station is returned.
     */

    @Test
    public void testgetNodeStation(){
        assertEquals("testStationA",
                testNetwork.getNodeStation(new Node("testStationA 1")).getName());
    }

    /**
     * Verify that the correct Routes is returned.
     */

    @Test
    public void testgetStationRoutes(){
        assertEquals("[node1, node2]", testNetwork.getStationRoutes(testStation).toString());
    }

    /**
     * Verify that all Stations is returned but no
     * duplicates.
     */

    @Test
    public void testgetAllStationNames(){
        assertEquals("[testStationA, testStationB, testStationC, testStationD," +
                        " testStationE, testStationF, testStationG, testStationH]",
                Arrays.toString(testNetwork.getAllStationNames()));
    }

    /**
     * Verify that subnodes Station is returned.
     */

    @Test
    public void testgetStationName(){
        assertEquals("testStationA",
                testNetwork.getStationName(new Node("testStationA 1")));
    }

    /**
     * Test that Hashcode is equal for objects
     * with same Value.
     */

    @Test
    public void testhashCode(){
        assertEquals(node1.hashCode(), node2.hashCode());
    }

    /**
     * Test that Objects with same Value are equal.
     */

    @Test
    public void testequals(){
        assertTrue(node1.equals(node2));
    }

    /**
     * Verify that the correct String is returned.
     */

    @Test
    public void testtoString() {
        assertEquals("node1", node1.toString());
    }

    /**
     * Verify that the correct name is returned.
     */

    @Test
    public void testgetName() {
        assertEquals("node1", node1.getName());
    }

    /**
     * Verify that the correct state is returned.
     */

    @Test
    public void testgetState(){
        assertFalse(node1.getState());
    }

    /**
     * Test that we could adjust the state.
     */

    @Test
    public void testsetState(){
        node1.setState(true);
        assertTrue(node1.getState());
    }

    /**
     * Verify that correct number of routes is returned.
     */

    /*
    @Test

    public void testgetImpactedRoutes(){
        nodelist.add(new Node("testStationB 3"));
        assertEquals(2, testNetwork.getImpactedRoutes(nodelist).size());
    }
     */

    /**
     * Test that we could adjust the state of the controllers to active.
     */

    @Test
    public void testsetActiveControllers(){
        assertFalse(nodelist.get(0).getState());
        testNetwork.setActiveControllersNodes(nodelist);
        assertTrue(nodelist.get(0).getState());
    }

    /**
     * Test that we could adjust the state of the controllers to inactive.
     */

    @Test
    public void testnoActiveControllers(){
        testNetwork.setActiveControllersNodes(nodelist);
        assertTrue(nodelist.get(0).getState());
        testNetwork.removeActiveControllersNodes(nodelist);
        assertFalse(nodelist.get(0).getState());
    }

    @Test
    public void testaddNodeToExistingStation(){
        System.out.println(Arrays.toString(testNetwork.getAllStationNames()));
    }

}
