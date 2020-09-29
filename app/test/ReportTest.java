import model.*;
import org.junit.Test;

import java.sql.Time;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportTest {
    @Test
    public void createReportTest() {
        Time time = new Time((long) 15.00);
        Station station = new Station("Marklandsgatan");
        Reporter reporter = new Reporter("lucask@live.se");
        int nContr = 5;

        ReportStation rs = new ReportStation(nContr, time, null, station, reporter);

        assertEquals(rs.getnControllants(), nContr);
        assertEquals(rs.getStation(), station);
        assertEquals(rs.getTimeOfReport(), time);
        assertEquals(rs.getReporter(), reporter);

        Route route = new Route("Marklandsgatan", null);
        ReportRoute rr = new ReportRoute(nContr, time, null, station, route, reporter);

        assertEquals(rr.getnControllants(), nContr);
        assertEquals(rr.getReporter(), reporter);
        assertEquals(rr.getStation(), station);
        assertEquals(rr.getTimeOfReport(), time);

        Incident i = new Incident(IncidentType.STATION);
        i.addReport(rs);

        assertEquals(i.latestReport(), rs);
        assertEquals(i.getTypeOfIncident(), IncidentType.STATION);
    }
}
