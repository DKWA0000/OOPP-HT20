package com.example.planka.service;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.planka.model.Node; // only used for javadoc
import com.example.planka.model.Route;

/**
 * Class Responsible for reading files.
 *
 * @author Seif Eddine Bourogaa
 * @see Node class
 * @see Route class
 */

public class FileReader {

    HashMap<String, ArrayList> fileContent;

    /**
     * Constructor of FileReader, takes the AssetManager and passes it to loadAllRoutes.
     *
     * @param am AssetManager
     * @see #loadAllRoutes(AssetManager)
     */
    public FileReader(AssetManager am) {
        fileContent = new HashMap<>();
        loadAllRoutes(am);
    }

    /**
     * Method for loading all the routes using the files found csv files found in assets
     *
     * @param am AssetManager
     */
    private void loadAllRoutes(AssetManager am) {
        try {
            for (String s : am.list("routes/")) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(am.open("routes/" + s)));
                String name = s.replace(".txt", "");
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

    /**
     * Get method for to get content read from file.
     */
    public HashMap<String, ArrayList> getAllRoutes() {
        return fileContent;
    }

}