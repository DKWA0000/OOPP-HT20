import java.io.*;
import java.util.*;

public class route {

    private int lineNumber;
    private Set<Node> stops;

    public route(int lineNumber, Set<Node> stops){
        this.lineNumber = lineNumber;
        this.stops = stops;
    }

    public int getlineNumber(){
        return this.lineNumber;
    }

    public Set<Node> getNodes(){
        return this.stops;
    }

    public route createRoute(String filePath, int lineNr) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        route r = new route(lineNr, new HashSet<Node>());

        while ((br.readLine()) != null) {
            line = br.readLine();
            r.stops.add(new Node(line));
        }

        return r;
    }


}
