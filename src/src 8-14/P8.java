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
        // laser beam
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
                    if (TDis <= 800 * Range) {
                        switch (GamePanel.Units[x].x) {
                            case Self.x:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                            case Self.x + 1:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                            case Self.x - 1:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                        }
                        switch (GamePanel.Units[x].y) {   
                                case Self.y:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                            case Self.y + 1:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                            case Self.y - 1:
                                GamePanel.Units[x].Dam(Self, Self.Damage);
                                break;
                    }
                }
            }
        };
        Att.Name = "laser beam";
        Att.Action = true;
    }
    
}
