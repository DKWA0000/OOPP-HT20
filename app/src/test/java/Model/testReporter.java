package Model;

import org.junit.Before;
import org.junit.Test;

import com.example.planka.model.Reporter;

import static org.junit.Assert.assertEquals;

/**
 * @author: Joakim Tubring.
 */

public class testReporter {

    private Reporter testReporter;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        testReporter = new Reporter("testMail");
    }

    /**
     * Test that the correct mail is returned.
     */

    @Test
    public void testNewReporter(){
        assertEquals("testMail", testReporter.getMail());
    }

    /**
     * Test that we could adjust the Trustfactor.
     */

    @Test
    public void testTrustFactor(){
        assertEquals(0, testReporter.getTrustFactor(), 0.1);
        testReporter.increaseTrustFactor(1);
        assertEquals(1, testReporter.getTrustFactor(), 0.1);
    }

}
