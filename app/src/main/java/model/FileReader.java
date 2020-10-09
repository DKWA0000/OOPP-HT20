package model;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Responsible for reading files.
 *
 * @see Network.Node class
 * @see Route class
 *
 * @author Seif Eddine Bourogaa
 */

public class FileReader {

    HashMap<String, ArrayList> fileContent = new HashMap<String, ArrayList>();

    /**
     * Constructor of FileReader, takes the AssetManager and passes it to loadAllRoutes.
     *
     * @param am AssetManager
     *
     * @see loadAllRoutes();
     */
    public FileReader(AssetManager am) {
        loadAllRoutes(am);
    }

    private void loadAllRoutes(AssetManager am) {

        try {
            for (String s : am.list("routes/")) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(am.open("routes/" + s)));
                String name = s.replace(".txt" , "");
                ArrayList<String> stops = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    stops.add(line.trim());
                }
                fileContent.put(name, stops);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public HashMap<String, ArrayList> getAllRoutes(){
        return fileContent;
    }
}