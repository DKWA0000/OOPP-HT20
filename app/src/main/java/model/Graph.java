package model;

import android.content.res.AssetManager;
import java.io.*;
import java.util.*;

/**
* A graph structure representing the public transportation network. 
*
* @see Graph.Node
* 
* @author: Seif Eddine Bourogaa.
*/
public class Graph {

    /*
        Make an adjacency list to store the nodes and their connections. 
    */


    private List<Route> routes = new ArrayList<>();
    private Map<Node, List<Node>> adjacencyList;
    private Map<String, Station> stations;


    /**
     * Constructor of Graph-object takes the AssetManager and passes it
     *
     * @param am AssetManager
     */
    public Graph(AssetManager am)
    {

        stations = new HashMap<>();
        adjacencyList = new HashMap<>();

        loadAllRoutes(am);
        mapAllNodes();

    }


    /**
     * Maps all the Nodes from every existing Route.
     * Loads the Nodes into the adjecencyList and also creates Stations and adds each Node to corresponding Station.
     */
    private void mapAllNodes() {
        for(Route r : routes){
            for(int i = 0 ; i < r.getNodes().size() ; i++){
                Node n = r.getNodes().get(i);

                //TODO: should be broken down into smaller method(s)
                if(!adjacencyList.containsKey(n)){
                    List<Graph.Node> nodes = new ArrayList<Graph.Node>();
                    if(i!=0)
                        nodes.add(r.getNodes().get(i-1));
                    if(i!=r.getNodes().size()-1)
                        nodes.add(r.getNodes().get(i+1));

                    adjacencyList.put(n,nodes);
                }
                else{
                    List<Graph.Node> list = adjacencyList.get(n);

                    if(i!=0 && !list.contains(r.getNodes().get(i-1)))
                        list.add(r.getNodes().get(i-1));

                    if(i!=r.getNodes().size()-1 && !list.contains(r.getNodes().get(i+1)))
                        list.add(r.getNodes().get(i+1));
                }


                if(!stations.containsKey(n.getStationName())){
                    Station s = new Station(n.getStationName());
                    s.addNode(n);
                    stations.put(n.getStationName(),s);
                }
                else{
                    stations.get(n.getStationName()).addNode(n);
                }
            }
        }


        /*      TESTING x)
        System.out.println("ALL STATIONS:");
        for (Iterator<String> it = stations.keySet().iterator(); it.hasNext(); ) {
            String s = it.next();
            System.out.print(s + " --> ");
            System.out.println(stations.get(s).getNodes());
        }


        System.out.println("ALL NODES:");
        for (Iterator<Node> it = adjacencyList.keySet().iterator(); it.hasNext(); ) {
            Node l = it.next();
            System.out.print(l + " --> ");
            System.out.println(adjacencyList.get(l));
        }
        */
    }

