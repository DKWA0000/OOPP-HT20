package model;

import java.util.*;

/**
 * Class representing a Route.
 *
 * @see AbstractReport
 *
 * @author Lucas Karlsson.
 */

public class Route {

    private String line;
    private List<Network.Node> stops = new ArrayList<Network.Node>();

    /**
     * Constructor of Route-object, takes parameter and passes it.
     *
     * @param name String
     * @param stops List<Network.Node>
     */
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