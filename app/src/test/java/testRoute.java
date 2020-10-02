import java.util.*;

public class testRoute {

    private String line;
    private List<testNetwork.Node> stops = new ArrayList<testNetwork.Node>();

    public testRoute(String name, List<testNetwork.Node> stops){
        this.line = name;
        this.stops = stops;
    }

    public String getLine(){
        return this.line;
    }

    public List<testNetwork.Node> getNodes(){
        return this.stops;
    }



}
