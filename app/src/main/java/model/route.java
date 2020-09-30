package model;

import java.util.*;

public class Route {

    private String line;
    private List<Network.Node> stops = new ArrayList<Network.Node>();

    public Route(String name, List<Network.Node> stops){
        this.line = name;
        this.stops = stops;
    }

    public String getLine(){
        return this.line;
    }

    public List<Network.Node> getNodes(){
        return this.stops;
    }



}
