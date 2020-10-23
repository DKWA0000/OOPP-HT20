package com.example.planka.model;

import java.util.*;

/**
 * A graph structure representing the public transportation network.
 *
 * @see Node
 * @see Station
 * @see Route
 *
 * @author Seif Eddine Bourogaa
 */
public class Network {

    /**
     * @see #adjacencyList Graph representing the tram network.
     * @see #stations Graph representing Stations.
     * @see #routes List of all tram lines in the tram network.
     * @see #allAffectedNodes all Nodes affected by a report.
     * @see #allAffectedRoutes all Routes affected by a report.
     */
    private Map<Node, List<Node>> adjacencyList;
    private Map<String, Station> stations;
    private List<Route> routes = new ArrayList<>();
    private List<Node> allAffectedNodes = new ArrayList<>();
    private List<Route> allAffectedRoutes = new ArrayList<>();

    /**
     * Constructor of Network, takes a HashMap and passes it to createRoutes.
     *
     * @param routesFromFile HashMap containing information about every Route.
     *
     * @see #createRoutes(HashMap)
     */
    public Network(HashMap<String, ArrayList> routesFromFile){
        stations = new HashMap<>();
        adjacencyList = new HashMap<>();

        createRoutes(routesFromFile);
        mapAllNodes(routes);
    }

    /**
     * Creates all the routes.
     *
     * @param routesFromFile A HashMap with information about each route's stops.
     */
    private void createRoutes(HashMap<String, ArrayList> routesFromFile){

        for (Map.Entry<String, ArrayList> entry : routesFromFile.entrySet()){
            String route = entry.getKey();
            ArrayList<String> values = entry.getValue();
            ArrayList<Node> stops = new ArrayList<>();

            for(String value : values){
                stops.add(new Node(value));
            }
            routes.add(new Route(route, stops));
        }
    }

    /**
     * See if a Route is impacted by recently reported Nodes. Meaning, if any node the route passes through has controllers nearby.
     * If they are, add them to the collection of affected Routes.
     *
     * @param nodes All nodes with controllers nearby.
     */
    private void impactedRoutes(List<Node> nodes){
        for(Node node : nodes){
            for(Route route : routes){
                if(route.getNodes().contains(node)){
                    setActiveControllersRoutes(route);
                }
            }

        }
    }

    /**
     * Set method to add routes impacted by nodes to the List holding routes impacted by nodes.
     *
     * @param nodes All nodes with controllers nearby.
     *
     * @see #impactedRoutes(List)
     */
    private void setImpactedRoutes(List<Node> nodes){
        impactedRoutes(nodes);
    }

    /**
     * Get method to find all routes that has been affected by a report.
     *
     * @return all routes that has been affected by a report.
     */
    public List<Route> getAllImpactedRoutes(){
        return allAffectedRoutes;
    }

    /**
     * Method to get a Route from a String.
     *
     * @param routeString Name of the route we want.
     *
     * @return Route object.
     */
    public Route getRouteFromString(String routeString){
        for(Route route : routes){
            if(route.getLine().equals(routeString)){
                return route;
            }
        }
        return null;
    }

    /**
     * Method to see if a Route user have selected is affected by controllants.
     *
     * @param routeString Route user want to know about.
     *
     * @return True if affected by controllers, false otherwise.
     */
    public boolean userRouteImpacted(String routeString){

        Route route = getRouteFromString(routeString);

        if(allAffectedRoutes.contains(route)){
            return true;
        }
        return false;
    }

    /**
     * Maps all the Nodes from every existing Route. Loads the Nodes into the adjacencyList and also creates Stations
     * and adds each Node to corresponding Station.
     *
     * @param routes List of all routes to map into Network
     *
     * @see #nodeExist(Node)
     * @see #newNode(Node, Route, int)
     * @see #existingNode(Node, Route, int)
     * @see #createStations(Node)
     */
    private void mapAllNodes(List<Route> routes){

        for(Route route : routes){
            for(int i = 0; i < route.getNodes().size(); i++){
                Node node = route.getNodes().get(i);

                if(nodeExist(node)){
                    existingNode(node, route, i);
                }
                else{
                    newNode(node, route, i);
                }
                createStations(node);
            }
        }
    }

