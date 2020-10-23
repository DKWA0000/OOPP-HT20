package com.example.planka.model;

import java.util.*;

/**
 * Class representing a Station.
 *
 * @author Lucas Karlsson.
 * @see AbstractReport
 */

public class Station {

    private String name;
    private ArrayList<Node> nodeList;

    /**
     * Constructor of Station-object, takes the name and passes it.
     *
     * @param name String
     */
    public Station(String name) {
        this.name = name;
        this.nodeList = new ArrayList<Node>();
    }

    /**
     * Method for adding a node to a Station
     *
     * @param node Node
     */
    public void addNode(Node node) {
        if (!nodeList.contains(node)) {
            nodeList.add(node);
        }
    }

    /**
     * Method to get the name of a Station.
     *
     * @return name of a Station
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get all the Nodes in a Station.
     *
     * @return all Nodes in a Station
     *
     * @see Node
     */
    public ArrayList<Node> getNodes() {
        return this.nodeList;
    }

}