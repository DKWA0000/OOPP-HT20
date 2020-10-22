package model;

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

    public void addNode(Node n) {
        if (!nodeList.contains(n)) {
            nodeList.add(n);
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Node> getNodes() {
        return this.nodeList;
    }
    // TODO: .equals() !?
}