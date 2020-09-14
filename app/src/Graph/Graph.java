import java.util.*;

public class Graph {

    /*
    Make the graph an adjacency list.
     */
    private Map<Node, LinkedList<Node>> adjacencyList;

    public Graph()
    {
        /*
        Use HashTable for smoother insertion and removal?
         */
        adjacencyList = new HashMap<>();
    }

    /*
    Insert new node if it doesn't already exist etc. 
     */
    public void insert(Node source, Node destination)
    {
        if(!adjacencyList.keySet().contains(source))
        {
            adjacencyList.put(source, null);
        }
        if(!adjacencyList.keySet().contains(destination))
        {
            adjacencyList.put(destination, null);
        }
        LinkedList<Node> temp = adjacencyList.get(source);
        if(temp == null)
        {
            temp = new LinkedList<>();
        }
        temp.add(destination);
        adjacencyList.put(source, temp);
    }
    /*
    Traverse graph to see we have the correct edges during testing.

    Maybe?: Change this for an DFS algorithm that takes the first station, much better IMO. 
    */
    public void traverse()
    {
        for(Node root: adjacencyList.keySet())
        {
            System.out.print("Edge from " + root.station + " - ");
            LinkedList<Node> vertices = adjacencyList.get(root);
            if(vertices != null)
            {
                for (Node adjacent : adjacencyList.get(root))
                {
                    System.out.print(adjacent.station);
                }
            }
            System.out.println();
        }

    }

    /*
    Method to create an entire graph using a .cvs file containing all the stations for a line. 

    To fix: Reading data from .cvs file should have its own method etc and pass the data to this method.
    */
    public void createGraph(pathToFolderHoldingFiles)
    {
        BufferedReader read = new BufferedReader(new FileReader(pathToFolderHoldingFiles)); 

        while((line = read.readLine()) != null))
        {
            String[] stations = line.split("asd") //Whatever our delimeter will be. Will decide once I start doing the file.
        }

        /*
        To do: 

        Create the graph. Index 0 -> Index 1, Index 1 -> Index 2, etc. until we reach the last index.
        */
    }
}


}
