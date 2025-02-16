package src.Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * This class handles parsing a text file containing our level into a navigatable grid
 * it also handles the logic for navigating said level.
 * 
 */


public class Level {
    private HashMap<Entity, String> ObjectMap = new HashMap<>();
    private ArrayList<int[]> Map = new ArrayList<>();
    private HashMap<String, ArrayList<String>> NavMap = new HashMap<>();

    //This function generates the map, essentially parsing our text file into a 2d array
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

        System.out.println("============Map Debug============");
        //View Our Map
        System.out.println("============Map Generation============");
        for(int[] arr:Map){
            System.out.println(Arrays.toString(arr));
        }

        //Generate NavMap
        GenerateNavMap();
        System.out.println("============Map Generation END============");

        // View The NavMap graph
        System.out.println("============Navigation Map Generation============");
        for (var entry : NavMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("============Navigation Map Generation END============");
        System.out.println("================================");
    }

    //This function generates a nav map, essentially creating a dictionary of initial positions:key, and possible positions:value
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

    //This is a basic search algorithem for now BSF, until i switch to A*
    public List<String> SimpleSearch(String start, String end){
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(Collections.singletonList(start));

        while(!queue.isEmpty()){
            List<String> path = queue.poll();
            String current = path.get(path.size()-1);

            //Found a path to the target location
            if(current.equals(end)){
                return path;
            }

            if(visited.contains(current)){continue;}

            visited.add(current);

            //Explore neighbor tiles;
            List<String> neighbors = NavMap.getOrDefault(current, new ArrayList<String>());
            for (String neighbor: neighbors){
                List<String> newPath = new ArrayList<>(path);
                newPath.add(neighbor);
                queue.add(newPath);
            }

        }
       return new ArrayList<>(); //No Valid Paths Found, Empty List Returned
    }

    //This function update|create an object mapping to some position on our grid, returns the string position of the object
    public String SetObjectPosition(Entity targetEntity, Integer[] position)
    {
        String setPosition = ObjectMap.put(targetEntity, position[0]+","+position[1]);
        this.GenerateNavMap();
        return setPosition;
    }

    //This Function will return the position of the object, (Given the object exists within the map) , returns the string position of the object i.e ("4,19")
    public String GetObjectPosition(Entity targetEntity){
        return ObjectMap.get(targetEntity);
    }

    //This function will return true if the space is occupied by a obstacle or entity
    public boolean isOccupied(int x, int y){
        String targetPosition = x+","+y;

        //Checks for object at position, or wall. 
        if(ObjectMap.containsValue(targetPosition) || Map.get(x)[y] == 1){
            return true;
        }
        return false;
    }
    public static void main(String[] args){
        //Test Map gen
        Level newLevel = new Level();
        newLevel.GenerateMap();
        
        String start = "1,1";
        String end = "19,19";

        List<String> foundPath = newLevel.SimpleSearch(start, end);
        if(!foundPath.isEmpty()){
            System.out.println("Navigation is Possible From "+ foundPath);
        } else {
            System.out.println("No path found.");
        }

    }


}