    /**
     * Adds a new Node to the Network and Maps its connections.
     *
     * @param node node to add to the network
     * @param route route the node belongs to
     * @param position the node's position in the route
     *
     * @see #addNode(String)
     * @see #addEdge(String, String)
     */
    private void newNode(Node node, Route route, int position){
        addNode(node.getName());

        if (position != 0){
            addEdge(node.getName(), route.getNodes().get(position - 1).getName());
        }
        if (position != route.getNodes().size() - 1){
            addEdge(node.getName(), route.getNodes().get(position + 1).getName());
        }
    }

    /**
     * Maps new connections for an existing Node.
     *
     * @param node node to add to the network
     * @param route route the node belongs to
     * @param position the node's position in the route
     *
     * @see #addEdge(String, String)
     */
    private void existingNode(Node node, Route route, int position){
        List<Node> listOfDestinations = adjacencyList.get(node);

        if(position != 0 && !listOfDestinations.contains(route.getNodes().get(position - 1))){
            addEdge(node.getName(), route.getNodes().get(position - 1).getName());
        }

        if(position != route.getNodes().size() - 1 && !listOfDestinations.contains(route.getNodes().get(position + 1))){
            addEdge(node.getName(), route.getNodes().get(position + 1).getName());
        }
    }

    /**
     * Check if a Node exists in the Network.
     */
    private boolean nodeExist(Node node){
        return adjacencyList.containsKey(node);
    }

    /**
     * Method for mapping which Station a Node belongs to.
     *
     * @param node Node to map to a station
     *
     * @see #stationExist(Node)
     * @see #existingStation(Node)
     * @see #newStation(Node)
     */
    private void createStations(Node node){
        if(stationExist(node)){
            existingStation(node);
        }
        else{
            newStation(node);
        }
    }
    /**
     * Creates a new Station for the Node and adds the Node to it.
     *
     * @param node Node to create a new Station for
     *
     * @see #getStationName(Node)
     * @see Station
     * @see Station#addNode(Node)
     */
    private void newStation(Node node){
        Station station = new Station(getStationName(node));
        station.addNode(node);
        stations.put(getStationName(node), station);
    }

    /**
     * Adds Node to an existing Station.
     *
     * @param node Node to add into a station
     *
     * @see #getStationName(Node)
     * @see Station
     * @see Station#addNode(Node)
     */
    private void existingStation(Node node){
        stations.get(getStationName(node)).addNode(node);
    }


    /**
     * Check if a Station for a Node exists.
     *
     * @param node Node to check if has a station already.
     */
    private boolean stationExist(Node node){
        return stations.containsKey(node);
    }

    /**
     * Change states at Nodes to imply that controllers are present nearby and add them to the List containing nodes with
     * controllers nearby.
     *
     * @param nodes List of Nodes that should have their state changed
     *
     * @see Node
     * @see #impactedRoutes(List)
     */
    public void setActiveControllersNodes(List<Node> nodes){

        for(Node node : nodes){
            node.setState(true);
            allAffectedNodes.add(node);
        }
        setImpactedRoutes(nodes);
    }

    /**
     * Change states at Nodes to imply that there are no controllers nearby and remove them from the List containing
     * nodes with controllers nearby.
     *
     * @param nodes List of Nodes that should have their state changed
     *
     * @see Node
     *
     * Note: Kept for further development purposes.
     */
    public void removeActiveControllersNodes(List<Node> nodes){
        for(Node node : nodes){
            node.setState(false);
            allAffectedNodes.remove(node);
        }
    }


    /**
     * Add a route to the List containing Routes affected by controllers.
     *
     * @param route to add
     *
     * @see Route
     */
    public void setActiveControllersRoutes(Route route){

        allAffectedRoutes.add(route);
    }

    /**
     * Remove a route from the List containing routes affected by controllers.
     *
     * @param route to remove
     *
     * @see Route
     *
     * Note: Kept for further development purposes.
     */
    public void removeActiveControllersRoutes(Route route){
        allAffectedRoutes.remove(route);
    }

