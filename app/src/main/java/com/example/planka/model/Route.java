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
    private List<Node> stops = new ArrayList<>();

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

    /**
     * Method to get name of a Route.
     *
     * @return Name of the Route.
     */
    public String getLine() {
        return this.line;
    }

    /**
     * Method to get all Nodes in a Route.
     *
     * @return Nodes in a Route.
     *
     * @see Node
     */
    public List<Node> getNodes() {
        return this.stops;
    }

}