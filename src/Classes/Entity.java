package src.Classes;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Entity {
    //Entity Varaiables
    Dictionary<Stat,Float> Status = new Hashtable<>();
    ArrayList<Stat,Float> ModifierTable = new ArrayList<>(); 

    float Allignment;
    
    // Entity Position
    private int x;
    private int y;

    //Entity Functions
    public int[] GetPosition(){
        int[] Position = {this.x, this.y};
        return Position;
    }

    //Get Entity Team
    public Team GetTeam(){
        return (this.Allignment>0) ? Team.Human : Team.Technology;
    }
}



