package Model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import model.ReportStation;
import model.Reporter;
import model.Station;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Joakim Tubring.
 */

public class testReportStation {

    private ReportStation testReportStation;

    /**
     * Method for generating testdata.
     */

    @Before
    public void buildTestData(){

        testReportStation  = new ReportStation(2, new Time(1,2,3), null,
                new Station("testStation"), new Reporter("testMail"));
    }

    /**
     * Verify that ReportStation is created.
     **/

    @Test
    public void testNewReportStation(){
        assertNotNull(testReportStation);
        assertEquals("class model.ReportStation", testReportStation.getClass().toString());
    }

    /**
     * Verify that correct String is returned.
     **/

    @Test
    public void testString(){
        assertEquals("", testReportStation.toString());
    }

}
