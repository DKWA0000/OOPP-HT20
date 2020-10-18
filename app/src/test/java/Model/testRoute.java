package Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import model.Network;
import model.Node;
import model.Route;

/**
 * @author: Joakim Tubring.
 */

public class testRoute{

    private Network testNetwork;
    private Route testRoute;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        testNetwork = new Network(new HashMap<String, ArrayList>());
        testRoute = new Route("testRoute", new ArrayList<>());
    }

    /**
     * Verify that correct Line is returned.
     */

    @Test
    public void testNewRoute(){
        assertEquals("testRoute", testRoute.getLine());
    }

    /**
     * Verify that correct List-size is returned.
     */

    @Test
    public void testListSize(){
        assertEquals(0, testRoute.getNodes().size());
    }

    /**
     * Test that we could add a Node to nodesList.
     */

    @Test
    public void testAddNode(){
        testRoute.getNodes().add(new Node("testNode"));
        assertEquals(1, testRoute.getNodes().size());
        assertEquals("testNode", testRoute.getNodes().get(0).getName());
    }

}
