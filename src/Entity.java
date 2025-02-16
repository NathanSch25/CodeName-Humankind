import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Entity {
    //Entity Varaiables
    ArrayList<Upgrade> AllAtt = new ArrayList<>();

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

    public Entity(GamePanel Screen, Level Map) {}
    public Entity(int a, int b, int c) {
        Hurt = Health;
        x = a;
        y = b;
        Speed = c;
    }
    public void Silly(int a, int b, int c) {
        Hurt = Health;
        x = a;
        y = b;
        Speed = c;
    }

    public void SetRPos(GamePanel Screen, Level Map) {
        int xPos = 0;
        int yPos = 0;
        int SafeCount = 1000;
        while (SafeCount != 0) {
            xPos = (int) ((Map.Width * Math.random()));
            yPos = (int) ((Map.Height * Math.random()));

            if (Map.Map.get(xPos + 1)[yPos + 1] == 0) {
                break;
            }
            SafeCount--;
        }
        Silly((int) ((xPos + .5f) * Screen.SWidth / Map.Width), (int) ((yPos + .5f) * Screen.SHeight / Map.Height), Speed);
    }

    public void AddBuffs() {
        // Load Base Stats
        Roboness = 0;
        MaxHP = 100;
        Damage = 10;
        Speed = 200;
        Range = 1;

        for (int x = 0; x < AllAtt.size(); x++) {
            Roboness += AllAtt.get(x).Robo;
            MaxHP += AllAtt.get(x).ExtraHP;
            Damage += AllAtt.get(x).ExtraDamage;
            Speed += AllAtt.get(x).Speed;
            Range += AllAtt.get(x).Range;
        }
        Health = MaxHP;
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