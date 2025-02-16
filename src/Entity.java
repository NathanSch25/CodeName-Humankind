import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Entity {
    //Entity Varaiables
    ArrayList<Upgrade> ModifierTable = new ArrayList<>();

    String Name;
    // Entity Position
    public int Roboness = 0;
    public int Stun = 0;
    public int StunResistance = 0;
    public int x;
    public int y;
    public float MaxHP = 100;
    public float Health = 100;
    public int Damage = 10;
    public int Speed = 200;
    public float Range = 1;
    public float Hurt;
    public Upgrade Active = new Upgrade();

    public Entity(int a, int b, int c) {
        Hurt = Health;
        x = a;
        y = b;
        Speed = c;
    }

    public void StunEnemy(int Length) {
        if (StunResistance > Length) {
            Dam(this, Length);
            return;
        }
        StunResistance += Stun + 1;
        Stun += Length;
    }

    public void Heal(Entity Healer, float Amount) {
        Health = Math.min(MaxHP, Health + Amount);
    }

    public void Dam(Entity Hurter) {
        Health -= Hurter.Damage;
        Active.Att.WhenAttacked(this, Hurter, Hurter.Damage);
        System.out.println("" + Hurter.Name + " hit " + Name + "!");
    }
    public void Dam(Entity Hurter, float Amount) {
        Health -= Amount;
        Active.Att.WhenAttacked(this, Hurter, (int) Amount);
        System.out.println("" + Hurter.Name + " hit " + Name + "!");
    }

    public boolean IfHurt() {
        Stun -= Stun > 0 ? 1 : 0;
        StunResistance -= StunResistance > 0 ? 1 : 0;
        Active.Att.Pain(this, Hurt - Health);
        Hurt = Health;
        return Health <= 0;
    }

    boolean Good = false;
    int CSize = 48;

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