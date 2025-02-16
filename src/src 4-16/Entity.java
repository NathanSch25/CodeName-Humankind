import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Entity {
    //Entity Varaiables
    ArrayList<Upgrade> ModifierTable = new ArrayList<>();
    public float Health = 100;
    public float Damage = 1;
    public float Speed = 3;

    float Allignment;

    // Entity Position
    public int x;
    public int y;

    //Entity Functions
    public int[] GetPosition(){
        int[] Position = {this.x, this.y};
        return Position;
    }

    //Get Entity Team
    /*
    public Team GetTeam(){
        return (this.Allignment>0) ? Team.Human : Team.Technology;
    }
    */
}