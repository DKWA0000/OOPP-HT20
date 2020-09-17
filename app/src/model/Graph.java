import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    /*
        Make the graph an adjacency list.
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
        Insert new node if it doesn't already exist etc.
    */
    public void addNode(String station)
    {
       adjacencyList.putIfAbsent(new Node(station), new ArrayList<>());
    }

    /*
        Remove node.
    */
    public void removeNode(String station)
    {
        Node n = new Node(station);
        adjacencyList.values().stream().forEach(e -> e.remove(n));
        adjacencyList.remove(new Node(station));
        System.out.print(adjacencyList.size());
    }

    /*
        Add edge between two nodes. This edge is one directional.
    */
    public void addEdge(String source, String destination)
    {
        Node sourceNode = new Node(source);
        Node destinationNode = new Node(destination);
        adjacencyList.get(sourceNode).add(destinationNode);
    }


    /*
        Remove edge between a source and destination node.
    */
    public void removeEdge(String source, String destination)
    {
        Node sourceNode = new Node(source);
        Node destinationNode = new Node(destination);
        List<Node> sourceEdges = adjacencyList.get(sourceNode);
        if (sourceEdges != null)
        {
            sourceEdges.remove(destinationNode);
        }
    }

    /*
        Get nodes adjacent to x node.
    */
    public List<Node> getAdjacentNodes(String station)
    {
       return adjacencyList.get(new Node(station));
    }

    /*
        Create route and add nodes and its edges to network.
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
