import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Entity {
    //Entity Varaiables
    ArrayList<Upgrade> ModifierTable = new ArrayList<>();

    String Name;
    // Entity Position
    public int x;
    public int y;
    public float Health = 100;
    public float Damage = 1;
    public int Speed = 200;

    public Entity(int a, int b, int c) {
        x = a;
        y = b;
        Speed = c;
    }


    float Allignment;

    //Entity Functions
    public int[] GetPosition(){
        return new int[]{this.x, this.y};
    }

    //Get Entity Team
    /*
    public Team GetTeam(){
        return (this.Allignment>0) ? Team.Human : Team.Technology;
    }
    */
}