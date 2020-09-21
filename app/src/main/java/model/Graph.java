package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private Map<Node, List<Node>> adjacencyList;
    
    public Graph()
    {
        /*
            Use HashTable for smoother insertion and removal.
        */
        this.adjacencyList = new HashMap<>();
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
       return adjacencyList.get(new Node(station));
    }

    /**
    * Add an entire route to the Graph. Adds every station and maps every connection for the route.
    *
    * @param pathToFolderHoldingFiles path to .csv file holding route specification.  
    *
    * @see Graph.Node
    * @see getNodesFromCSV()
    *
    * To do: This should be moved to the Class Route. 
    */
    public void createRoute(String pathToFolderHoldingFiles) throws IOException, FileNotFoundException
    {

        List<String> routeNodes = getNodesFromCSV(pathToFolderHoldingFiles);

        /*
            Add all stations to network.
         */
        for(int i = 0; i < routeNodes.size(); i++)
        {
            addNode(routeNodes.get(i));
        }

        /*
            Create edge between stations.
         */
        for(int i = 0; i < routeNodes.size(); i++)
        {
            if(i + 1 < routeNodes.size())
            {
                addEdge(routeNodes.get(i), routeNodes.get(i+1));
            }
        }
    }

    /**
    * Reads route specification from a file. 
    *
    * @param pathToFolderHoldingFiles path to .csv file holding route specification. 
    *
    * @return List of all stations for a route.  
    *
    * @see Graph.Node
    */
    public List<String> getNodesFromCSV(String pathToFolderHoldingFiles) throws IOException, FileNotFoundException
    {
        String file = "/home/loqotia/GraphTest/Stations/" + pathToFolderHoldingFiles;
        List<List<String>> temp = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] nodes = line.split(",");
                temp.add(Arrays.asList(nodes));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        /*
            We want a 1D list, not 2D. Convert it.
        */
        List<String> routeNodes = temp.stream().flatMap(List::stream).collect(Collectors.toList());

        return routeNodes;
    }

    /**
    * Temporary Node class until I can fix it. Need Graph specific information to get keys to work
    * smoothly in order to find and adress Nodes.
    */
    class Node {
        String station;
        Node(String station) {
            this.station = station;
        }

        @Override
        /*
            To make it easier to find our nodes.
        */
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((station == null) ? 0 : station.hashCode());
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
            if (station == null) {
                if (other.station != null)
                    return false;
            } else if (!station.equals(other.station))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return station;
        }


        private Graph getOuterType() {
            return Graph.this;
        }
    }
}
