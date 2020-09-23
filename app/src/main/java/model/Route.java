package model;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.*;
import java.util.*;

public class Route {

    private String line;
    private List<Graph.Node> stops = new ArrayList<Graph.Node>();

    public Route(String name, List<Graph.Node> stops){
        this.line = name;
        this.stops = stops;
    }

    public String getLine(){
        return this.line;
    }

    public List<Graph.Node> getNodes(){
        return this.stops;
    }



}
