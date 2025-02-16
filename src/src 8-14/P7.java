package src 8-14;

import com.sun.jdi.connect.AttachingConnector;

public class P7 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraMeleeDamage;
    public int ExtraRangeDamage;
    public int Range;
    public int Speed;
     */
    P7() {
        // Punch Attack
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {}
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                for (int x = 0; x < GamePanel.Units.length; x++) {
                    if (Self == GamePanel.Units[x]) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units[x].x - Self.x, 2) + Math.pow(GamePanel.Units[x].y - Self.y, 2));
                    if (TDis <= 400 * Range) {
                        GamePanel.Units[x].Dam(Self, Self.Damage * 1.3);
                        break;
                    }
                }
            }
        };
        Att.Name = "spear throw";
        Att.Action = true;
    }
    
}