    /**
     * Creates new Routes from all .txt files in the /routes/ folder.
     *
     * @param am AssetManager to access /routes/
     */
    private void loadAllRoutes(AssetManager am){

        try {
            for (String s : am.list("routes/")) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(am.open("routes/" + s)));
                String name = s.replace(".txt" , "");
                ArrayList<Node> stops = new ArrayList<Node>();
                String line;
                while ((line = reader.readLine()) != null) {
                    stops.add(new Node(line.trim()));
                }

                routes.add(new Route(name,stops));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
    * Insert a new Node to the Graph, if it doesn not already exist. Every Node is given its own ArrayList
    * holding all the Nodes it is connecting to. 
    *
    * @param station name of the Node to create as well as the Graph.Node.station attribute 
    *
    * @see Graph.Node
    */
    public void addNode(String station)
    {
       adjacencyList.putIfAbsent(new Node(station), new ArrayList<>());
    }

    /**
    * Remove a Node and its ArrayList from the Graph.
    *
    * @param station name of the Node to remove from the Graph
    *
    * @see Graph.Node
    */
    public void removeNode(String station)
    {
        adjacencyList.values().stream().forEach(e -> e.remove(new Node(station)));
        adjacencyList.remove(new Node(station));
    }

    
    /**
    * Create an edge between any two Nodes in the Graph by adding the @param destination Node
    * into the Arraylist of @param source Node. Such that source Node maps to the destination Node, but
    * destination Node does not map to the source Node. 
    *
    * @param source name of the node to map from
    * @param destination name of the node to map to 
    *
    * @see Graph.Node
    */
    public void addEdge(String source, String destination)
    {
        adjacencyList.get(new Node(source)).add(new Node(destination));
        List<Node> abc = adjacencyList.get(new Node(source));
    }


    /**
    * Remove an an edge between any two Nodes in the Graph by removing @param destination Node from
    * ArrayList of @param souce Node. Such that @param source no longer maps to @param destination. 
    *
    * @param source name of Node to remove mapping from
    * @param destination name of Node to remove mapping to
    *
    * @see Graph.Node
    */
    public void removeEdge(String source, String destination)
    {
        List<Node> sourceEdges = adjacencyList.get(new Node(source));
        if (sourceEdges != null)
        {
            sourceEdges.remove(new Node(destination));
        }
    }

    /**
    * Get Nodes adjacent to Node @param station
    *
    * @param station name of the Node whos adjacent Nodes we are looking for
    *
    * @see Graph.Node
    *
    * @return List containing all adjacent nodes to @param station
    *
    * To do: This needs to be fixed. It currently only finds Nodes @param station is mapping to, not Nodes
    * that are mapping to @param station too. 
    */
    public List<Node> getAdjacentNodes(String station)
    {
        //TODO: Maybe use this code for the other method?
        ArrayList<Node> nodes = new ArrayList<Node>();


        for (Node n :adjacencyList.get(new Node(station))
             ) {
            nodes.add(n);
        }

        return nodes;
    }

    /**
    * Temporary Node class until I can fix it. Need Graph specific information to get keys to work
    * smoothly in order to find and adress Nodes.
    */
    public class Node {
        private String name;
        public Node(String name) {
            this.name = name;
        }

        @Override
        /*
            To make it easier to find our nodes.
        */
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getStationName(){
            return name.substring(0,name.lastIndexOf(' '));
        }


        private Graph getOuterType() {
            return Graph.this;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * Returns the Node before the given Node in given Route.
     * Returns null if there is no Node before given Node.
     *
     * @param n Node of interest
     * @param r Route of interest
     * @return The Node before n
     */
    public Node getPrevNode(Node n ,Route r){

        for (int i = 1; i < r.getNodes().size(); i++) {
            Node current = r.getNodes().get(i);
            if(current == n){
                return r.getNodes().get(i-1);
            }
        }
        return null;
    }


    /**
     * Returns the Node after the given Node in given Route.
     * Returns null if there is no Node after given Node.
     *
     * @param n Node of interest
     * @param r Route of interest
     * @return The Node after n
     */
    public Node getNextNode(Node n ,Route r) {

        for (int i = 0; i < r.getNodes().size()-1; i++) {
            Node current = r.getNodes().get(i);
            if(current == n){
                return r.getNodes().get(i+1);
            }
        }
        return null;
    }

    /**
     * Returns all Nodes in the given Route.
     * @param r Route of interest
     * @return An ordered list of all nodes in that Route
     */
    public List<Graph.Node> getRouteNodes(Route r){
        return r.getNodes();
    }

    /**
     * Returns the Sation of the given Node.
     * @param n Node of interest
     * @return The Nodes Station
     */
    public Station getNodeStation(Node n){
        return stations.get(n.getStationName());
    }

    /**
     * Returns all Nodes at the given Station.
     * @param s Station of interest
     * @return An ordered list of all nodes at that Station
     */
    public List<Graph.Node> getStationRoutes(Station s){
        return s.getNodes();
    }


    public void getAdjacentNodes(Node n){
        // TODO:
    }

    public void getAdjacentStations(Station s){
        // TODO: Maybe use getAdjacentNodes() or vice versa?
    }
}
