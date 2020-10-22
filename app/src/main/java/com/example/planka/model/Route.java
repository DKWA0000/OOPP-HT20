package com.example.planka.model;

import java.util.*;

/**
 * Class representing a Route.
 *
 * @author Lucas Karlsson.
 * @see AbstractReport
 */

public class Route {

    private String line;
    private List<Node> stops = new ArrayList<Node>();

    /**
     * Constructor of Route-object, takes parameter and passes it.
     *
     * @param name  String
     * @param stops List<Network.Node>
     */
    public Route(String name, List<Node> stops) {
        this.line = name;
        this.stops = stops;
    }

    public String getLine() {
        return this.line;
    }

    public List<Node> getNodes() {
        return this.stops;
    }

}