    /**
     * Insert a new Node to the Graph, if it does not not already exist. Every Node is given its own ArrayList
     * holding all the Nodes it is connecting to.
     *
     * @param station name of the Node to create as well as the Graph.Node.station attribute
     *
     * @see Node
     */
    private void addNode(String station){
        adjacencyList.putIfAbsent(new Node(station), new ArrayList<>());
    }

    /**
     * Remove a Node and its ArrayList from the Graph.
     *
     * @param station name of the Node to remove from the Graph
     *
     * @see Node
     *
     * Note: Kept for further development purposes.
     */
    private void removeNode(String station){
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
     * @see Node
     */
    private void addEdge(String source, String destination){
        adjacencyList.get(new Node(source)).add(new Node(destination));
    }

    /**
     * Remove an an edge between any two Nodes in the Graph by removing @param destination Node from
     * ArrayList of @param source Node. Such that @param source no longer maps to @param destination.
     *
     * @param source name of Node to remove mapping from
     * @param destination name of Node to remove mapping to
     *
     * @see Node
     *
     * Note: kept for further development purposes.
     */
    private void removeEdge(String source, String destination){
        List<Node> sourceEdges = adjacencyList.get(new Node(source));
        if (sourceEdges != null){
            sourceEdges.remove(new Node(destination));
        }
    }

    /**
     * Method to get adjacent nodes for a list of nodes.
     * @param nodes one want to find adjacent nodes for.
     * @param range how far one want to check for adjacent nodes.
     *
     * @return all adjacent nodes.
     *
     * @see #adjacentNodes(String, int)
     */
    public List<Node> getAdjacentNodes(List<Node> nodes, int range){
        List<Node> adjacentNodes = new ArrayList<>();

        for(Node node : nodes){
            adjacentNodes.addAll(adjacentNodes(node.getName(), range));
        }
        return adjacentNodes;
    }

    /**
     * Get Nodes adjacent to Node @param station
     *
     * @param station name of the Node whose adjacent Nodes we are looking for
     * @param range how many stations away we want to find adjacent stations.
     *
     * @see Node
     *
     * @return List containing all adjacent nodes to @param station
     */
    private List<Node> adjacentNodes(String station, int range){
        Set<Node> adjacentNodes = new HashSet<>();
        adjacentNodes.addAll(getNodesAhead(station));
        adjacentNodes.addAll(getNodesBehind(station));

        for(int i = 1; i < range; i++){
            Set<Node> temp = new HashSet<>();

            for(Node node : adjacentNodes){
                temp.addAll(getNodesAhead(node.getName()));
                temp.addAll(getNodesBehind(node.getName()));
            }
            adjacentNodes.addAll(temp);
        }
        List<Node> adjacent = new ArrayList<>(adjacentNodes);
        return adjacent;
    }

    /**
     * Get Nodes ahead of the @param station Node.
     *
     * @param station name of the Node whose forward Nodes we are looking for
     *
     * @see Node
     *
     * @return List containing all nodes ahead of @param station
     */
    private List<Node> getNodesAhead(String station){
        List<Node> nodesAhead = adjacencyList.get(new Node(station));
        return nodesAhead;
    }

    /**
     * Get Nodes ahead of the @param station Node.
     *
     * @param station name of the Node whose backward Nodes we are looking for
     *
     * @see Node
     *
     * @return List containing all nodes behind of @param station
     */
    private List<Node> getNodesBehind(String station){

        List<Node> nodesBehind = new ArrayList<>();

        for(Node key: adjacencyList.keySet()){
            if(adjacencyList.get(key).contains(new Node(station))){
                nodesBehind.add(key);
            }
        }
        return nodesBehind;
    }

    /**
     * Returns a specific Station
     * @param station name of the Station
     * @return Station object with given name
     *
     * @see Station
     */
    public Station getStation(String station){
        return stations.get(station);
    }

    /**
     * Returns all station names
     * @return String[] of all station names
     *
     * @see Station
     */
    public String[] getAllStationNames(){
        return stations.keySet().toArray(new String[0]);
    }

    /**
     * Get the name of which station a node belongs to.
     *
     * @return the name of the station it belongs to.
     *
     * @see Station
     */
    public String getStationName(Node node){
        return node.getName().substring(0,node.getName().lastIndexOf(' '));
    }
}
