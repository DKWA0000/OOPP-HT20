package model;

import java.io.*;
import java.util.*;


public class Station {

    private String name;
    private ArrayList<Node> nodeList;

    public Station(String name, ArrayList nodeList){
        this.name = name;
        this.nodeList = nodeList;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList getnodes(){
        return this.nodeList;
    }




}
