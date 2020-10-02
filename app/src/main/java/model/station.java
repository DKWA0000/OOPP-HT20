package model;

import java.util.*;


public class Station {

    private String name;
    private ArrayList<Network.Node> nodeList;

    public Station(String name) {
        this.name = name;
        this.nodeList = new ArrayList<Network.Node>();
    }

    public void addNode(Network.Node n){
        if(!nodeList.contains(n)) {
            nodeList.add(n);
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Network.Node> getNodes() {
        return this.nodeList;
    }



    // TODO: .equals() !?


}