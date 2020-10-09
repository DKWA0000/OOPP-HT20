

import java.util.*;


public class testStation {

    private String name;
    private ArrayList<testNetwork.Node> nodeList;

    public testStation(String name) {
        this.name = name;
        this.nodeList = new ArrayList<testNetwork.Node>();
    }

    public void addNode(testNetwork.Node n){
        if(!nodeList.contains(n)) {
            nodeList.add(n);
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<testNetwork.Node> getNodes() {
        return this.nodeList;
    }



    // TODO: .equals() !?


}
