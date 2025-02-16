import java.awt.*;
import java.util.ArrayList;
public class P9 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */

    P9() {
        Robo = 20;
        ExtraHP = 5;
        

        // refractor master
        Att = new Style() {
            @Override
            public boolean TakeOver(Entity Self) {
                return false;
            }

            @Override
            public void Pain(Entity Self, float Amount) {
                Self.Health -= 20;
                float dammageTaken = (Self.Hurt - Self.Health);
                ArrayList<Integer> nearby = new ArrayList<>();
                
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - Self.x, 2) + Math.pow(GamePanel.Units.get(x).y - Self.y, 2));
                    if (TDis <= 140 + 10 * Self.Range) {
                        nearby.add(x);
                        
                    }
                }
                float dammageSpread = ((float) dammageTaken) / nearby.size();
                for (int i : nearby) {
                    GamePanel.Units.get(i).Dam(Self, -dammageSpread);
                }   
            }

            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {
            }

            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
            }
        };
        Att.Name = "refractor master";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRoundRect(GamePanel.player.NextX-100, GamePanel.player.NextY-100, 200, 200, -100, -100);
    }
    
}