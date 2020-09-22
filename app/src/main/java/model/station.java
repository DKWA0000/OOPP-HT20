import java.io.*;
import java.util.*;

public class station {

    private String name;
    private ArrayList<Node> nodeList;

    /**
     * Constructor for creating station-objects.
     * @param name
     * @param nodeList
     */
    public station(String name, ArrayList nodeList) {
        this.name = name;
        this.nodeList = nodeList;
    }

    /**
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return ArrayList nodes
     */
    public ArrayList getnodes() {
        return this.nodeList;
    }

    /**
     * Takes a path and returns a list of stations created
     * from CSV-files in path.
     * @param path
     * @return ArrayList stations
     * @throws IOException
     */

    public ArrayList createStations(String path) throws IOException{

        File[] f = new File(path).listFiles();
        String line;
        String subString;
        ArrayList<station> stations = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();

        for (int i = 0; i < f.length; i++) {

            BufferedReader br = new BufferedReader(new FileReader(f[i]));

            while ((br.readLine()) != null) {

                    line = br.readLine();
                    station s;

                    if(line != null && !(tmp.contains(line))){

                        String[] l = line.split(",");
                        subString = l[0];

                        if(!(tmp.contains(subString))) {
                            stations.add(new station(subString, new ArrayList()));
                        }
                        tmp.add(line);
                        stations.get(stations.size() - 1).nodeList.add(new Node(line));
                    }
            }
        }

        return stations;
}


}
