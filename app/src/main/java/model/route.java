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
        ArrayList tmp = new ArrayList<>();
        route r = new route(lineNr, new HashSet<Node>());

        while ((br.readLine()) != null) {
            line = br.readLine();
            tmp.add(line);

        }
        for(int i = 0; i < tmp.size(); i++){
            r.stops.add(new Node((String) tmp.get(i)));
        }

        return r;
    }


}
