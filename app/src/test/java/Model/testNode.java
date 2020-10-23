package Model;

import org.junit.Before;
import org.junit.Test;

import model.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class testNode {

    Node node1;
    Node node2;
    Node node3;
    String testString;

    @Before
    public void buildTestdata(){
        node1 = new Node("test1");
        node2 = new Node("test1");
        node3 = new Node(null);
        testString = "test";
    }

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
        assertEquals("test1", node1.toString());
    }

    /**
     * Verify that the correct name is returned.
     */

    @Test
    public void testgetName() {
        assertEquals("test1", node1.getName());
        assertFalse(node1.equals(null));
        assertFalse(node1.equals(testString));

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
}
