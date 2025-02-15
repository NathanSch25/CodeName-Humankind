package src.Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class handles parsing a text file containing our level into a navigatable grid
 * it also handles the logic for navigating said level.
 * 
 */


public class Level {
    private ArrayList<int[]> Map = new ArrayList<>();
    private HashMap<String, ArrayList<String>> NavMap = new HashMap<>();

    public void GenerateMap(){
        //Read Lines From required file
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines = new ArrayList<>(Files.readAllLines(Paths.get("src","Classes","TestMap.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        //Convert lines to array elements for our map
        for(String line:lines){
             int[] intArray = Arrays.stream(line.trim().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
        Map.add(intArray);
        }

        //View Our Map
        for(int[] arr:Map){
            System.out.println(Arrays.toString(arr));
        }

        //Generate NavMap
        GenerateNavMap();

        // View The NavMap graph
        for (var entry : NavMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void GenerateNavMap(){
        int gridSize = Map.size();
        
        for(int i =0; i<gridSize; i++)
        {
            //Get A Line
            int[] row = Map.get(i);
            for(int j = 0; j<gridSize; j++)
            {
                //Check if the current position is not a wall
                if(row[j] != 1){
                    ArrayList<String> navigatable = new ArrayList<>();
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    for(int[] dir: directions){
                        int nextI = i + dir[0], nextJ = j+dir[1];
                        
                        //Ensure that nextPosition is within the map
                        if(nextI >= 0 && nextI < gridSize && nextJ >= 0 && nextJ < row.length)
                        {
                            if(Map.get(nextI)[nextJ] == 0){
                                //Position is Free
                                navigatable.add(nextI+","+nextJ);
                            }
                        }
                    }
                
                NavMap.put(i+","+j,navigatable);
                }
            }
        }
    }

    public static void main(String[] args){
        //Test Map gen
        Level newLevel = new Level();
        newLevel.GenerateMap();

    }


}
