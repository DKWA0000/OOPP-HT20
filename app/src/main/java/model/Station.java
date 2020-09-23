package model;

import java.io.*;
import java.util.*;


public class Station {

    private String name;
    private ArrayList<Graph.Node> nodeList;

    public Station(String name) {
        this.name = name;
        this.nodeList = new ArrayList<Graph.Node>();
    }

    public void addNode(Graph.Node n){
        if(!nodeList.contains(n)) {
            nodeList.add(n);
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Graph.Node> getNodes() {
        return this.nodeList;
    }






}
