package model;

import java.io.*;
import java.util.*;

public class Route {

    private int lineNumber;
    private Set<Node> stops;

    public Route(int lineNumber, Set stops){
        this.lineNumber = lineNumber;
        this.stops = stops;
    }

    public int getlineNumber(){
        return this.lineNumber;
    }

    public Set<Node> getNodes(){
        return this.stops;
    }

    public Route createRoute(int lineNr) throws IOException {

            BufferedReader br = new BufferedReader(new FileReader("C:\\testApp\\src\\6_Kortedala.csv"));
            String line;
            List tmp = new ArrayList<>();
            Route r = new Route(lineNr, new HashSet());

            while ((line = br.readLine()) != null) {
                String[] l = line.split(",");
                tmp = Arrays.asList(l);
                System.out.println(Arrays.toString(l));
            }
            for(int i = 0; i < tmp.size(); i++){
                stops.add(new Node((String) tmp.get(i)));
            }

            return r;
        }


}
