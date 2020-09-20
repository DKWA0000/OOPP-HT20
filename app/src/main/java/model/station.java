import java.io.*;
import java.util.*;


public class station {

    private String name;
    private ArrayList<Graph.Node> nodeList;

    public station(String name, ArrayList nodeList){
        this.name = name;
        this.nodeList = nodeList;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList getnodes(){
        return this.nodeList;
    }

    public ArrayList<station> createStations(String path){

        ArrayList<station> result = new ArrayList<>();
        ArrayList<String> tmp = multibleListToOne(path);
        int i = 0;

        for(int i = 0; i < tmp.size(); i++){
            if(!(result.contains(tmp.get(i)))){
                result.add(new station(tmp.get(i), new ArrayList()));
            }
            for(int j = 0; i < tmp.size(); i++){
                if()

            }
        }

    }

    private ArrayList multibleListToOne(String path) throws IOException {

        ArrayList<String> result = new ArrayList<>();
        File[] f = new File(path).listFiles();
        List<String> tmp;
        String[] fileNames;

        for(int i = 0; i < f.length; i++){
            fileNames[i] = (f[i].getName());
        }

        for(int i = 0; i < result.size(); i++) {
            BufferedReader br = new BufferedReader(new FileReader(result.get(i)));
            String line;

            while ((line = br.readLine()) != null) {
                {
                    String[] l = line.split(",");
                    tmp = Arrays.asList(l);
                    System.out.println(Arrays.toString(l));

                for(int j = 0; j < tmp.size(); j++) {
                    result.add(tmp.get(i));
                }
            }
            }

        }

        return result;
    }

}
