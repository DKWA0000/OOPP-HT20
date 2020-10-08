package model;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.*;
import java.util.*;

/**
* A graph structure representing the public transportation network. 
*
* @see Network.Node class
* @see Station class 
* @see Route class
*
* @author Seif Eddine Bourogaa
* @author Joakim something?
*/
public class Network {

    /*
        Make an adjacency list to store the nodes and their connections. 
    */
    private Map<Node, List<Node>> adjacencyList;
    private Map<String, Station> stations;
    private List<Route> routes = new ArrayList<>();

    /**
     * Constructor of Graph-object takes the AssetManager and passes it
     *
     * @param am AssetManager
     */
    public Network(HashMap<String, ArrayList> routesFromFile) {

        stations = new HashMap<>();
        adjacencyList = new HashMap<>();

        createRoutes(routesFromFile);
        mapAllNodes(routes);

    }

    /**
     * Creates all the routes.
     *
     * @param routesFromFile contains a List of lists, with information about each route's stops.
     *
     * @see FileReader
     */
    private void createRoutes(HashMap<String, ArrayList> routesFromFile) {

        for (Map.Entry<String, ArrayList> entry : routesFromFile.entrySet())
        {
            String route = entry.getKey();
            ArrayList<String> values = entry.getValue();
            ArrayList<Network.Node> stops = new ArrayList<>();

            for(String value : values) {
                stops.add(new Node(value));
            }
            routes.add(new Route(route, stops));
        }

    }

    /**
     * Maps all the Nodes from every existing Route.
     * Loads the Nodes into the adjacencyList and also creates Stations and adds each Node to corresponding Station.
     *
     * TODO: Should be broken down into smaller methods and use the currently implemented methods addEdge and addNode.
     */
    private void mapAllNodes(List<Route> routes) {
        for(Route r : routes){
            for(int i = 0 ; i < r.getNodes().size() ; i++){
                Node n = r.getNodes().get(i);

                //TODO: should be broken down into smaller method(s)
                if(!adjacencyList.containsKey(n)){
                    List<Network.Node> nodes = new ArrayList<Network.Node>();
                    if(i!=0)
                        nodes.add(r.getNodes().get(i-1));
                    if(i!=r.getNodes().size()-1)
                        nodes.add(r.getNodes().get(i+1));

                    adjacencyList.put(n,nodes);
                }
                else{
                    List<Network.Node> list = adjacencyList.get(n);

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
    }

    /**
    * Insert a new Node to the Graph, if it doesn not already exist. Every Node is given its own ArrayList
    * holding all the Nodes it is connecting to. 
    *
    * @param station name of the Node to create as well as the Graph.Node.station attribute 
    *
    * @see Network.Node
    */
    public void addNode(String station)
    {
       //adjacencyList.putIfAbsent(new Node(station), new ArrayList<>());
    }

    /**
    * Remove a Node and its ArrayList from the Graph.
    *
    * @param station name of the Node to remove from the Graph
    *
    * @see Network.Node
    */
    @RequiresApi(api = Build.VERSION_CODES.N)
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
    * @see Network.Node
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
    * @see Network.Node
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
    * @param station name of the Node whose adjacent Nodes we are looking for
    * @param range how many stations away we want to find adjacent stations. 
    *
    * @see Network.Node
    *
    * @return List containing all adjacent nodes to @param station
    */
    public List<Node> getAdjacentNodes(String station, int range)
    {
       List<Node> adjacentNodes = new ArrayList<Node>(); 
       List<Node> temp = new ArrayList<Node>();
       temp.addAll(getNodesAhead(station));
       temp.addAll(getNodesBehind(station));
       
       for(int i = 1; i < range; i++)
       {
       		for(Network.Node node : adjacentNodes){

     				temp.addAll(getNodesAhead(node.name));
     				temp.addAll(getNodesBehind(node.name));

     		}
    	}

    	for(Node node : temp){
    		if(!adjacentNodes.contains(node))
    		{
    			adjacentNodes.add(node); 
    		}
    	}

       return adjacentNodes;
    }

	/**
    * Get Nodes ahead of the @param station Node. 
    *
    * @param station name of the Node whose forward Nodes we are looking for
    *
    * @see Network.Node
    *
    * @return List containing all nodes ahead of @param station
    */
    public List<Node> getNodesAhead(String station)
    {

		List<Node> nodesAhead = adjacencyList.get(new Node(station));
		return nodesAhead;

    }

    /**
    * Get Nodes ahead of the @param station Node. 
    *
    * @param station name of the Node whose backward Nodes we are looking for
    *
    * @see Network.Node
    *
    * @return List containing all nodes behind of @param station
    */
    public List<Node> getNodesBehind(String station){

    	List<Node> nodesBehind = new ArrayList<Node>(); 


    	for(Network.Node key: adjacencyList.keySet())
        {
           	if(adjacencyList.get(key).contains(new Node(station)))
           	{
           	    nodesBehind.add(key);
           	}
        }
        
        return nodesBehind;

    }

    /**
     * Returns the Node before the given Node in given Route.
     * Returns null if there is no Node before given Node.
     *
     * @param n Node of interest
     * @param r Route of interest
     *
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
     * Returns the Node after the given Node in given a given Route.
     * Returns null if there is no Node after given Node.
     *
     * @param n Node of interest
     * @param r Route of interest
     *
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
     * 
     * @param r Route of interest
     *
     * @return An ordered list of all nodes in that Route
     */
    public List<Network.Node> getRouteNodes(Route r){
        return r.getNodes();
    }

    /**
     * Returns a specific Station
     * @param station name of the Station
     * @return Station object with given name
     */
    public Station getStation(String station) {
        return stations.get(station);
    }

    /**
     * Returns the Station of a given Node.
     *
     * @param n Node of interest
     *
     * @return The Nodes Station
     */
    public Station getNodeStation(Node n){
        return stations.get(n.getStationName());
    }

    /**
     * Returns all Nodes at the given Station.
     *
     * @param s Station of interest
     *
     * @return An ordered list of all nodes at that Station
     */
    public List<Network.Node> getStationRoutes(Station s){
        return s.getNodes();
    }

    /**
     * Returns all station names
     * @return String[] of all station names
     */
    public String[] getAllStationNames() {
        return stations.keySet().toArray(new String[0]);
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

        /**
        * Used by our hash.
        */
        private Network getOuterType() {
            return Network.this;
        }

        /**
        * Get the name of which station a node belongs to. 
		*
        * @return the name of the station it belongs to. 
        */
        public String getStationName(){
            return name.substring(0,name.lastIndexOf(' '));
        }


        /**
        * Get the name of the Node.
        *
        * @return Name of the node. 
        */
        public String getName() {
            return this.name;
        }
    }
}
