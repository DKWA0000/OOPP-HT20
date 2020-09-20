import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    /*
        Make an adjacency list to store the nodes and its connections. 
    */

    private Map<Node, List<Node>> adjacencyList;
    
    public Graph()
    {
        /*
            Use HashTable for smoother insertion and removal?
        */
        this.adjacencyList = new HashMap<>();
    }

    /*
        Insert a new Node to the Graph, if it does not already exist. Every Node
        is given its own ArrayList holding all Nodes it is connecting to. 

        Args:
            @station: Name of the Node to create as well as the Graph.Node.station attribute.

        See: 
            Class: Graph.Node
    */
    public void addNode(String station)
    {
       adjacencyList.putIfAbsent(new Node(station), new ArrayList<>());
    }

    /*
        Remove a Node and its ArrayList from the Graph.

        Args: 
            @station: Name of the Node to remove from the Graph.  

    */
    public void removeNode(String station)
    {
        adjacencyList.values().stream().forEach(e -> e.remove(new Node(station)));
        adjacencyList.remove(new Node(station));
    }

    /*
        Create an edge between any two Nodes in the Graph by adding the @destination Node
        into ArrayList of @source Node. Such that @source maps to @destination, but @destination
        does not map to @source. 

        Args:
            @source: Name of Node to map from.
            @destination: Name of Node to map to.   
    */
    public void addEdge(String source, String destination)
    {
        adjacencyList.get(new Node(source)).add(new Node(destination));
        List<Node> abc = adjacencyList.get(new Node(source));
    }


    /*
        Remove an edge between any two Nodes in the Graph by removing @destination Node from ArrayList
        of @source Node. Such that @source no longer maps to @destination.

        Args:
            @source: Name of Node to remove mapping from.
            @destination: Name of Node to remove mapping to.  
    */
    public void removeEdge(String source, String destination)
    {
        List<Node> sourceEdges = adjacencyList.get(new Node(source));
        if (sourceEdges != null)
        {
            sourceEdges.remove(new Node(destination));
        }
    }

    /*
        Get Nodes adjacent to Node @station.

        Args: 
            @station: Name of Node to find adjacent Nodes to.

        To do: 
            This needs to be fixed. I just realized it only get Nodes mapped from it.
    */
    public List<Node> getAdjacentNodes(String station)
    {
       return adjacencyList.get(new Node(station));
    }

    /*
        Add an entire route to the Graph. Adds every station as a Node, and maps every connection.

        Args: 
            @pathToFolderHoldingFiles: name of file containing every relevant route informaiton.

        See: 
            @getNodesFromCSV()

        To do: 
            This should be moved to route Class.  
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

    /*
        Get data from CSV file.
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

    /*
        AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
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
