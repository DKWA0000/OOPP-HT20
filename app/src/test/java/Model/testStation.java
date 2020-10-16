package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import model.Network;
import model.Node;
import model.Station;

import static org.junit.Assert.assertEquals;

/**
 * @author: Joakim Tubring.
 */

public class testStation {

    private Station test;
    private Network testNetwork;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        test = new Station("testStation");
        testNetwork = new Network(new HashMap<String, ArrayList>());
    }

    /**
     * Verify that correct name is returned.
     */

    @Test
    public void testNewStation() {
        assertEquals("testStation", test.getName());
    }

    /**
     * Verify that correct List-size is returned.
     */

    @Test
    public void testListSize(){
        assertEquals(0, test.getNodes().size());
    }

    /**
     * Test that we could add a Node.
     */

    @Test
    public void testaddNode(){
        test.addNode(new Node("testNode"));
        assertEquals(1, test.getNodes().size());
        assertEquals("testNode", test.getNodes().get(0).getName());
    }

}


